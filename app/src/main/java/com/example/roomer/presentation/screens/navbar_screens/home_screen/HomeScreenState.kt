package com.example.roomer.presentation.screens.navbar_screens.home_screen

import com.example.roomer.utils.ScreenState

data class HomeScreenState(
    var unauthorized: Boolean = false,
    var emptyRecommendedRooms: Boolean = false,
    var emptyRecommendedMates: Boolean = false,
    var emptyHistory: Boolean = false,
    override var success: Boolean = false,
    override var isLoading: Boolean = false,
    override var error: String = "Undefined error",
    override var internetProblem: Boolean = false,
): ScreenState
