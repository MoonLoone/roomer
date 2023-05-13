package com.example.roomer.presentation.screens.profile_nested_screens.follows

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.data.shared.follow.FollowManipulate
import com.example.roomer.domain.model.entities.Follow
import com.example.roomer.domain.model.entities.User
import com.example.roomer.domain.usecase.profile_nested_screens.FollowScreenUseCase
import com.example.roomer.utils.Constants
import com.example.roomer.utils.Resource
import com.example.roomer.utils.SpManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class FollowScreenViewModel @Inject constructor(
    roomerRepositoryInterface: RoomerRepositoryInterface,
    application: Application,
    private val followManipulate: FollowManipulate
) : AndroidViewModel(application) {

    private val _follows: MutableState<List<Follow>> = mutableStateOf(emptyList())
    private val _state: MutableStateFlow<FollowsScreenState> =
        MutableStateFlow(FollowsScreenState())
    private val followScreenUseCase = FollowScreenUseCase(roomerRepositoryInterface)
    private val userToken = SpManager().getSharedPreference(
        getApplication<Application>().applicationContext,
        SpManager.Sp.TOKEN,
        null
    ) ?: ""

    private val currentUser: MutableState<User> = mutableStateOf(User())
    val follows: State<List<Follow>> = _follows
    val state: StateFlow<FollowsScreenState> = _state

    init {
        viewModelScope.launch {
            getCurrentUser()
            getFollows()
        }
    }

    fun deleteFollow(follow: Follow) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { current ->
                current.copy(isLoading = true)
            }
            followManipulate.deleteFollow(currentUser.value.userId, follow.user.userId, userToken)
            _follows.value = _follows.value.filter {
                it.id != follow.id
            }
            _state.update { current ->
                current.copy(isLoading = false)
            }
        }
    }

    private suspend fun getCurrentUser() {
        followScreenUseCase.getCurrentUser(currentUser).collect { result ->
            when (result) {
                is Resource.Loading -> _state.update { current ->
                    current.copy(isLoading = true)
                }

                is Resource.Success -> _state.update { current ->
                    current.copy(isLoading = false, success = true)
                }

                else -> _state.update { current ->
                    current.copy(isLoading = false, error = Constants.Follows.ERROR_UNAUTHORIZED)
                }
            }
        }
    }

    private suspend fun getFollows() {
        followScreenUseCase.getFollows(currentUser.value.userId, userToken, _follows)
            .collect { resource ->
                when (resource) {
                    is Resource.Loading -> _state.update { current ->
                        current.copy(isLoading = true)
                    }

                    is Resource.Success -> _state.update { current ->
                        current.copy(isLoading = false, success = true)
                    }

                    else -> _state.update { current ->
                        current.copy(isLoading = false, error = Constants.Follows.ERROR_EMPTY_LIST)
                    }
                }
            }
    }
}
