package com.example.roomer.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "interest")
data class LocalInterest(
    @PrimaryKey
    val interestId: Int,
    val interest: String
)
