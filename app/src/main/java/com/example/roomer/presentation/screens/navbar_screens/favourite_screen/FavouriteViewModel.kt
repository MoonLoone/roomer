package com.example.roomer.presentation.screens.navbar_screens.favourite_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.data.room.entities.toRoom
import com.example.roomer.data.shared.HousingLikeInterface
import com.example.roomer.domain.model.entities.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val roomerRepository: RoomerRepositoryInterface,
    val housingLike: HousingLikeInterface
) : ViewModel() {

    val pagingData: MutableStateFlow<Flow<PagingData<Room>>> = MutableStateFlow(emptyFlow())

    init {
        viewModelScope.launch {
            val response = roomerRepository.getFavouritesForUser()
            pagingData.value = response.map { pagingData ->
                pagingData.map { localRoom ->
                    localRoom.toRoom()
                }
            }.cachedIn(viewModelScope)
        }
    }
}
