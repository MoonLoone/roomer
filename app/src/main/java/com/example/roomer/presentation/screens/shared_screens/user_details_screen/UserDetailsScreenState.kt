package com.example.roomer.presentation.screens.shared_screens.user_details_screen

import com.example.roomer.utils.ScreenState

data class UserDetailsScreenState(
    override var success: Boolean = false,
    override var isLoading: Boolean = false,
    override var error: String = "",
    override var internetProblem: Boolean = false,
    var isFollow: Boolean = false
) : ScreenState
