package com.example.roomer.utils

import com.example.roomer.BuildConfig
import com.example.roomer.R

object Constants {
    const val BASE_URL = BuildConfig.BASE_URL
    const val EXP_TEXT_MINIMUM_TEXT_LINE = 3
    const val JPEG_QUALITY = 80
    const val HISTORY_SIZE: Long = 10
    const val CITIES_SHOWN_AT_ONCE = 5
    const val USER_CARD_MAX_NAME = 12
    const val ROOM_CARD_MAX_NAME = 16
    const val ROOM_CARD_MAX_LOCATION = 24
    const val MAX_CHARS_AMOUNT_IN_BUTTONS_ROW = 35
    const val SPACE_IN_BUTTONS_ROW = 8
    const val MAX_CHARS_IN_COMMENT_NAME = 16

    object ScreensId {
        const val greetingScreenId = 0
        const val signUpScreenId = 7
    }

    object UseCase {
        const val internetErrorMessage = "No internet connection!"
        const val loginErrorName = "non_field_errors"
        const val tokenNotFoundErrorMessage = "Token not found"
        const val generalError = "An error occurred!"
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
        const val PAGE_SIZE = 20
        const val CASH_SIZE = 100
        const val INITIAL_SIZE = 40
        const val CHAT_USERNAME_MAX_LENGTH = 16
        const val MESSENGER_TEXT_MAX_SIZE = 32
    }

    object Home {
        const val HOME_USERNAME_MAX_LENGTH = 16
        const val RECOMMENDED_MATES_SIZE = 15
        const val RECOMMENDED_ROOMS_SIZE = 10
    }

    object Follows {
        const val ERROR_EMPTY_LIST = "empty list"
        const val ERROR_UNAUTHORIZED = "unauthorized"
        const val USER_CARD_MAX_NAME = 12
        const val SMALL_USER_NAME = 10
        const val FOLLOW_PROBLEM = "follow can't be performed"
    }

    object Favourites {
        const val ADD_FAVOURITE_ERROR = "Can't add to favourite"
    }

    object RoomDetails {
        const val ERROR_MSG = "Generall error"
    }

    object Options {
        val apartmentOptions = mapOf(
            Pair("F", R.string.flat),
            Pair("DU", R.string.duplex),
            Pair("H", R.string.house),
            Pair("DO", R.string.dorm)
        )

        val sexOptions = mapOf(
            Pair("A", R.string.any),
            Pair("M", R.string.male),
            Pair("F", R.string.female)
        )

        val sleepOptions = mapOf(
            Pair("N", R.string.night),
            Pair("D", R.string.day),
            Pair("O", R.string.occasionally)
        )

        val personalityOptions = mapOf(
            Pair("E", R.string.extraverted),
            Pair("I", R.string.introverted),
            Pair("M", R.string.mixed)
        )

        val attitudeOptions = mapOf(
            Pair("P", R.string.positive),
            Pair("N", R.string.negative),
            Pair("I", R.string.indifferent)
        )

        val employmentOptions = mapOf(
            Pair("NE", R.string.not_employed),
            Pair("E", R.string.employed),
            Pair("S", R.string.searching_for_work)
        )

        val cleanOptions = mapOf(
            Pair("N", R.string.neat),
            Pair("D", R.string.it_depends),
            Pair("C", R.string.chaos)
        )

        val roomsCountOptions = listOf(R.string.any, R.string._1, R.string._2, R.string._3)
    }

    object RoomPost {
        val ROOMS_COUNT_LIST = listOf("1", "2", "3", "4", "5")
    }
}
