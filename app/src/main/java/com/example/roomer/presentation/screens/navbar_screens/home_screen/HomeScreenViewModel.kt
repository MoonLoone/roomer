package com.example.roomer.presentation.screens.navbar_screens.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.data.shared.HousingLike
import com.example.roomer.data.shared.HousingLikeInterface
import com.example.roomer.domain.model.entities.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val roomerRepository: RoomerRepositoryInterface,
    val housingLike: HousingLikeInterface,
) : ViewModel() {
    val testRooms = listOf(
        Room(0),
        Room(1),
        Room(2),
        Room(3),
        Room(4),
        Room(5)
    )
    fun addToFavourites(room: Room) {
        viewModelScope.launch {
            roomerRepository.addLocalFavourite(room)
        }
    }
    fun removeLocalFavourite(room: Room) {
        viewModelScope.launch {
            roomerRepository.deleteLocalFavourite(room)
        }
    }
}
