package com.example.roomer.data.room.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.roomer.domain.model.entities.Room

@Entity(tableName = "history")
data class HistoryItem(
    @PrimaryKey val historyId: Int = 0,
    @Embedded val room: LocalRoom? = null,
    @Embedded val user: LocalCurrentUser? = null,
)
