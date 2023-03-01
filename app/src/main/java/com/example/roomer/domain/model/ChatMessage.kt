package com.example.roomer.domain.model

import java.util.*

data class ChatMessage(
    var messageText: String,
    var messageSenderUser: String,
    var messageReceiverUser: String,
    var messageTime: Long = Date().time,
)