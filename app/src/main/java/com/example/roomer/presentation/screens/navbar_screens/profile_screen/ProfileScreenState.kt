package com.example.roomer.presentation.screens.navbar_screens.profile_screen

import com.example.roomer.utils.ScreenState

data class ProfileScreenState(
    override var success: Boolean = false,
    override var isLoading: Boolean = false,
    override var error: String = "",
    override var internetProblem: Boolean = false,
    val isLogout: Boolean = false
) : ScreenState
