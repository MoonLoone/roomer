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
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class UserDetailsScreenViewModel @Inject constructor(
    private val roomerRepositoryInterface: RoomerRepositoryInterface,
    private val addToHistory: AddToHistory,
    private val savedStateHandle: SavedStateHandle,
    val followManipulate: FollowManipulate
) : ViewModel() {

    val currentUser: MutableState<User> = mutableStateOf(User())

    init {
        viewModelScope.launch {
            val userString: String? = savedStateHandle["user"]
            val user = Gson().fromJson(userString, User::class.java)
            user?.let {
                addToHistory.roomerRepositoryInterface.addRoommateToLocalHistory(user)
            }
            currentUser.value = roomerRepositoryInterface.getLocalCurrentUser()
        }
    }
}
