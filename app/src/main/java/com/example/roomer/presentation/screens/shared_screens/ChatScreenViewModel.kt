package com.example.roomer.presentation.screens.shared_screens

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.roomer.data.remote.ChatClientWebSocket
import com.example.roomer.data.repository.roomer_repository.RoomerRepository
import com.example.roomer.domain.model.entities.Message
import com.example.roomer.domain.model.entities.User
import com.example.roomer.presentation.screens.entrance.login.LoginScreenViewModel
import com.example.roomer.utils.RoomerPagingSource
import com.example.roomer.utils.SpManager
import com.example.roomer.utils.converters.createJson
import com.example.roomer.utils.converters.getFromJson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import org.json.JSONObject

@HiltViewModel
class ChatScreenViewModel @Inject constructor(
    application: Application,
    private val roomerRepository: RoomerRepository,
) : AndroidViewModel(application) {

    private val chatClientWebSocket: ChatClientWebSocket = ChatClientWebSocket { decodeJSON(it) }
    private val _socketConnectionState: MutableState<Boolean> = mutableStateOf(false)
    private val socketConnectionState: State<Boolean> = _socketConnectionState
    private var currentUserId: Int = 302
    private var recipientUserId: Int = 0
    var messagesPager: Flow<PagingData<Message>> = Pager(PagingConfig(pageSize = 3, prefetchDistance = 10)) {
            RoomerPagingSource { offset: Int, limit: Int ->
                roomerRepository.getMessagesForChat(currentUserId, 603, offset, limit)
            }
    }.flow.cachedIn(viewModelScope)
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
            currentUserId =
                roomerRepository.getCurrentUserInfo(token.toString()).body()?.userId ?: 0
            recipientUserId = recipientId
            chatClientWebSocket.open(currentUserId, recipientUserId)
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

    fun messageRead(messageId: Int) {
        viewModelScope.launch {
            val token = SpManager().getSharedPreference(
                getApplication<Application>().applicationContext,
                SpManager.Sp.TOKEN,
                LoginScreenViewModel.FIELD_DEFAULT_VALUE
            ) ?: ""
            roomerRepository.messageChecked(messageId, token)
        }
    }

    private fun decodeJSON(jsonString: String) {
        val json = JSONObject(jsonString)
        val message = Message(
            id = getFromJson(json, "id").toInt(),
            chatId = getFromJson(json, "chat_id").toInt(),
            dateTime = "",
            text = getFromJson(json, "text"),
            donor = User(userId = getFromJson(json, "donor").toInt()),
            recipient = User(userId = getFromJson(json, "recipient").toInt()),
            isChecked = getFromJson(json, "is_checked").toBoolean()
        )
    }
}
