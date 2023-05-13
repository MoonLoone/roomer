package com.example.roomer.presentation.screens.profile_nested_screens.follows

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.domain.model.entities.Follow
import com.example.roomer.domain.model.entities.User
import com.example.roomer.domain.usecase.profile_nested_screens.FollowScreenUseCase
import com.example.roomer.utils.SpManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowScreenViewModel @Inject constructor(
    roomerRepositoryInterface: RoomerRepositoryInterface,
    application: Application
) :
    AndroidViewModel(application) {

    private val _follows: MutableState<List<Follow>> = mutableStateOf(emptyList())
    private val _state: MutableStateFlow<FollowsScreenState> =
        MutableStateFlow(FollowsScreenState())
    private val followScreenUseCase = FollowScreenUseCase(roomerRepositoryInterface)

    private val userToken = SpManager().getSharedPreference(
        getApplication<Application>().applicationContext,
        SpManager.Sp.TOKEN,
        null
    )

    val follows: State<List<Follow>> = _follows
    val state: StateFlow<FollowsScreenState> = _state


    init {
        viewModelScope.launch(Dispatchers.IO) {
            val user = followScreenUseCase.getCurrentUser()
            _follows.value = followScreenUseCase.getFollows(user.userId, userToken!!)
        }
    }

    fun deleteFollow(followId: Int) {

    }

}
