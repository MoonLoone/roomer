package com.example.roomer.presentation.screens.entrance.signup.interests

data class InterestsScreenState(
    val isInterestsSent: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = "",
    val internetProblem: Boolean = false,
    val isInterestsLoaded: Boolean = false
)
