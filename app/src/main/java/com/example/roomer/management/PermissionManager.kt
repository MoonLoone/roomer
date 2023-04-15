package com.example.roomer.management

import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import javax.inject.Inject

class PermissionManager @Inject constructor(
    private val application: Application
) {

    fun askNotificationPermission() {
        if ((
                ContextCompat.checkSelfPermission(
                    application.applicationContext,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
                ) && (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        ) ActivityResultContracts.RequestPermission()
    }
}
