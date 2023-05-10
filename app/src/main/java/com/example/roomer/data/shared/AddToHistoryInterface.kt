package com.example.roomer.data.shared

import com.example.roomer.data.room.entities.LocalCurrentUser
import com.example.roomer.data.room.entities.LocalRoom
import com.example.roomer.domain.model.entities.User

interface AddToHistoryInterface {

    fun addRoomToHistory(room: LocalRoom)

    fun addMateToHistory(user: User)
}
