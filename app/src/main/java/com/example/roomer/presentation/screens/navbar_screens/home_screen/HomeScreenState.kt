package com.example.roomer.presentation.screens.navbar_screens.home_screen

import com.example.roomer.utils.ScreenState

data class HomeScreenState(
    override var success: Boolean = false,
    override var isLoading: Boolean = false,
    override var error: String = "Undefined error",
    override var internetProblem: Boolean = false,
): ScreenState
