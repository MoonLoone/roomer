package com.example.roomer.presentation.screens

data class UsualScreenState(
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val isLoading: Boolean = false,
    val isInternetProblem: Boolean = false
)
