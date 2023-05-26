package com.example.roomer.presentation.screens.profile_nested_screens.account_screen

import com.example.roomer.utils.ScreenState

data class AccountScreenState(
    override val success: Boolean = false,
    override val isLoading: Boolean = false,
    override val error: String = "",
    override val internetProblem: Boolean = false
): ScreenState