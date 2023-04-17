package com.example.roomer.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

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
    var rating: Double
)
