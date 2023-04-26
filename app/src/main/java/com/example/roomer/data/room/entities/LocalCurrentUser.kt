package com.example.roomer.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.roomer.domain.model.login_sign_up.InterestModel

@Entity(tableName = "currentUser")
data class LocalCurrentUser(
    @PrimaryKey
    val userId: Int,
    val firstName: String,
    val lastName: String,
    val avatar: String,
    val employment: String,
    val sex: String,
    val alcoholAttitude: String,
    val smokingAttitude: String,
    val sleepTime: String,
    val personalityType: String,
    val cleanHabits: String,
    var rating: Double,
    val interests: List<InterestModel>?,
    val city: String?,
    val birthDate: String?,
    val aboutMe: String?
)
