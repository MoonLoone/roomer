package com.example.roomer.domain.model.entities

import com.google.gson.annotations.SerializedName

data class Follow(
    @SerializedName("id") val id: Int,
    @SerializedName("following") val user: User
)
