package com.example.roomer.data.shared.add_to_history

import com.example.roomer.data.room.entities.LocalRoom
import com.example.roomer.domain.model.entities.User

interface AddToHistoryInterface {

    fun addRoomToHistory(room: LocalRoom)

    fun addRoommateToHistory(user: User)
}
