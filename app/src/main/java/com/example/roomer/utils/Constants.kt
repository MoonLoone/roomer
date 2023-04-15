package com.example.roomer.utils

import com.example.roomer.BuildConfig

object Constants {
    const val BASE_URL = BuildConfig.BASE_URL

    object ScreensId {
        const val greetingScreenId = 0
        const val signUpScreenId = 7
    }

    object UseCase {
        const val internetErrorMessage = "No internet connection!"
        const val loginErrorName = "non_field_errors"
    }
    const val ACTION_NOTIFICATION_ROOMS = "com.example.roomer.SHOW_ROOMS"
    const val ACTION_NOTIFICATION_MATES = "com.example.roomer.SHOW_MATES"
    const val ACTION_NOTIFICATION_CHAT = "com.example.roomer.SHOW_CHAT"
    const val EXTRA_NOTIFICATION_CHAT = "chat_id"
    const val EXTRA_NOTIFICATION_RECIPIENT = "recipient_id"
}
