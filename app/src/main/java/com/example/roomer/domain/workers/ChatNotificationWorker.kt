package com.example.roomer.domain.workers

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.Person
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import coil.ImageLoader
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.roomer.MainActivity
import com.example.roomer.R
import com.example.roomer.data.repository.roomer_repository.RoomerRepository
import com.example.roomer.domain.model.entities.MessageNotification
import com.example.roomer.utils.Constants
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.Date

@HiltWorker
class ChatNotificationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val roomerRepository: RoomerRepository,
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val authUser = roomerRepository.getLocalCurrentUser()
            val messages = roomerRepository.getMessageNotifications(authUser.userId).body()
            if (!messages.isNullOrEmpty()) {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    applicationContext.resources.getString(R.string.messenger_notification_title),
                    NotificationManager.IMPORTANCE_DEFAULT,
                )
                val manager =
                    applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.createNotificationChannel(channel)
                manager.notify(NOTIFICATION_BASE_ID, notification(messages))
            }
            return Result.success()
        }
        return Result.failure()
    }

    private suspend fun notification(messages: List<MessageNotification>): Notification? {
        val notificationsByChats = messages.groupBy { it.message?.chatId }
        for (groupNotification in notificationsByChats) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.action = Constants.ACTION_NOTIFICATION_CHAT
            intent.putExtra(
                Constants.EXTRA_NOTIFICATION_CHAT,
                groupNotification.value.first().message?.chatId,
            )
            intent.putExtra(
                Constants.EXTRA_NOTIFICATION_RECIPIENT,
                groupNotification.value.first().message?.recipient?.userId,
            )
            val pendingIntent = PendingIntent.getActivity(
                applicationContext,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE,
            )
            val userAvatar = getUserImg(messages.first().message?.donor?.avatar ?: "")
            val notificationBuilder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                .setSmallIcon(R.drawable.account_icon)
                .setChannelId(CHANNEL_ID)
                .setLargeIcon(userAvatar)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
            val sender =
                Person.Builder().setName(
                    groupNotification.value.first().message?.donor?.firstName ?: "Undefined name",
                )
                    .build()
            val notificationChat = NotificationCompat.MessagingStyle(sender)
            notificationChat.setBuilder(notificationBuilder)
            groupNotification.value.forEach { message ->
                notificationChat.addMessage(message.message?.text ?: "", Date().time, sender)
            }
            return notificationChat.build()!!
        }
        return null
    }

    private suspend fun getUserImg(avatarPath: String): Bitmap? {
        val loader = ImageLoader(applicationContext)
        val request = ImageRequest.Builder(applicationContext)
            .data(avatarPath)
            .allowHardware(false)
            .build()
        val bitmap = when (val result = loader.execute(request)) {
            is SuccessResult -> (result as BitmapDrawable).bitmap
            is ErrorResult -> null
        }
        return bitmap
    }

    private companion object {
        const val NOTIFICATION_BASE_ID = 15
        const val CHANNEL_ID = "Messenger channel"
    }
}
