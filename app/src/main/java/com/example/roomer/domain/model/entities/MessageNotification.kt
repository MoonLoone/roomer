package com.example.roomer.domain.model.entities

import com.google.gson.annotations.SerializedName

data class MessageNotification(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("message")
    val message: Message? = null,
)
