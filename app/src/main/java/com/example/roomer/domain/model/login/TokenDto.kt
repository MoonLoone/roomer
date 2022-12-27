package com.example.roomer.domain.model.login

import com.google.gson.annotations.SerializedName

data class TokenDto(
    @SerializedName("auth_token") val token: String,
)
