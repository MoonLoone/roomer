package com.example.roomer.domain.model.entities

import com.example.roomer.data.room.entities.LocalMessage
import com.google.gson.annotations.SerializedName

data class Message(
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
) : BaseEntity()

fun Message.toLocalMessage(): LocalMessage {
    val local = LocalMessage(
        id,
        chatId,
        dateTime,
        text,
        donor.userId,
        recipient.userId,
        isChecked
    )
    local.page = page
    local.lastPage = lastPage
    return local
}
