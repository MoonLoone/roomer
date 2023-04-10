package com.example.roomer.room.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.example.roomer.domain.model.entities.User


data class RoomWithHost(
    @Embedded val room: LocalRoom,
    @Relation(
         parentColumn = "hostId",
         entityColumn = "userId"
     )
     val host: User?
)
