package com.example.roomer.data.room.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.User

data class RoomWithHost(
    @Embedded val room: LocalRoom,
    @Relation(
        parentColumn = "hostId",
        entityColumn = "userId"
    )
    val host: User?
)

fun RoomWithHost.toRoom() = Room(
    room.monthPrice,
    host,
    room.description,
    room.fileContent,
    room.bathroomsCount,
    room.bedroomsCount,
    room.housingType,
    room.sharingType,
    room.location,
    room.title,
    room.isLiked
)
