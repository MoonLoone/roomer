package com.example.roomer.domain.workers

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.example.roomer.R
import com.example.roomer.data.remote.RoomerApi
import com.example.roomer.data.repository.RoomerRepository
import com.example.roomer.domain.model.entities.MessageNotification
import com.example.roomer.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class ChatNotificationWorker(
    context: Context,
    workerParameters: WorkerParameters,
) : CoroutineWorker(context, workerParameters) {

    private val roomerRepositoryInterface = RoomerRepository(
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RoomerApi::class.java)
    )

    override suspend fun doWork(): Result {
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val messages = roomerRepositoryInterface.getMessageNotifications(302).body()
            setForeground(createForegroundInfo(messages ?: emptyList<MessageNotification>()))
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
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
        manager.createNotificationChannel(channel)
        manager.notify(NOTIFICATION_ID, notificationChat)
        return notificationChat
    }

    private companion object {
        const val NOTIFICATION_ID = 15
        const val CHANNEL_ID = "Messenger channel"
    }

}