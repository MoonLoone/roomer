package com.example.roomer.data.remote

import android.content.Context
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.roomer.domain.workers.NotificationWorker
import java.util.concurrent.TimeUnit

object NotificationManager {

    fun registerWork(context: Context) {
        val request =
            PeriodicWorkRequest.Builder(NotificationWorker::class.java, 15, TimeUnit.MINUTES)
                .build()
        WorkManager.getInstance(context).enqueue(request)
    }

}