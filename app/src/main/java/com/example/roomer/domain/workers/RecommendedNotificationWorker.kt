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
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.example.roomer.MainActivity
import com.example.roomer.R
import com.example.roomer.utils.Constants
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlin.random.Random

class RecommendedNotificationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            setForeground(createForegroundInfo())
            return Result.success()
        }
        return Result.failure()
    }

    private fun createForegroundInfo(): ForegroundInfo {
        val random = LIST_OF_RECOMMENDED.random()
        return ForegroundInfo(
            NOTIFICATION_ID,
            when(random){
                "Room" -> notificationRooms()
                "Mate" -> notificationMates()
                else -> notificationMates()
            }
        )
    }

    private fun notificationRooms(): Notification {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.action = Constants.ACTION_NOTIFICATION_ROOMS
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val channel = NotificationChannel(
            CHANNEL_ID,
            applicationContext.resources.getString(R.string.messenger_notification_title),
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val notificationRecommended = NotificationCompat.Builder(
            applicationContext,
            CHANNEL_ID
        )
            .setSmallIcon(R.drawable.account_icon)
            .setContentText(applicationContext.resources.getString(R.string.recommended_room_notification_text))
            .setContentTitle(applicationContext.resources.getString(R.string.messenger_notification_title))
            .setChannelId(CHANNEL_ID)
            .setAutoCancel(false)
            .setContentIntent(pendingIntent)
            .build()
        val manager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
        manager.createNotificationChannel(channel)
        manager.notify(NOTIFICATION_ID, notificationRecommended)
        return notificationRecommended
    }

    private fun notificationMates(): Notification {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.action = Constants.ACTION_NOTIFICATION_MATES
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val channel = NotificationChannel(
            CHANNEL_ID,
            applicationContext.resources.getString(R.string.messenger_notification_title),
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val notificationRecommended = NotificationCompat.Builder(
            applicationContext,
            CHANNEL_ID
        )
            .setSmallIcon(R.drawable.account_icon)
            .setContentTitle(applicationContext.resources.getString(R.string.messenger_notification_title))
            .setChannelId(CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setAutoCancel(false)
            .setContentText(applicationContext.resources.getString(R.string.recommended_mate_notification_text))
            .build()
        val manager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
        manager.createNotificationChannel(channel)
        manager.notify(NOTIFICATION_ID, notificationRecommended)
        return notificationRecommended
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return super.getForegroundInfo()
    }

    private companion object {
        const val NOTIFICATION_ID = 16
        const val CHANNEL_ID = "Recommended channel"
        val LIST_OF_RECOMMENDED = listOf("Room", "Mate")
    }

}