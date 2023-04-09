package com.example.roomer.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite")
data class LocalRoom(
    @PrimaryKey
    val roomId: Int,
    val monthPrice: Int,
    val hostId: Long,
    val description: String,
    val photo: String,
    val bathroomsCount: Int,
    val bedroomsCount: Int,
    val housingType: String,
    val sharingType: String,
    val location: String,
    val title: String,
    val isLiked: Boolean
)