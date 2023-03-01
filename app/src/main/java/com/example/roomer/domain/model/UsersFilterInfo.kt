package com.example.roomer.domain.model

import com.google.gson.annotations.SerializedName

data class UsersFilterInfo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("first_name")
    val firstName: String = "",
    @SerializedName("last_name")
    val lastName: String = "",
    @SerializedName("avatar")
    val avatar: String = "",
    @SerializedName("employment")
    val employment: String = "",
    @SerializedName("sex")
    val sex: String = "",
    @SerializedName("alcohol_attitude")
    val alcoholAttitude: String = "",
    @SerializedName("smoking_attitude")
    val smokingAttitude: String = "",
    @SerializedName("sleep_time")
    val sleepTime: String = "",
    @SerializedName("personality_type")
    val personalityType: String = "",
    @SerializedName("clean_habits")
    val cleanHabits: String = "",
)
