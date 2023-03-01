package com.example.roomer.domain.model.signup.signup_one

import com.google.gson.annotations.SerializedName

data class SignUpOneModel(
    @SerializedName("birth_date") val birthDate: String,
    @SerializedName("sex") val sex: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
)
