package com.example.roomer.presentation.screens.navbar_screens.home_screen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.data.room.entities.HistoryItem
import com.example.roomer.data.shared.HousingLikeInterface
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.User
import com.example.roomer.domain.usecase.navbar_screens.HomeScreenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val roomerRepository: RoomerRepositoryInterface,
    val housingLike: HousingLikeInterface
) : ViewModel() {

    private val homeScreenUseCase = HomeScreenUseCase(roomerRepository)
    private val _state: MutableState<HomeScreenState> = mutableStateOf(HomeScreenState())
    private val _history: MutableStateFlow<List<HistoryItem>> = MutableStateFlow(emptyList())
    private val _currentUser: MutableState<User> = mutableStateOf(User())
    private val _recommendedRooms: MutableStateFlow<List<Room>> = MutableStateFlow(emptyList())
    private val _recommendedMates: MutableStateFlow<List<User>> = MutableStateFlow(emptyList())

    val state: State<HomeScreenState> = _state
    val history: StateFlow<List<HistoryItem>> = _history
    val currentUser: State<User> = _currentUser
    val recommendedRooms: StateFlow<List<Room>> = _recommendedRooms
    val recommendedMates: StateFlow<List<User>> = _recommendedMates

    init {
        viewModelScope.launch {
            homeScreenUseCase.getCurrentUserInfo(_currentUser)
            homeScreenUseCase.getRecommendedMates(_recommendedMates, currentUser.value)
            homeScreenUseCase.getRecommendedRooms(_recommendedRooms, currentUser.value)
            homeScreenUseCase.getRecently(_history)
        }
    }

}
