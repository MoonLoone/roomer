package com.example.roomer.domain.model.login

import com.google.gson.annotations.SerializedName

data class LoginDto(
    @SerializedName("username") val email: String,
    @SerializedName("password") val password: String
)
