package com.example.roomer.management

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.roomer.domain.workers.ChatNotificationWorker
import com.example.roomer.domain.workers.RecommendedNotificationWorker
import com.example.roomer.utils.Constants
import java.util.concurrent.TimeUnit

object NotificationManager {

    private const val NOTIFICATION_TAG = "notification_tag"
    private const val RECOMMENDATION_TAG = "recommendation_tag"

    fun registerAllWorks(context: Context) {
        registerRecommendationWork(context)
        registerMessengerWork(context)
    }

    private fun registerMessengerWork(context: Context) {
        val request =
            PeriodicWorkRequestBuilder<ChatNotificationWorker>(
                Constants.Notification.MESSENGER_WORK_REPEAT,
                TimeUnit.MINUTES,
                Constants.Notification.MESSENGER_WORK_FLEX,
                TimeUnit.MINUTES,
            )
                .addTag(NOTIFICATION_TAG)
                .build()
        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(NOTIFICATION_TAG, ExistingPeriodicWorkPolicy.KEEP, request)
    }

    private fun registerRecommendationWork(context: Context) {
        val request =
            PeriodicWorkRequestBuilder<RecommendedNotificationWorker>(
                Constants.Notification.RECOMMENDATION_WORK_REPEAT,
                TimeUnit.DAYS,
                Constants.Notification.RECOMMENDATION_WORK_FLEX,
                TimeUnit.DAYS,
            )
                .build()
        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(RECOMMENDATION_TAG, ExistingPeriodicWorkPolicy.KEEP, request)
    }

    fun stopAllWorks(context: Context) {
        WorkManager.getInstance(context).cancelAllWorkByTag(NOTIFICATION_TAG)
        WorkManager.getInstance(context).cancelAllWorkByTag(RECOMMENDATION_TAG)
    }
}
