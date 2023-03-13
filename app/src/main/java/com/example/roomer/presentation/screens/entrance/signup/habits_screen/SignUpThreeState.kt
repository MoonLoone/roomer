package com.example.roomer.presentation.screens.entrance.signup.habits_screen

data class SignUpThreeState(
    val success: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = "",
    val internetProblem: Boolean = false,
)
