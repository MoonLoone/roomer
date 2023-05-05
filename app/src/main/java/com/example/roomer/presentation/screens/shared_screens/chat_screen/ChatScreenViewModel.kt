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
import com.example.roomer.data.remote.ChatClientWebSocket
import com.example.roomer.data.repository.roomer_repository.RoomerRepository
import com.example.roomer.domain.model.entities.Message
import com.example.roomer.domain.model.entities.User
import com.example.roomer.domain.model.entities.toLocalMessage
import com.example.roomer.domain.usecase.shared_screens.ChatConnectionUseCase
import com.example.roomer.domain.usecase.shared_screens.ChatUseCase
import com.example.roomer.utils.SpManager
import com.example.roomer.utils.converters.createJson
import com.google.gson.Gson
import com.google.gson.JsonParser
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatScreenViewModel @AssistedInject constructor(
    @Assisted private val recipientUser: User,
    application: Application,
    private val roomerRepository: RoomerRepository
) : AndroidViewModel(application) {

    private val chatUseCase: ChatUseCase = ChatUseCase(roomerRepository)
    private val chatConnectionUseCase: ChatConnectionUseCase =
        ChatConnectionUseCase(roomerRepository)
    private val chatClientWebSocket: ChatClientWebSocket =
        ChatClientWebSocket { text -> messageReceived(text) }
    private val _state: MutableStateFlow<ChatScreenState> = MutableStateFlow(
        ChatScreenState(isLoading = true)
    )
    private val _currentUser = mutableStateOf(User())
    private val _pagingData: MutableState<Flow<PagingData<Message>>> = mutableStateOf(emptyFlow())
    private val token = SpManager().getSharedPreference(
        getApplication<Application>().applicationContext,
        key = SpManager.Sp.TOKEN,
        null
    ) ?: ""

    val state: StateFlow<ChatScreenState> = _state
    val pagingData: State<Flow<PagingData<Message>>> = _pagingData
    val currentUser: State<User> = _currentUser

    init {
        viewModelScope.launch {
            if (!chatUseCase.getCurrentUserInfo(_currentUser)) {
                _state.update { current ->
                    current.copy(unauthorized = true)
                }
                return@launch
            }
            if (!chatConnectionUseCase.openConnection(
                    chatClientWebSocket,
                    currentUser.value.userId,
                    recipientUser.userId
                )
            ) {
                _state.update { current ->
                    current.copy(connectionRefused = true)
                }
                return@launch
            }
            if (!chatUseCase.getMessages(
                    currentUser.value.userId,
                    recipientUser.userId,
                    _pagingData
                )
            ) {
                _state.update { current ->
                    current.copy(emptyMessagesList = true, success = true)
                }
                return@launch
            }
            _state.value = ChatScreenState(success = true)
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!state.value.connectionRefused) {
                val messageJson = createJson(
                    Pair("message", message),
                    Pair("donor_id", _currentUser.value.userId),
                    Pair("recipient_id", recipientUser.userId)
                )
                chatClientWebSocket.sendMessage(messageJson)
            }
        }
    }

    fun closeChat() = chatConnectionUseCase.closeConnection(chatClientWebSocket)

    private fun messageReceived(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
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

    override fun onCleared() {
        chatClientWebSocket.close()
        super.onCleared()
    }

    @AssistedFactory
    interface Factory {
        fun create(recipientUser: User): ChatScreenViewModel
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
