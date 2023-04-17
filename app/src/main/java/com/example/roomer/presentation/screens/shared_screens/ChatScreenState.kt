package com.example.roomer.presentation.screens.shared_screens

import com.example.roomer.utils.ScreenState

data class ChatScreenState(
    val noMessages: Boolean = false
) : ScreenState {
    override var success: Boolean = false
    override var isLoading: Boolean = true
    override var error: String = "Undefined error"
    override var internetProblem: Boolean = false
}
