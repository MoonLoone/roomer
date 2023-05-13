package com.example.roomer.presentation.screens.profile_nested_screens.follows

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.data.shared.follow.FollowManipulate
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
    application: Application,
    private val followManipulate: FollowManipulate
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
    )?:""

    private val currentUser: MutableState<User> = mutableStateOf(User())
    val follows: State<List<Follow>> = _follows
    val state: StateFlow<FollowsScreenState> = _state


    init {
        viewModelScope.launch{
            currentUser.value = followScreenUseCase.getCurrentUser()
            _follows.value = followScreenUseCase.getFollows(currentUser.value.userId, userToken!!)
        }
    }

    fun deleteFollow(followId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            followManipulate.deleteFollow(currentUser.value.userId, followId, userToken)
        }
    }

}
