package com.example.roomer.domain.model.signupone

import com.google.gson.annotations.SerializedName

data class SignUpOneModel(
    @SerializedName("birth_date") val birthDate: String,
    @SerializedName("sex") val sex: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
)
