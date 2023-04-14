package com.example.roomer.domain.workers

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.example.roomer.R
import com.example.roomer.data.repository.roomer_repository.RoomerRepository
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.domain.model.entities.MessageNotification
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.*
import javax.inject.Inject

@HiltWorker
class ChatNotificationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val roomerRepository: RoomerRepository
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val messages = roomerRepository.getMessageNotifications(302).body()
            setForeground(createForegroundInfo(messages ?: emptyList()))
            return Result.success()
        }
        return Result.failure()
    }

    private fun createForegroundInfo(messageNotifications: List<MessageNotification>): ForegroundInfo {
        return ForegroundInfo(NOTIFICATION_ID, notification(messageNotifications))
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return super.getForegroundInfo()
    }

    private fun notification(messages: List<MessageNotification>): Notification {
        val channel = NotificationChannel(
            CHANNEL_ID,
            applicationContext.resources.getString(R.string.messenger_notification_title),
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val messagingStyle = NotificationCompat.MessagingStyle("User")
        messages.forEach { message ->
            messagingStyle.addMessage(message.message?.text,Date().time, message.message?.donor?.firstName)
        }
        val notificationChat = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.account_icon)
            .setContentTitle(applicationContext.resources.getString(R.string.messenger_notification_title))
            .setStyle(messagingStyle)
            .setChannelId(CHANNEL_ID)
            .build()
        val manager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
        manager.notify(NOTIFICATION_ID, notificationChat)
        return notificationChat
    }

    private companion object {
        const val NOTIFICATION_ID = 15
        const val CHANNEL_ID = "Messenger channel"
    }

}