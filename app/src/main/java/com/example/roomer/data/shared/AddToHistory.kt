package com.example.roomer.data.shared

import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.data.room.entities.LocalCurrentUser
import com.example.roomer.data.room.entities.LocalRoom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddToHistory(val roomerRepositoryInterface: RoomerRepositoryInterface): AddToHistoryInterface {

    override fun addRoomToHistory(room: LocalRoom) {
        CoroutineScope(Dispatchers.IO).launch {
            roomerRepositoryInterface.addRoomToLocalHistory(room)
        }
    }

    override fun addMateToHistory(user: LocalCurrentUser) {
        CoroutineScope(Dispatchers.IO).launch {
            roomerRepositoryInterface.addMateToLocalHistory(user)
        }
    }
}