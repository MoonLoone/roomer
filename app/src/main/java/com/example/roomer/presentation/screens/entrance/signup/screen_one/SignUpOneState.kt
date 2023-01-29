package com.example.roomer.presentation.screens.entrance.signup.screen_one

data class SignUpOneState(
    val success: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = "",
    val internetProblem: Boolean = false,
    val isFirstNameError: Boolean = false,
    val isLastNameError: Boolean = false
    )
