package com.example.roomer.domain.model.login_sign_up

import com.google.gson.annotations.SerializedName

data class LoginDto(
    @SerializedName("username") val email: String,
    @SerializedName("password") val password: String,
)
