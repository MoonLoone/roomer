package com.example.roomer.presentation.screens.navbar_screens.favourite_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.domain.model.entities.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val roomerRepository: RoomerRepositoryInterface,
) : ViewModel() {

    private val _favourites: MutableState<List<Room>> =
        mutableStateOf(emptyList())
    val favourites: State<List<Room>> = _favourites

    init {
        getLocalFavourites()
    }

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
    private fun getLocalFavourites() {
        viewModelScope.launch {
            roomerRepository.getLocalFavourites().collect {
                _favourites.value = it
            }
        }
    }
}
