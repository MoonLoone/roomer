package com.example.roomer.presentation.screens.entrance.signup

import com.example.roomer.utils.ScreenState

data class SignUpScreenState(
    val success: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = "",
    val internetProblem: Boolean = false,
    val isEmailError: Boolean = false,
    val isPasswordError: Boolean = false,
    val isConfPasswordError: Boolean = false,
    val isUsernameError: Boolean = false,
)
