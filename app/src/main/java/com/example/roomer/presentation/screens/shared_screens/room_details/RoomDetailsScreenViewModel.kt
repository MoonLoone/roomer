package com.example.roomer.presentation.screens.shared_screens.room_details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.data.shared.add_to_history.AddToHistory
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.toLocalRoom
import com.example.roomer.domain.usecase.shared_screens.RoomDetailsUseCase
import com.example.roomer.utils.Constants
import com.example.roomer.utils.Resource
import com.example.roomer.utils.SpManager
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class RoomDetailsScreenViewModel @Inject constructor(
    private val addToHistory: AddToHistory,
    private val savedStateHandle: SavedStateHandle,
    val roomerRepositoryInterface: RoomerRepositoryInterface,
    application: Application
) : AndroidViewModel(application) {

    private val _state: MutableStateFlow<RoomDetailsScreenState> =
        MutableStateFlow(RoomDetailsScreenState())
    private val roomDetailsUseCase = RoomDetailsUseCase(roomerRepositoryInterface)
    private val userToken = SpManager().getSharedPreference(
        getApplication<Application>().applicationContext,
        SpManager.Sp.TOKEN,
        null
    ) ?: ""

    val state: StateFlow<RoomDetailsScreenState> = _state

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val roomString: String? = savedStateHandle["room"]
            val room = Gson().fromJson(roomString, Room::class.java)
            checkIsFavourite(room.id)
            room?.let {
                addToHistory.roomerRepositoryInterface.addRoomToLocalHistory(room.toLocalRoom())
                if (room.isLiked) {
                    _state.update { current ->
                        current.copy(
                            isFavourite = true
                        )
                    }
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
                                isFavourite = true
                            )
                        }
                    }

                    is Resource.Loading -> _state.update { current ->
                        current.copy(isLoading = true)
                    }

                    is Resource.Internet -> _state.update { current ->
                        current.copy(isLoading = false, success = false, internetProblem = true)
                    }

                    else -> _state.update { current ->
                        current.copy(
                            success = false,
                            isLoading = false,
                            error = Constants.Favourites.ADD_FAVOURITE_ERROR
                        )
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
                                isFavourite = false
                            )
                        }
                    }

                    is Resource.Internet -> _state.update { current ->
                        current.copy(isLoading = false, success = false, internetProblem = true)
                    }

                    else -> _state.update { current ->
                        current.copy(
                            success = false,
                            isLoading = false,
                            error = Constants.Favourites.DELETE_FAVOURITE_ERROR
                        )
                    }
                }
            }
        }
    }

    private suspend fun checkIsFavourite(housingId: Int) {
        val user = roomerRepositoryInterface.getLocalCurrentUser()
        roomDetailsUseCase.checkIsFavourite(user.userId, housingId, userToken).collect { result ->
            when (result) {
                is Resource.Success -> {
                    _state.update { current ->
                        current.copy(
                            success = true,
                            isLoading = false,
                            isFavourite = true
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
