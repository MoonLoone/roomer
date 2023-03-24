package com.example.roomer.utils

open class ScreenState(
    val success: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = "",
    val internetProblem: Boolean = false,
)