package com.example.roomer.domain.model.signupthree

import com.google.gson.annotations.SerializedName

data class SignUpThreeModel(
    @SerializedName("sleep_time") val sleepTime: String,
    @SerializedName("alcohol_attitude") val alcoholAttitude: String,
    @SerializedName("smoking_attitude") val smokingAttitude: String,
    @SerializedName("personality_type") val personalityType: String,
    @SerializedName("clean_habits") val cleanHabits: String,
)
