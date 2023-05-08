package com.example.roomer.data.shared

import com.example.roomer.data.room.entities.LocalCurrentUser
import com.example.roomer.data.room.entities.LocalRoom

interface AddToHistoryInterface {

    fun addRoomToHistory(room: LocalRoom)

    fun addMateToHistory(user: LocalCurrentUser)
}
