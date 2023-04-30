package com.example.roomer.presentation.screens.shared_screens.chat_screen

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.roomer.data.remote.ChatClientWebSocket
import com.example.roomer.data.repository.roomer_repository.RoomerRepository
import com.example.roomer.domain.model.entities.Message
import com.example.roomer.presentation.screens.entrance.login.LoginScreenViewModel
import com.example.roomer.utils.SpManager
import com.example.roomer.utils.converters.createJson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@HiltViewModel
class ChatScreenViewModel @Inject constructor(
    application: Application,
    private val roomerRepository: RoomerRepository
) : AndroidViewModel(application) {

    private val chatClientWebSocket: ChatClientWebSocket = ChatClientWebSocket { decodeJSON(it) }
    private val _socketConnectionState: MutableState<Boolean> = mutableStateOf(false)
    val socketConnectionState: State<Boolean> = _socketConnectionState
    private var recipientUserId: Int = 0
    private var currentUserId: Int = 0
    var messagesPager: Flow<PagingData<Message>> = flowOf(PagingData.empty())

    fun startChat(recipientId: Int, chatId: Int) {
        viewModelScope.launch {
            _socketConnectionState.value = false
            chatClientWebSocket.socket?.let {
                _socketConnectionState.value = true
                return@launch
            }
            currentUserId = roomerRepository.getLocalCurrentUser().userId
            recipientUserId = recipientId
            /*messagesPager = Pager(
                PagingConfig(
                    pageSize = Constants.Chat.PAGE_SIZE,
                    maxSize = Constants.Chat.CASH_SIZE,
                    initialLoadSize = Constants.Chat.INITIAL_SIZE
                )
            ) {
                RoomerPagingSource { offset: Int, limit: Int ->
                    roomerRepository.getMessagesForChat(currentUserId, chatId, offset, limit)
                }
            }.flow.cachedIn(viewModelScope)*/
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
            )
            if (token != null) {
                roomerRepository.messageChecked(messageId, token)
            }
        }
    }

    private fun decodeJSON(jsonString: String) {
    }
}
