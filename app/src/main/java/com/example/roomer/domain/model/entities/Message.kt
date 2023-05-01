package com.example.roomer.domain.model.entities

import com.example.roomer.data.room.entities.LocalMessage
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
    var isChecked: Boolean
)

fun Message.toLocalMessage() = LocalMessage(
    id, chatId, dateTime, text, donor.userId, recipient.userId, isChecked
)