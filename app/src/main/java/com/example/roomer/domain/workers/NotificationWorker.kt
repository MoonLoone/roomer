package com.example.roomer.domain.workers

import android.Manifest
import android.app.Notification
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.roomer.R


class NotificationWorker (val appContext: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(appContext,workerParameters) {

    override suspend fun doWork(): Result {
        //setForeground(createForegroundInfo())
        notification()
        return Result.success()
    }

    fun createForegroundInfo(): ForegroundInfo{
        return ForegroundInfo(NOTIFICATION_ID, notification())
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return super.getForegroundInfo()
    }

    fun notification(): Notification{
        val notification = NotificationCompat
            .Builder(appContext, NOTIFICATION_ID.toString())
            .setContentText("text")
            .setSmallIcon(R.drawable.account_icon)
            .build()
        if (ActivityCompat.checkSelfPermission(
                appContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return notification
        }
        NotificationManagerCompat.from(appContext).notify(NOTIFICATION_ID, notification)
        return notification
    }

    companion object{
        const val NOTIFICATION_ID = 15
    }

}