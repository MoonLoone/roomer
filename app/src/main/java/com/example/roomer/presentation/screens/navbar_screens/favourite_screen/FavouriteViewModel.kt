package com.example.roomer.presentation.screens.navbar_screens.favourite_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.data.room.RoomerDatabase
import com.example.roomer.data.room.entities.toRoom
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.presentation.ui_components.shared.HousingLike
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val roomerRepository: RoomerRepositoryInterface,
    private val housingLike: HousingLike,
    private val roomerDatabase: RoomerDatabase
) : ViewModel() {

    private val _state: MutableStateFlow<FavouriteScreenState> = MutableStateFlow(
        FavouriteScreenState()
    )
    val state: StateFlow<FavouriteScreenState> = _state
    val pagingData: MutableStateFlow<Flow<PagingData<Room>>> = MutableStateFlow(emptyFlow())

    init {
        viewModelScope.launch {
            val currentUser = roomerRepository.getLocalCurrentUser()
            val response = roomerRepository.getFavourites(currentUser.userId)
            pagingData.value = response.map { it.map { it.toRoom() } }
        }
    }

    fun dislikeHousing(housingId: Int) {
        viewModelScope.launch {
            _state.value = FavouriteScreenState(isLoading = true)
            val currentUser = roomerRepository.getLocalCurrentUser()
            roomerDatabase.favourites.deleteById(housingId)
            housingLike.dislikeHousing(housingId, currentUser.userId)
            roomerRepository.pagingSource?.invalidate()
            _state.value = FavouriteScreenState(success = true)
        }
    }
}
