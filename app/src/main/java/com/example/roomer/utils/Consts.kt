package com.example.roomer.utils

import com.example.roomer.BuildConfig

object Constants {
    const val BASE_URL = BuildConfig.BASE_URL

    object ScreensId{
        const val greetingScreenId = 0
        const val loginScreenId = 1
        const val signUpOneScreenId = 2
        const val signUpTwoScreenId = 3
        const val signUpThreeScreenId = 4
        const val interestsScreenId = 5
        const val signUpScreenId = 7
    }

    object UseCase{
        const val internetErrorMessage = "No internet connection!"
        const val loginErrorName = "non_field_errors"
    }

}
