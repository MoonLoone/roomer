package com.example.roomer.presentation.screens.shared_screens.user_details_screen.comment_screen

import com.example.roomer.utils.ScreenState

data class CommentScreenState(
    override var success: Boolean = false,
    override var isLoading: Boolean = false,
    override var internetProblem: Boolean = false,
    override var error: String = "",
    var requestProblem: Boolean = false
) : ScreenState
