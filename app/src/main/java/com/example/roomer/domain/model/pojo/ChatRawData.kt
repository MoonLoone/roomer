package com.example.roomer.domain.model.pojo

import com.example.roomer.domain.model.entities.Message
import com.google.gson.annotations.SerializedName

data class ChatRawData(
    @SerializedName("results")
    val results: List<Message>? = null
) : RawData()
