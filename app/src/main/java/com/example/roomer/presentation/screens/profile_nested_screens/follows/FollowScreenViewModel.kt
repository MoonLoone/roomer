package com.example.roomer.presentation.screens.profile_nested_screens.follows

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.domain.model.entities.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class FollowScreenViewModel @Inject constructor(roomerRepositoryInterface: RoomerRepositoryInterface) :
    ViewModel() {

    private val _follows: MutableState<List<User>> = mutableStateOf(emptyList())
    private val _state: MutableStateFlow<FollowsScreenState> =
        MutableStateFlow(FollowsScreenState())

    val follows: State<List<User>> = _follows
    val state: StateFlow<FollowsScreenState> = _state

    init {

    }

    fun deleteFollow(followId: Int){

    }

}
