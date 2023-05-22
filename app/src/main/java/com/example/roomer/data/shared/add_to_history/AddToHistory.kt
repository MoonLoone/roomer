package com.example.roomer.data.shared.add_to_history

import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.data.room.entities.LocalRoom
import com.example.roomer.domain.model.entities.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

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
