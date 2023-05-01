package com.example.roomer.presentation.screens.shared_screens

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
import com.example.roomer.data.room.entities.LocalMessage
import com.example.roomer.data.room.entities.toMessage
import com.example.roomer.domain.model.entities.Message
import com.example.roomer.domain.model.entities.User
import com.example.roomer.utils.SpManager
import com.example.roomer.utils.converters.createJson
import com.example.roomer.utils.converters.getFromJson
import com.google.gson.Gson
import com.google.gson.JsonParser
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ChatScreenViewModel @AssistedInject constructor(
    @Assisted private val recipientUser: User,
    application: Application,
    private val roomerRepository: RoomerRepository,
) : AndroidViewModel(application) {

    private val chatClientWebSocket: ChatClientWebSocket = ChatClientWebSocket {text -> messageReceived(text) }
    private val _socketConnectionState: MutableState<Boolean> = mutableStateOf(true)
    val socketConnectionState: State<Boolean> = _socketConnectionState
    val pagingData: MutableStateFlow<Flow<PagingData<Message>>> = MutableStateFlow(emptyFlow())
    private var currentUser = User()

    @AssistedFactory
    interface Factory {
        fun create(recipientUser: User): ChatScreenViewModel
    }

    init {
        viewModelScope.launch {
            currentUser = roomerRepository.getLocalCurrentUser()
            val chatId = (currentUser.userId + recipientUser.userId).toString()
            chatClientWebSocket.open(currentUser.userId, recipientUser.userId)
            val response = roomerRepository.getMessages(chatId = chatId)
            pagingData.value = response
                .map {
                    it.map {
                        it.toMessage()
                    }
                }.cachedIn(viewModelScope)
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                if (socketConnectionState.value) {
                    val messageJson = createJson(
                        Pair("message", message),
                        Pair("donor_id", currentUser.userId),
                        Pair("recipient_id", recipientUser.userId)
                    )
                    chatClientWebSocket.sendMessage(messageJson)
                }
            }
        }
    }

    private fun messageReceived(text: String) {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                val token = SpManager().getSharedPreference(
                    getApplication<Application>().applicationContext,
                    key = SpManager.Sp.TOKEN,
                    null
                ) ?: ""
                val jsonObject = JsonParser.parseString(text).asJsonObject
                val donor = Gson().fromJson(jsonObject.getAsJsonObject("donor"), User::class.java)
                val recipient = Gson().fromJson(jsonObject.getAsJsonObject("recipient"), User::class.java)
                val message = LocalMessage(
                    getFromJson(jsonObject, "id").toInt(),
                    getFromJson(jsonObject, "chat_id").toInt(),
                    getFromJson(jsonObject, "date_time"),
                    getFromJson(jsonObject, "text"),
                    donor.userId,
                    recipient.userId,
                    true
                )

                roomerRepository.messageChecked(message.messageId,token)
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
