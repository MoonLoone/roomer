package com.example.roomer.utils

interface ScreenState {
    val success: Boolean
    val isLoading: Boolean
    val error: String
    val internetProblem: Boolean
}
