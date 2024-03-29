package com.example.roomer.domain.model.login_sign_up

import com.google.gson.annotations.SerializedName

data class SignUpModel(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String,
    @SerializedName("email") val email: String
)
