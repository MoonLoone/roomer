package com.example.roomer.presentation.screens.entrance.signup.interests_screen

data class InterestsScreenState(
    val success: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = "",
    val internetProblem: Boolean = false,
    val isInterestsSent: Boolean = false,
    val isInterestsLoaded: Boolean = false
)
