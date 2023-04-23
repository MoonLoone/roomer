package com.example.roomer.domain.model.pojo

import com.example.roomer.domain.model.entities.Room
import com.google.gson.annotations.SerializedName

data class Favourite(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("housing")
    val housing: Room? = null,
)
