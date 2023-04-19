package com.example.roomer.presentation.screens.navbar_screens.messenger_screen

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.data.repository.roomer_repository.RoomerRepository
import com.example.roomer.domain.model.entities.Message
import com.example.roomer.domain.usecase.navbar_screens.MessengerUseCase
import com.example.roomer.presentation.screens.entrance.login.LoginScreenViewModel
import com.example.roomer.utils.Resource
import com.example.roomer.utils.SpManager
import com.example.roomer.utils.converters.convertTimeDateFromBackend
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MessengerViewModel @Inject constructor(
    application: Application,
    private val roomerRepository: RoomerRepository
) : AndroidViewModel(application) {

    private val _state = mutableStateOf(MessengerScreenState(false))
    val state: State<MessengerScreenState> = _state
    private val messengerUseCase = MessengerUseCase(roomerRepository)
    private val _chats = MutableStateFlow(emptyList<Message>())
    val chats: StateFlow<List<Message>> = _chats

    fun getChats() {
        viewModelScope.launch {
            val token = SpManager().getSharedPreference(
                getApplication<Application>().applicationContext,
                SpManager.Sp.TOKEN,
                LoginScreenViewModel.FIELD_DEFAULT_VALUE
            )
            val currentUser = roomerRepository.getCurrentUserInfo(token.toString()).body()
            messengerUseCase.loadChats(currentUser?.userId ?: 0).collect {
                when (it) {
                    is Resource.Internet -> {
                        _state.value = MessengerScreenState()
                    }
                    is Resource.Loading -> {
                        _state.value = MessengerScreenState(
                            isLoading = true,
                            internetProblem = false
                        )
                    }
                    is Resource.Success -> {
                        _state.value = MessengerScreenState(
                            isLoading = true
                        )
                        _chats.value = it.data?.map { message ->
                            message.dateTime = convertTimeDateFromBackend(message.dateTime)
                            message
                        } ?: emptyList()
                        _state.value = MessengerScreenState(
                            success = true,
                            isLoading = false,
                            error = "Unrecognized error"
                        )
                    }
                    else -> {
                        _state.value = MessengerScreenState(
                            isLoading = false,
                            error = "Unrecognized error"
                        )
                    }
                }
            }
        }
    }
}
