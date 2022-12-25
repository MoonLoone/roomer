package com.example.roomer.api.dto

import com.google.gson.annotations.SerializedName

data class TokenDto(
    @SerializedName("auth_token") val token: String,
)
