package com.example.roomer.presentation.screens.search_screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.domain.model.entities.User
import com.example.roomer.domain.usecase.search.SearchUseCase
import com.example.roomer.utils.LoadingStates
import com.example.roomer.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
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
    private val searchUseCase = SearchUseCase(roomerRepository)
    fun loadRoommates(
        sex: String?,
        location: String?,
        ageFrom: String?,
        ageTo: String?,
        employment: String?,
        alcoholAttitude: String?,
        smokingAttitude: String?,
        sleepTime: String?,
        personalityType: String?,
        cleanHabits: String?,
        interests: List<String>?
    ) = effect {
        _loadingStates.value = LoadingStates.Loading
        searchUseCase.loadRoommates(
            sex,
            location,
            ageFrom,
            ageTo,
            employment,
            alcoholAttitude,
            smokingAttitude,
            sleepTime,
            personalityType,
            cleanHabits,
            interests
        ).collect {
            when (it) {
                is Resource.Internet -> {
                    _loadingStates.value = LoadingStates.Error
                }

                is Resource.Loading -> {
                    LoadingStates.Loading
                }

                is Resource.Success -> {
                    _roommates.value = it.data ?: listOf()
                    _loadingStates.value = LoadingStates.Success
                }

                else -> {
                    _loadingStates.value = LoadingStates.Error
                }
            }
        }
    }

    private fun effect(block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) { block() }
    }
}
