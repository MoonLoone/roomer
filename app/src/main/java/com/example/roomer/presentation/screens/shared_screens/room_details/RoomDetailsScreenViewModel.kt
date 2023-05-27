package com.example.roomer.presentation.screens.shared_screens.room_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.data.shared.add_to_history.AddToHistory
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.toLocalRoom
import com.example.roomer.domain.usecase.shared_screens.RoomDetailsUseCase
import com.example.roomer.presentation.screens.navbar_screens.favourite_screen.FavouriteScreenState
import com.example.roomer.utils.Resource
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class RoomDetailsScreenViewModel @Inject constructor(
    private val addToHistory: AddToHistory,
    private val savedStateHandle: SavedStateHandle,
    val roomerRepositoryInterface: RoomerRepositoryInterface,
) : ViewModel() {

    private val _state: MutableStateFlow<RoomDetailsScreenState> =
        MutableStateFlow(RoomDetailsScreenState())
    private val roomDetailsUseCase = RoomDetailsUseCase(roomerRepositoryInterface)

    val state: StateFlow<RoomDetailsScreenState> = _state

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val roomString: String? = savedStateHandle["room"]
            val room = Gson().fromJson(roomString, Room::class.java)
            room?.let {
                addToHistory.roomerRepositoryInterface.addRoomToLocalHistory(room.toLocalRoom())
                if (room.isLiked) _state.update { current ->
                    current.copy(
                        isFavourite = true,
                    )
                }
            }
        }
    }

    fun addToFavourite(room: Room) {
        viewModelScope.launch {
            roomDetailsUseCase.addToFavourites(room).collect { result ->
                when (result) {
                    is Resource.Success -> {

                        _state.update { current ->
                            current.copy(
                                success = true,
                                isLoading = false,
                                isFavourite = true,
                            )
                        }
                    }

                    is Resource.Loading -> _state.update { current ->
                        current.copy(isLoading = true)
                    }

                    else -> _state.update { current ->
                        current.copy(success = true, isLoading = false)
                    }
                }
            }
        }
    }

    fun deleteFromFavourite(room: Room) {
        viewModelScope.launch {
            roomDetailsUseCase.deleteFromFavourites(room).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.update { current ->
                            current.copy(
                                success = true,
                                isLoading = false,
                            )
                        }
                    }

                    is Resource.Loading -> _state.update { current ->
                        current.copy(isLoading = true)
                    }

                    else -> _state.update { current ->
                        current.copy(success = true, isLoading = false)
                    }
                }
            }
        }
    }

}
