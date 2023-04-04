package com.example.roomer.data.remote

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.roomer.data.repository.RoomerRepository
import com.example.roomer.presentation.screens.destinations.ChatScreenDestination
import com.example.roomer.utils.Constants
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.remoteMessage
import javax.inject.Inject

class NotificationService @Inject constructor(
    private val roomerRepository: RoomerRepository
) : FirebaseMessagingService() {

    private lateinit var firebaseToken: String
    private val FCMInstance = FirebaseMessaging.getInstance()

    init {
        FCMInstance.token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                firebaseToken = ""
                return@addOnCompleteListener
            }
            firebaseToken = task.result
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        firebaseToken = token
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val data = message.data
        val title = data["title"]
        val messageText = data["message"]
        sendNotification(this, title ?: "", messageText ?: "")
    }

    private fun sendNotification(
        context: Context,
        title: String,
        message: String,
    ) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationChannel = NotificationChannel(
            "channel_notification",
            "notification_channel",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationChannel.description = "What else"
        notificationManager.createNotificationChannel(notificationChannel)
        val builder = NotificationCompat.Builder(context, "id")
            .setAutoCancel(true)
        val intent = Intent()
        intent.action = Intent.ACTION_MAIN
        intent.putExtra(Constants.NAVIGATE_TO_SCREEN, Constants.ROOMS_SCREEN_DEST)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        builder.setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
        notificationManager.notify(0, builder.build())
    }
}