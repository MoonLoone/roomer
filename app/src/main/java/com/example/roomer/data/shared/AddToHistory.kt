package com.example.roomer.data.shared

import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.data.room.entities.LocalRoom
import com.example.roomer.domain.model.entities.User
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddToHistory @Inject constructor(
    val roomerRepositoryInterface: RoomerRepositoryInterface
) : AddToHistoryInterface {

    override fun addRoomToHistory(room: LocalRoom) {
        CoroutineScope(Dispatchers.IO).launch {
            roomerRepositoryInterface.addRoomToLocalHistory(room)
        }
    }

    override fun addRoommateToHistory(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            roomerRepositoryInterface.addRoommateToLocalHistory(user)
        }
    }
}
