package com.example.roomer.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.models.UsersFilterInfo
import com.example.roomer.repository.RoomerRepository
import com.example.roomer.utils.LoadingStates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchRoommateResultViewModel : ViewModel() {
    private val _roommates = MutableStateFlow(emptyList<UsersFilterInfo>())
    val roommates: StateFlow<List<UsersFilterInfo>> = _roommates
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
        _loadingStates.value = LoadingStates.Loading
        coroutineScope {
            val response = RoomerRepository.getFilterRoommates(
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