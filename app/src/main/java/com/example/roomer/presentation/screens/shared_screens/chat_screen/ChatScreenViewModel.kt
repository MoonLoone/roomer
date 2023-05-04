package com.example.roomer.presentation.screens.shared_screens.chat_screen

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.roomer.data.remote.ChatClientWebSocket
import com.example.roomer.data.repository.roomer_repository.RoomerRepository
import com.example.roomer.data.room.entities.toMessage
import com.example.roomer.domain.model.entities.Message
import com.example.roomer.domain.model.entities.User
import com.example.roomer.domain.model.entities.toLocalMessage
import com.example.roomer.utils.SpManager
import com.example.roomer.utils.converters.createJson
import com.google.gson.Gson
import com.google.gson.JsonParser
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatScreenViewModel @AssistedInject constructor(
    @Assisted private val recipientUser: User,
    application: Application,
    private val roomerRepository: RoomerRepository
) : AndroidViewModel(application) {

    private val chatClientWebSocket: ChatClientWebSocket =
        ChatClientWebSocket { text -> messageReceived(text) }
    private val _state: MutableStateFlow<ChatScreenState> = MutableStateFlow(
        ChatScreenState()
    )
    private val _currentUser = mutableStateOf(User())
    private val _pagingData: MutableState<Flow<PagingData<Message>>> = mutableStateOf(emptyFlow())

    val state: StateFlow<ChatScreenState> = _state
    val pagingData: State<Flow<PagingData<Message>>> = _pagingData
    val currentUser: State<User> = _currentUser

    @AssistedFactory
    interface Factory {
        fun create(recipientUser: User): ChatScreenViewModel
    }

    init {
        viewModelScope.launch {
            _state.update { current ->
                current.copy(socketConnected = false, isLoading = true)
            }
            _currentUser.value = roomerRepository.getLocalCurrentUser()
            val chatId = (_currentUser.value.userId + recipientUser.userId).toString()
            chatClientWebSocket.open(_currentUser.value.userId, recipientUser.userId)
            val response = roomerRepository.getMessages(chatId = chatId)
            _pagingData.value = response
                .map { pagedMessages ->
                    pagedMessages.map { localMessage ->
                        localMessage.toMessage()
                    }
                }.cachedIn(viewModelScope)
            _state.update { current ->
                current.copy(socketConnected = true, isLoading = false)
            }
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                if (state.value.socketConnected) {
                    val messageJson = createJson(
                        Pair("message", message),
                        Pair("donor_id", _currentUser.value.userId),
                        Pair("recipient_id", recipientUser.userId)
                    )
                    chatClientWebSocket.sendMessage(messageJson)
                }
            }
        }
    }

    fun closeChat() = chatClientWebSocket.close()

    private fun messageReceived(text: String) {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                val token = SpManager().getSharedPreference(
                    getApplication<Application>().applicationContext,
                    key = SpManager.Sp.TOKEN,
                    null
                ) ?: ""
                val jsonObject = JsonParser.parseString(text).asJsonObject
                val message = Gson().fromJson(jsonObject, Message::class.java).toLocalMessage()
                if (message.recipientId == currentUser.value.userId) {
                    roomerRepository.messageChecked(
                        message.messageId,
                        token
                    )
                }
                roomerRepository.addLocalMessage(message)
            }
        }
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            recipientUser: User
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(recipientUser) as T
            }
        }
    }
}
