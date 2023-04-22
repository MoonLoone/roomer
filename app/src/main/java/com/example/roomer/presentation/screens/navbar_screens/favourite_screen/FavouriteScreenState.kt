package com.example.roomer.presentation.screens.navbar_screens.favourite_screen

import com.example.roomer.utils.ScreenState

data class FavouriteScreenState(
    override var success: Boolean = false,
    override var isLoading: Boolean = false,
    override var error: String = "",
    override var internetProblem: Boolean = false
): ScreenState
