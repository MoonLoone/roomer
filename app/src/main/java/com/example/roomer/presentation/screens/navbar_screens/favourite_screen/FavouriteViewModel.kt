package com.example.roomer.presentation.screens.navbar_screens.favourite_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.data.room.entities.toRoom
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.usecase.favourite_screen.FavouriteScreenUseCase
import com.example.roomer.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val roomerRepository: RoomerRepositoryInterface
) : ViewModel() {

    private val _pagingData: MutableState<Flow<PagingData<Room>>> = mutableStateOf(emptyFlow())
    private val favouriteScreenUseCase = FavouriteScreenUseCase(roomerRepository)
    private val _state: MutableStateFlow<FavouriteScreenState> =
        MutableStateFlow(FavouriteScreenState())

    val pagingData: State<Flow<PagingData<Room>>> = _pagingData
    val state: StateFlow<FavouriteScreenState> = _state

    init {
        viewModelScope.launch {
            val response = roomerRepository.getFavouritesForUser()
            _pagingData.value = response.map { pagingData ->
                pagingData.map { localRoom ->
                    val room = localRoom.toRoom()
                    room.isLiked = true
                    room
                }
            }.cachedIn(viewModelScope)
        }
    }

    fun addToFavourite(room: Room) {
        viewModelScope.launch {
            favouriteScreenUseCase.addToFavourites(room).collect { result ->
                when (result) {
                    is Resource.Success -> {

                        _state.update { current ->
                            current.copy(
                                success = true,
                                isLoading = false,
                            )
                        }
                    }

                    is Resource.Loading -> _state.update { current ->
                        current.copy(isLoading = true)
                    }

                    else -> _state.update { current ->
                        current.copy(success = true, isLoading = false)
                    }
                }
            }
        }
    }

    fun deleteFromFavourite(room: Room) {
        viewModelScope.launch {
            favouriteScreenUseCase.deleteFromFavourites(room).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        favouriteScreenUseCase.deleteLocalFavourite(room)
                        _state.update { current ->
                            current.copy(
                                success = true,
                                isLoading = false,
                            )
                        }
                    }

                    is Resource.Loading -> _state.update { current ->
                        current.copy(isLoading = true)
                    }

                    else -> _state.update { current ->
                        current.copy(success = true, isLoading = false)
                    }
                }
            }
        }
    }

}
