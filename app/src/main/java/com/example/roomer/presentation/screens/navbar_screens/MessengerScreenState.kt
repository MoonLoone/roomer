package com.example.roomer.presentation.screens.navbar_screens

import com.example.roomer.utils.ScreenState

data class MessengerScreenState(
    val noChats: Boolean = false,
    override var success: Boolean = false,
    override var isLoading: Boolean = true,
    override var error: String = "Unresolved error",
    override var internetProblem: Boolean = false
) : ScreenState
