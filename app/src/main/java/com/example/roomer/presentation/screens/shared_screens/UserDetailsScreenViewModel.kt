package com.example.roomer.presentation.screens.shared_screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.data.shared.add_to_history.AddToHistory
import com.example.roomer.domain.model.entities.User
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class UserDetailsScreenViewModel @Inject constructor(
    private val addToHistory: AddToHistory,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val userString: String? = savedStateHandle["user"]
            val user = Gson().fromJson(userString, User::class.java)
            user?.let {
                addToHistory.roomerRepositoryInterface.addRoommateToLocalHistory(user)
            }
        }
    }
}
