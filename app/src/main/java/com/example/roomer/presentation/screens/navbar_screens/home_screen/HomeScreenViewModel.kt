package com.example.roomer.presentation.screens.navbar_screens.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.data.repository.RoomerRepositoryInterface
import com.example.roomer.domain.model.entities.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val roomerRepository: RoomerRepositoryInterface
): ViewModel() {
    val testRooms = listOf(
        Room(id=1), Room(id=2), Room(id=3), Room(id=4), Room(id=5), Room(id=6)
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