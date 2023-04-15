package com.example.roomer.domain.workers

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.Person
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.example.roomer.MainActivity
import com.example.roomer.R
import com.example.roomer.data.repository.roomer_repository.RoomerRepository
import com.example.roomer.domain.model.entities.MessageNotification
import com.example.roomer.utils.Constants
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.*

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
            val authUser = roomerRepository.getLocalCurrentUser()
            //val messages = roomerRepository.getMessageNotifications(authUser.userId).body()
            val messages = roomerRepository.getMessageNotifications(302).body()
            messages?.let {
                if (messages.isNotEmpty()) {
                    setForeground(createForegroundInfo(messages))
                }
                return Result.success()
            }
        }
        return Result.failure()
    }

    private fun createForegroundInfo(messageNotifications: List<MessageNotification>): ForegroundInfo {
        return ForegroundInfo(NOTIFICATION_BASE_ID, notification(messageNotifications))
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
        val manager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
        val notificationsByChats = messages.groupBy { it.message?.chatId }
        for (groupNotification in notificationsByChats) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.action = Constants.ACTION_NOTIFICATION_CHAT
            intent.putExtra(Constants.EXTRA_NOTIFICATION_CHAT, groupNotification.value.first().message?.chatId)
            intent.putExtra(Constants.EXTRA_NOTIFICATION_RECIPIENT, groupNotification.value.first().message?.recipient?.userId)
            val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            val notificationBuilder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                .setSmallIcon(R.drawable.account_icon)
                .setContentTitle(applicationContext.resources.getString(R.string.messenger_notification_title))
                .setChannelId(CHANNEL_ID)
                .setAutoCancel(false)
                .setContentIntent(pendingIntent)
            val sender =
                Person.Builder().setName(
                    groupNotification.value.first().message?.donor?.firstName ?: "Undefined name"
                )
                    .build()
            val notificationChat = NotificationCompat.MessagingStyle(sender)
            notificationChat.setBuilder(notificationBuilder)
            groupNotification.value.forEach { message ->
                notificationChat.addMessage(message.message?.text ?: "", Date().time, sender)
            }
            return notificationChat.build()!!
        }
        return Notification.Builder(applicationContext).build()
    }

    private companion object {
        const val NOTIFICATION_BASE_ID = 15
        const val CHANNEL_ID = "Messenger channel"
    }

}