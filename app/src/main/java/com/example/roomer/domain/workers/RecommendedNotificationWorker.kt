package com.example.roomer.domain.workers

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.roomer.MainActivity
import com.example.roomer.R
import com.example.roomer.utils.Constants
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class RecommendedNotificationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val manager =
                applicationContext
                    .getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
            val channel = NotificationChannel(
                CHANNEL_ID,
                applicationContext.resources.getString(R.string.recommended_notification_title),
                NotificationManager.IMPORTANCE_DEFAULT,
            )
            manager.createNotificationChannel(channel)
            manager.notify(
                NOTIFICATION_ID,
                getRecommendation(),
            )
            return Result.success()
        }
        return Result.failure()
    }

    private fun notificationRooms(): Notification {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.action = Constants.ACTION_NOTIFICATION_ROOMS
        val pendingIntent =
            PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        return NotificationCompat.Builder(
            applicationContext,
            CHANNEL_ID,
        )
            .setSmallIcon(R.drawable.account_icon)
            .setContentText(
                applicationContext.resources.getString(R.string.recommended_room_notification_text)
            )
            .setContentTitle(
                applicationContext.resources.getString(R.string.recommended_notification_title)
            )
            .setChannelId(CHANNEL_ID)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
    }

    private fun notificationMates(): Notification {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.action = Constants.ACTION_NOTIFICATION_MATES
        val pendingIntent =
            PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        return NotificationCompat.Builder(
            applicationContext,
            CHANNEL_ID,
        )
            .setSmallIcon(R.drawable.account_icon)
            .setContentTitle(
                applicationContext.resources.getString(R.string.messenger_notification_title)
            )
            .setChannelId(CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setContentText(
                applicationContext.resources.getString(R.string.recommended_mate_notification_text)
            )
            .build()
    }

    private fun getRecommendation(): Notification {
        return when (LIST_OF_RECOMMENDED.random()) {
            "Room" -> notificationRooms()
            "Mate" -> notificationMates()
            else -> notificationMates()
        }
    }

    private companion object {
        const val NOTIFICATION_ID = 16
        const val CHANNEL_ID = "Recommended channel"
        val LIST_OF_RECOMMENDED = listOf("Room", "Mate")
    }
}
