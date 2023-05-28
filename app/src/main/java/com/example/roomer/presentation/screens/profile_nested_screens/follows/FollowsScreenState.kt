package com.example.roomer.presentation.screens.profile_nested_screens.follows

import com.example.roomer.utils.ScreenState

data class FollowsScreenState(
    override var success: Boolean = false,
    override var isLoading: Boolean = false,
    override var error: String = "Undefined error",
    override var internetProblem: Boolean = false,
    val emptyFollowsList: Boolean = false
) : ScreenState
