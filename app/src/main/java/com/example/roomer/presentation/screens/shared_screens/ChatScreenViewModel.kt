package com.example.roomer.presentation.screens.shared_screens

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.data.remote.ChatClientWebSocket
import com.example.roomer.data.repository.AuthRepositoryInterface
import com.example.roomer.domain.model.entities.User
import com.example.roomer.presentation.screens.entrance.login.LoginScreenViewModel
import com.example.roomer.utils.SpManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatScreenViewModel @Inject constructor(
    application: Application,
    private val authRepository: AuthRepositoryInterface,
) : AndroidViewModel(application){

    private val chatClientWebSocket: ChatClientWebSocket = ChatClientWebSocket()
    private val _socketConnectionState: MutableState<Boolean> = mutableStateOf(false)
    private val socketConnectionState: State<Boolean> = _socketConnectionState
    fun startChat(){
        viewModelScope.launch {
            val token = SpManager().getSharedPreference(
                getApplication<Application>().applicationContext,
                SpManager.Sp.TOKEN,
                LoginScreenViewModel.FIELD_DEFAULT_VALUE
            )
            token?.let {
                val currentUser = authRepository.getCurrentUserInfo("Token $it").body()?: User()
                chatClientWebSocket.open(currentUser.id,1)
                chatClientWebSocket.sendMessage("Hello")
            }
        }
    }
}

