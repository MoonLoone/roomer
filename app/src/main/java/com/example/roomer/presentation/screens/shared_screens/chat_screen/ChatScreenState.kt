package com.example.roomer.presentation.screens.shared_screens.chat_screen

import com.example.roomer.utils.ScreenState

data class ChatScreenState(
    val emptyMessagesList: Boolean = false,
    val socketConnected: Boolean = false,
    override var success: Boolean = false,
    override var isLoading: Boolean = false,
    override var error: String = "Undefined error",
    override var internetProblem: Boolean = false
) : ScreenState
