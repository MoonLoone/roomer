package com.example.roomer.domain.model.login_sign_up

import com.example.roomer.domain.model.login_sign_up.interests.InterestModel
import com.google.gson.annotations.SerializedName

data class SignUpDataModel(
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("sex") val sex: String,
    @SerializedName("birth_date") val birthDate: String,
    @SerializedName("about_me") val aboutMe: String,
    @SerializedName("employment") val employment: String,
    @SerializedName("sleep_time") val sleepTime: String,
    @SerializedName("alcohol_attitude") val alcoholAttitude: String,
    @SerializedName("smoking_attitude") val smokingAttitude: String,
    @SerializedName("personality_type") val personalityType: String,
    @SerializedName("clean_habits") val cleanHabits: String,
    @SerializedName("interests") val interests: List<InterestModel>
)
