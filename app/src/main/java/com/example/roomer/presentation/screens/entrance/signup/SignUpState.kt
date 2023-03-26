package com.example.roomer.presentation.screens.entrance.signup

data class SignUpState(
    val isValid: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
)
