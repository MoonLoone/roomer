package com.example.roomer.data.room.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.roomer.domain.model.entities.BaseEntity
import com.example.roomer.domain.model.entities.Room

@Entity(tableName = "favourite")
data class LocalRoom(
    @PrimaryKey
    val roomId: Int,
    val monthPrice: Int,
    val hostId: Int,
    val description: String,
    val fileContent: List<Room.Photo>?,
    val bathroomsCount: Int,
    val bedroomsCount: Int,
    val housingType: String,
    val sharingType: String,
    val location: String,
    val title: String,
    val isLiked: Boolean
): BaseEntity()

fun LocalRoom.toRoom(): Room {
    val room = Room(
        monthPrice,
        null,
        description,
        fileContent,
        bathroomsCount,
        bedroomsCount,
        housingType,
        sharingType,
        location,
        title,
        isLiked
    )
    room.id = roomId
    return room
}