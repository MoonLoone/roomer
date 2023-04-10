package com.example.roomer.domain.model.entities

import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("id")
    val id: Int,
    @SerializedName("chat_id")
    val chatId: Int,
    @SerializedName("date_time")
    var dateTime: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("donor")
    val donor: User,
    @SerializedName("recipient")
    val recipient: User,
    @SerializedName("is_checked")
    var isChecked: Boolean,
)
