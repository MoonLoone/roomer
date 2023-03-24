package com.example.roomer.presentation.screens.shared_screens

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.data.remote.ChatClientWebSocket
import com.example.roomer.data.repository.AuthRepositoryInterface
import com.example.roomer.data.repository.RoomerRepository
import com.example.roomer.domain.model.entities.User
import com.example.roomer.presentation.screens.entrance.login.LoginScreenViewModel
import com.example.roomer.utils.SpManager
import com.example.roomer.utils.createJson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatScreenViewModel @Inject constructor(
    application: Application,
    private val roomerRepository: RoomerRepository,
) : AndroidViewModel(application) {

    private val chatClientWebSocket: ChatClientWebSocket = ChatClientWebSocket()
    private val _socketConnectionState: MutableState<Boolean> = mutableStateOf(false)
    private val socketConnectionState: State<Boolean> = _socketConnectionState
    private var currentUser: User? = null
    private var recipientUser: User? = null

    fun startChat() {
        viewModelScope.launch {
            _socketConnectionState.value = false
            val token = SpManager().getSharedPreference(
                getApplication<Application>().applicationContext,
                SpManager.Sp.TOKEN,
                LoginScreenViewModel.FIELD_DEFAULT_VALUE
            )
            currentUser = roomerRepository.getCurrentUserInfo("Token $token").body()
            recipientUser = User(id = 25)
            if (currentUser == null || recipientUser == null) return@launch
            chatClientWebSocket.open(currentUser!!.id, recipientUser!!.id)
            _socketConnectionState.value = true
        }
    }

    fun sendMessage(message: String) {
        if (socketConnectionState.value) {
            val messageJson = createJson(
                Pair("message", message),
                Pair("donor_id", currentUser?.id ?: -1),
                Pair("recipient_id", recipientUser?.id ?: -1)
            )
            chatClientWebSocket.sendMessage(messageJson)
        }
    }
}

