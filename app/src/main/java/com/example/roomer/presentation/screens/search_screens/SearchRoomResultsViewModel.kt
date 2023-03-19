package com.example.roomer.presentation.screens.search_screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.data.repository.AuthRepositoryInterface
import com.example.roomer.domain.model.entities.Room
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
class SearchRoomResultsViewModel @Inject constructor(
    private val roomerRepository: AuthRepositoryInterface
) : ViewModel() {
    private val _rooms = MutableStateFlow(emptyList<Room>())
    val rooms: StateFlow<List<Room>> = _rooms
    private val _loadingStates = MutableStateFlow(LoadingStates.Loading)
    val loadingState = _loadingStates.asStateFlow()

    fun loadRooms(
        monthPriceFrom: String,
        monthPriceTo: String,
        bedroomsCount: String,
        bathroomsCount: String,
        housingType: String,
    ) = effect {
        delay(2000)
        _loadingStates.value = LoadingStates.Loading
        coroutineScope {
            val response = roomerRepository.getFilterRooms(
                monthPriceFrom,
                monthPriceTo,
                bedroomsCount,
                bathroomsCount,
                housingType,
            )
            if (response.isSuccessful) {
                _rooms.value = response.body() ?: listOf()
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
