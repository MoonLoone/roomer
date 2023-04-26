package com.example.roomer.utils

import com.example.roomer.BuildConfig

object Constants {
    const val BASE_URL = BuildConfig.BASE_URL

    object Maps {
        val sexMap = mapOf(Pair("M", "Male"), Pair("F", "Female"))
        val employmentMap = mapOf(
            Pair("NE", "Not Employed"),
            Pair("E", "Employed"),
            Pair("S", "Searching for work")
        )
        val personalityMap = mapOf(
            Pair("E", "Extraverted"),
            Pair("I", "Introverted"),
            Pair("M", "Mixed")
        )
    }

    object ScreensId {
        const val greetingScreenId = 0
        const val signUpScreenId = 7
    }

    object UseCase {
        const val internetErrorMessage = "No internet connection!"
        const val loginErrorName = "non_field_errors"
    }

    object Notification {
        const val ACTION_NOTIFICATION_ROOMS = "com.example.roomer.SHOW_ROOMS"
        const val ACTION_NOTIFICATION_MATES = "com.example.roomer.SHOW_MATES"
        const val ACTION_NOTIFICATION_CHAT = "com.example.roomer.SHOW_CHAT"
        const val EXTRA_NOTIFICATION_CHAT = "chat_id"
        const val EXTRA_NOTIFICATION_RECIPIENT = "recipient_id"
        const val MESSENGER_WORK_REPEAT = 15L
        const val MESSENGER_WORK_FLEX = 1L
        const val RECOMMENDATION_WORK_REPEAT = 3L
        const val RECOMMENDATION_WORK_FLEX = 2L
    }

    object Chat {
        const val PAGE_SIZE = 10
        const val CASH_SIZE = 30
        const val INITIAL_SIZE = 10
    }
}
