package com.example.roomer.data.room.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.roomer.domain.model.entities.Room

@Entity(
    tableName = "history",
    indices = [Index(value = ["userId"], unique = true), Index(value = ["roomId"], unique = true)]
)
data class HistoryItem(
    @PrimaryKey(autoGenerate = true)
    var historyId: Int = 0,
    @Embedded
    val room: LocalRoom? = null,
    @Embedded val user: LocalCurrentUser? = null,
)
