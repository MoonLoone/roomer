package com.example.roomer.presentation.screens.navbar_screens.favourite_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.usecase.navbar_screens.FavouriteUseCase
import com.example.roomer.presentation.ui_components.shared.HousingLike
import com.example.roomer.utils.Constants
import com.example.roomer.utils.Resource
import com.example.roomer.utils.RoomerPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val roomerRepository: RoomerRepositoryInterface,
    private val housingLike: HousingLike,
) : ViewModel() {

    private val _state: MutableStateFlow<FavouriteScreenState> = MutableStateFlow(FavouriteScreenState())
    val state: StateFlow<FavouriteScreenState> = _state
    var pagingData: Flow<PagingData<Room>>? = null
    private val favouritesUseCase = FavouriteUseCase(roomerRepository)

    fun getFavourites() {
        viewModelScope.launch {
            if (pagingData == null) {
                val currentUser = roomerRepository.getLocalCurrentUser()
                pagingData = Pager(
                    PagingConfig(
                        pageSize = Constants.Chat.PAGE_SIZE,
                        maxSize = Constants.Chat.CASH_SIZE,
                        initialLoadSize = Constants.Chat.INITIAL_SIZE
                    )
                ) {
                    RoomerPagingSource { offset: Int, limit: Int ->
                        var items = listOf<Room>()
                        favouritesUseCase(currentUser.userId, offset, limit).collect { resource ->
                            when (resource) {
                                is Resource.Success -> {
                                    _state.value = FavouriteScreenState(success = true)
                                    items = resource.data ?: emptyList()
                                }
                                is Resource.Loading -> {
                                    _state.value = FavouriteScreenState(isLoading = true)
                                }
                                is Resource.Internet -> {
                                    _state.value = FavouriteScreenState(internetProblem = true)
                                }
                                else -> {
                                    _state.value =
                                        FavouriteScreenState(error = resource.message ?: "")
                                }
                            }
                        }
                        items
                    }
                }.flow
                _state.value = FavouriteScreenState(isLoading = true)
            }
        }
    }

    fun likeHousing(housingId: Int) {
        viewModelScope.launch {
            val currentUser = roomerRepository.getLocalCurrentUser()
            housingLike.likeHousing(housingId, currentUser.userId)
        }
    }

    fun dislikeHousing(housingId: Int) {
        viewModelScope.launch {
            val currentUser = roomerRepository.getLocalCurrentUser()
            housingLike.dislikeHousing(housingId, currentUser.userId)
        }
    }

}
