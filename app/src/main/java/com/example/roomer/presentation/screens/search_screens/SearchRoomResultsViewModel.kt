package com.example.roomer.presentation.screens.search_screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.data.shared.HousingLikeInterface
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.usecase.search.SearchUseCase
import com.example.roomer.utils.LoadingStates
import com.example.roomer.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SearchRoomResultsViewModel @Inject constructor(
    private val roomerRepository: RoomerRepositoryInterface,
    val housingLike: HousingLikeInterface
) : ViewModel() {
    private val _rooms = MutableStateFlow(emptyList<Room>())
    val rooms: StateFlow<List<Room>> = _rooms
    private val _loadingStates = MutableStateFlow(LoadingStates.Loading)
    val loadingState = _loadingStates.asStateFlow()
    private val searchUseCase = SearchUseCase(roomerRepository)

    fun loadRooms(
        monthPriceFrom: String?,
        monthPriceTo: String?,
        location: String?,
        bedroomsCount: String?,
        bathroomsCount: String?,
        housingType: String?
    ) = effect {
        _loadingStates.value = LoadingStates.Loading
        searchUseCase.loadRooms(
            monthPriceFrom,
            monthPriceTo,
            location,
            bedroomsCount,
            bathroomsCount,
            housingType
        ).collect {
            when (it) {
                is Resource.Internet -> {
                    _loadingStates.value = LoadingStates.Error
                }

                is Resource.Loading -> {
                    LoadingStates.Loading
                }

                is Resource.Success -> {
                    _rooms.value = it.data ?: listOf()
                    _loadingStates.value = LoadingStates.Success
                }

                else -> {
                    _loadingStates.value = LoadingStates.Error
                }
            }
        }
    }

    private fun effect(block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) { block() }
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }
}
