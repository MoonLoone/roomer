package com.example.roomer.domain.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    @SerializedName("id")
    val userId: Int = -1,
    @SerializedName("first_name")
    val firstName: String = "",
    @SerializedName("last_name")
    val lastName: String = "",
    val avatar: String = "",
    val employment: String = "",
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
    var rating: Double = 0.0,
)
