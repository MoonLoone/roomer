package com.example.roomer.presentation.screens.shared_screens.user_details_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.data.shared.add_to_history.AddToHistory
import com.example.roomer.data.shared.follow.FollowManipulate
import com.example.roomer.domain.model.entities.User
import com.example.roomer.domain.usecase.shared_screens.UserDetailsUseCase
import com.example.roomer.utils.Resource
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsScreenViewModel @Inject constructor(
    private val roomerRepositoryInterface: RoomerRepositoryInterface,
    private val addToHistory: AddToHistory,
    private val savedStateHandle: SavedStateHandle,
    val followManipulate: FollowManipulate
) : ViewModel() {

    private val userDetailsUseCase = UserDetailsUseCase(roomerRepositoryInterface)
    private val _state: MutableStateFlow<UserDetailsScreenState> = MutableStateFlow(
        UserDetailsScreenState()
    )

    val currentUser: MutableState<User> = mutableStateOf(User())
    val state: StateFlow<UserDetailsScreenState> = _state

    init {
        viewModelScope.launch {
            val userString: String? = savedStateHandle["user"]
            val user = Gson().fromJson(userString, User::class.java)
            user?.let {
                addToHistory.roomerRepositoryInterface.addRoommateToLocalHistory(user)
            }
            currentUser.value = roomerRepositoryInterface.getLocalCurrentUser()
            checkIsFollow(user)
        }
    }

    private suspend fun checkIsFollow(user: User) {
        userDetailsUseCase.checkIsFollowed(currentUser.value.userId, user.userId)
            .collect { result ->
                when (result) {
                    is Resource.Success -> _state.update { current ->
                        current.copy(isFollow = true, success = true, isLoading = false)
                    }

                    is Resource.Loading -> _state.update { current ->
                        current.copy(isLoading = true)
                    }

                    else -> _state.update { current ->
                        current.copy(success = true, isLoading = false, error = "General error")
                    }
                }
            }
    }

}
