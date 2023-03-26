package com.example.roomer.local.tables

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.roomer.domain.model.entities.User

@Entity(tableName = "favourite")
data class LocalFavourite (
    @PrimaryKey val id: Int,
    val monthPrice: Int = 0,
    val host: User? = null,
    val description: String = "",
    val photo: String = "",
    val bathroomsCount: Int = 0,
    val bedroomsCount: Int = 0,
    val housingType: String = "",
    val sharingType: String = "",
    val location: String = "Ordinary location",
    val title: String = "Ordinary location",
    val isLiked: Boolean = true,
)