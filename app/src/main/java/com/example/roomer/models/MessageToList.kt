package com.example.roomer.models

import com.example.roomer.utils.Screens

data class MessageToList(
    val userAvatarPath: String,
    val messageDate: String,
    val messageCutText: String,
    val username: String,
    var isRead: Boolean,
    var unreadMessages: Int,
    val navigateToMessage: () -> Unit,
)
