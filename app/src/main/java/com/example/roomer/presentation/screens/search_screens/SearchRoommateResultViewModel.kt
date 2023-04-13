package com.example.roomer.presentation.screens.search_screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.domain.model.entities.User
import com.example.roomer.utils.LoadingStates
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SearchRoommateResultViewModel @Inject constructor(
    private val roomerRepository: RoomerRepositoryInterface
) : ViewModel() {
    private val _roommates = MutableStateFlow(emptyList<User>())
    val roommates: StateFlow<List<User>> = _roommates
    private val _loadingStates = MutableStateFlow(LoadingStates.Loading)
    val loadingState = _loadingStates.asStateFlow()
    fun loadRoommates(
        sex: String,
        employment: String,
        alcoholAttitude: String,
        smokingAttitude: String,
        sleepTime: String,
        personalityType: String,
        cleanHabits: String,
    ) = effect {
        delay(2000)
        _loadingStates.value = LoadingStates.Loading
        coroutineScope {
            val response = roomerRepository.getFilterRoommates(
                sex,
                employment,
                alcoholAttitude,
                smokingAttitude,
                sleepTime,
                personalityType,
                cleanHabits
            )
            if (response.isSuccessful) {
                _roommates.value = response.body() ?: listOf()
                _loadingStates.value = LoadingStates.Success
            } else {
                _loadingStates.value = LoadingStates.Error
            }
        }
    }

    private fun effect(block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) { block() }
    }
}
