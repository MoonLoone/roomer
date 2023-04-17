package com.example.roomer.presentation.screens.entrance.signup.primary_user_info_screen

data class SignUpOneState(
    val success: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = "",
    val internetProblem: Boolean = false
)
