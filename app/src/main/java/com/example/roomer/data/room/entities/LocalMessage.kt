package com.example.roomer.data.room.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.roomer.domain.model.entities.BaseEntity
import com.example.roomer.domain.model.entities.Message
import com.example.roomer.domain.model.entities.User
import com.google.gson.annotations.SerializedName

@Entity(tableName = "messages")
data class LocalMessage(
    @PrimaryKey(autoGenerate = true)
    val messageId: Int = -1,
    val chatId: Int = -1,
    var dateTime: String = "",
    val text: String = "",
    val donorId: Int = -1,
    val recipientId: Int = -1,
    var isChecked: Boolean = false,
) : BaseEntity(messageId)

fun LocalMessage.toMessage(): Message {
    val message = Message(
        chatId, dateTime, text, User(donorId), User(recipientId), isChecked
    )
    message.id = messageId
    return message
}
