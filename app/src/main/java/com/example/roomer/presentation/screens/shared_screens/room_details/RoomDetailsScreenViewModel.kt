package com.example.roomer.presentation.screens.shared_screens.room_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.data.shared.add_to_history.AddToHistory
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.toLocalRoom
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class RoomDetailsScreenViewModel @Inject constructor(
    private val addToHistory: AddToHistory,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val roomString: String? = savedStateHandle["room"]
            val room = Gson().fromJson(roomString, Room::class.java)
            room?.let {
                addToHistory.roomerRepositoryInterface.addRoomToLocalHistory(room.toLocalRoom())
            }
        }
    }
}
