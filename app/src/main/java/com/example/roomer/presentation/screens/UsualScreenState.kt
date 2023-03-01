package com.example.roomer.presentation.screens

data class UsualScreenState(
    val success: Boolean = false,
    val error: String = "",
    val isLoading: Boolean = false,
    val internetProblem: Boolean = false,
)
