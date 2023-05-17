package com.example.roomer.presentation.screens.entrance.splash_screen

import com.example.roomer.utils.ScreenState

data class SplashScreenState(
    override var success: Boolean = false,
    override var isLoading: Boolean = false,
    override var error: String = "",
    override var internetProblem: Boolean = false,
    var isSignUpNotFinished: Boolean = false,
    var isError: Boolean = false
) : ScreenState
