package com.example.roomer.presentation.screens.navbar_screens

data class MessengerScreenState(
    val success: Boolean = false,
    val isLoading: Boolean = true,
    val error: String = "",
    val internetProblem: Boolean = false,
)
