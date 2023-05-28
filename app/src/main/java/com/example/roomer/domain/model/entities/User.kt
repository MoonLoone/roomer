package com.example.roomer.domain.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.roomer.data.room.entities.LocalCurrentUser
import com.example.roomer.domain.model.login_sign_up.InterestModel
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    @SerializedName("id")
    val userId: Int = -1,
    @SerializedName("first_name") val firstName: String = "",
    @SerializedName("last_name") val lastName: String = "",
    @SerializedName("avatar") val avatar: String = "",
    @SerializedName("employment") val employment: String = "",
    @SerializedName("sex") val sex: String = "",
    @SerializedName("alcohol_attitude") val alcoholAttitude: String = "",
    @SerializedName("smoking_attitude") val smokingAttitude: String = "",
    @SerializedName("sleep_time") val sleepTime: String = "",
    @SerializedName("personality_type") val personalityType: String = "",
    @SerializedName("clean_habits") val cleanHabits: String = "",
    var rating: Double = 0.0,
    @SerializedName("interests") val interests: List<InterestModel>? = null,
    @SerializedName("city") val city: String? = null,
    @SerializedName("birth_date") val birthDate: String? = null,
    @SerializedName("about_me") val aboutMe: String? = null
)

fun User.toLocalUser() = LocalCurrentUser(
    userId,
    firstName,
    lastName,
    avatar,
    employment,
    sex,
    alcoholAttitude,
    smokingAttitude,
    sleepTime,
    personalityType,
    cleanHabits,
    rating,
    interests,
    city,
    birthDate,
    aboutMe
)
