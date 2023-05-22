package com.example.roomer.presentation.screens.navbar_screens.home_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.data.room.entities.HistoryItem
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.User
import com.example.roomer.domain.usecase.navbar_screens.HomeScreenUseCase
import com.example.roomer.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val roomerRepository: RoomerRepositoryInterface
) : ViewModel() {

    private val homeScreenUseCase = HomeScreenUseCase(roomerRepository)
    private val _state: MutableStateFlow<HomeScreenState> = MutableStateFlow(HomeScreenState())
    private val _history: MutableStateFlow<List<HistoryItem>> = MutableStateFlow(emptyList())
    private val _currentUser: MutableState<User> = mutableStateOf(User())
    private val _recommendedRooms: MutableStateFlow<List<Room>> = MutableStateFlow(emptyList())
    private val _recommendedMates: MutableStateFlow<List<User>> = MutableStateFlow(emptyList())

    val state: StateFlow<HomeScreenState> = _state
    val history: StateFlow<List<HistoryItem>> = _history
    val currentUser: State<User> = _currentUser
    val recommendedRooms: StateFlow<List<Room>> = _recommendedRooms
    val recommendedMates: StateFlow<List<User>> = _recommendedMates

    init {
        viewModelScope.launch {
            _state.update { current ->
                current.copy(isLoading = true)
            }
            getCurrentUser()
            getRecommendedRoommates()
            getRecommendedRooms()
            getRecently()
            _state.update { current ->
                current.copy(isLoading = false, success = true)
            }
        }
    }

    private suspend fun getCurrentUser() {
        homeScreenUseCase.getCurrentUserInfo(_currentUser).collect { result ->
            if (result is Resource.Error) {
                _state.update { current ->
                    current.copy(unauthorized = true)
                }
            }
        }
    }

    private suspend fun getRecommendedRooms() {
        homeScreenUseCase.getRecommendedRooms(_recommendedRooms, currentUser.value)
            .collect { result ->
                if (result is Resource.Error) {
                    _state.update { current ->
                        current.copy(emptyRecommendedRooms = true)
                    }
                }
            }
    }

    private suspend fun getRecommendedRoommates() {
        homeScreenUseCase.getRecommendedRoommates(_recommendedMates, currentUser.value)
            .collect { result ->
                if (result is Resource.Error) {
                    _state.update { current ->
                        current.copy(emptyRecommendedMates = true)
                    }
                }
            }
    }

    private suspend fun getRecently() {
        homeScreenUseCase.getRecently(_history).collect { result ->
            if (result is Resource.Error) {
                _state.update { current ->
                    current.copy(emptyHistory = true)
                }
            }
        }
    }
}
