package com.example.roomer.domain.model.signup.signup_two

import com.google.gson.annotations.SerializedName

data class SignUpTwoModel(
    @SerializedName("about_me") val aboutMe: String,
    @SerializedName("employment") val employment: String,
)
