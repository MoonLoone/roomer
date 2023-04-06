package com.example.roomer.presentation.screens.shared_screens

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomer.local.converters.createJson
import com.example.roomer.local.converters.getFromJson
import com.example.roomer.data.remote.ChatClientWebSocket
import com.example.roomer.data.repository.RoomerRepository
import com.example.roomer.domain.model.entities.Message
import com.example.roomer.domain.model.entities.User
import com.example.roomer.presentation.screens.entrance.login.LoginScreenViewModel
import com.example.roomer.utils.SpManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

@HiltViewModel
class ChatScreenViewModel @Inject constructor(
    application: Application,
    private val roomerRepository: RoomerRepository,
) : AndroidViewModel(application) {

    private val _messages = MutableStateFlow(listOf<Message>())
    val messages: StateFlow<List<Message>> = _messages
    private val chatClientWebSocket: ChatClientWebSocket = ChatClientWebSocket { decodeJSON(it) }
    private val _socketConnectionState: MutableState<Boolean> = mutableStateOf(false)
    private val socketConnectionState: State<Boolean> = _socketConnectionState
    private var currentUserId: Int = 0
    private var recipientUserId: Int = 0

    fun startChat(recipientId: Int, chatId: Int) {
        viewModelScope.launch {
            _socketConnectionState.value = false
            chatClientWebSocket.socket?.let {
                _socketConnectionState.value = true
                return@launch
            }
            val token = SpManager().getSharedPreference(
                getApplication<Application>().applicationContext,
                SpManager.Sp.TOKEN,
                LoginScreenViewModel.FIELD_DEFAULT_VALUE
            )
            currentUserId = roomerRepository.getCurrentUserInfo(token.toString()).body()?.id ?: 0
            recipientUserId = recipientId
            chatClientWebSocket.open(currentUserId, recipientUserId)
            _messages.value =
                roomerRepository.getMessagesForChat(userId = currentUserId, chatId = chatId).body()
                ?.toMutableList() ?: mutableListOf()
            _socketConnectionState.value = true
        }
    }

    fun sendMessage(message: String) {
        if (socketConnectionState.value) {
            val messageJson = createJson(
                Pair("message", message),
                Pair("donor_id", currentUserId),
                Pair("recipient_id", recipientUserId)
            )
            chatClientWebSocket.sendMessage(messageJson)
        }
    }

    private fun decodeJSON(jsonString: String) {
        val json = JSONObject(jsonString)
        val message = Message(
            id = getFromJson(json, "id").toInt(),
            chatId = getFromJson(json, "chat_id").toInt(),
            dateTime = "",
            text = getFromJson(json, "text"),
            donor = User(id = getFromJson(json, "donor").toInt()),
            recipient = User(id = getFromJson(json, "recipient").toInt()),
            isChecked = getFromJson(json, "isChecked").toBoolean(),
        )
        _messages.value = messages.value + message
    }
}
