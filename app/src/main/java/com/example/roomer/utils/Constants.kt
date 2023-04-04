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

    const val NAVIGATE_TO_SCREEN = "navigate_to_screen"
    const val ROOMS_SCREEN_DEST = "rooms_screen"
    const val ROOMMATES_SCREEN_DEST = "roommates_screen"

}
