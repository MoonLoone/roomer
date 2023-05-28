package com.example.roomer.presentation.screens.entrance.signup.sign_up_primary_screen

data class SignUpScreenState(
    val success: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = "",
    val internetProblem: Boolean = false,
    val isEmailError: Boolean = false,
    val isPasswordError: Boolean = false,
    val isConfPasswordError: Boolean = false,
    val isUsernameError: Boolean = false
)
