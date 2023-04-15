package com.example.roomer.management

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.roomer.domain.workers.ChatNotificationWorker
import com.example.roomer.domain.workers.RecommendedNotificationWorker
import java.util.concurrent.TimeUnit

object NotificationManager {

    private const val NOTIFICATION_TAG = "notification_tag"
    private const val RECOMMENDATION_TAG = "recommendation_tag"

    fun registerAllWorks(context: Context) {
        registerRecommendationWork(context)
        registerMessengerWork(context)
    }

    private fun registerMessengerWork(context: Context) {
        /*val request =
            PeriodicWorkRequestBuilder<ChatNotificationWorker>(
                15,
                TimeUnit.MINUTES,
                1,
                TimeUnit.MINUTES
            )
                .addTag(NOTIFICATION_TAG)
                .build()*/
        val request = OneTimeWorkRequestBuilder<ChatNotificationWorker>().build()
        WorkManager.getInstance(context).enqueue(request)
        /*WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(NOTIFICATION_TAG, ExistingPeriodicWorkPolicy.KEEP, request)*/
    }

    private fun registerRecommendationWork(context: Context) {
        val request =
            PeriodicWorkRequestBuilder<RecommendedNotificationWorker>(
                3,
                TimeUnit.DAYS,
                2,
                TimeUnit.DAYS
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