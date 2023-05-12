package com.example.roomer.domain.usecase.shared_screens

import androidx.compose.runtime.MutableState
import androidx.paging.PagingData
import androidx.paging.map
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.data.room.entities.toMessage
import com.example.roomer.domain.model.entities.Message
import com.example.roomer.domain.model.entities.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

class ChatUseCase(
    private val roomerRepository: RoomerRepositoryInterface
) {

    suspend fun getCurrentUserInfo(user: MutableState<User>): Boolean {
        user.value = roomerRepository.getLocalCurrentUser()
        return user.value != User()
    }

    suspend fun getMessages(
        userId: Int,
        recipientId: Int,
        data: MutableState<Flow<PagingData<Message>>>
    ): Boolean {
        val chatId = (userId + recipientId).toString()
        data.value = roomerRepository.getMessages(chatId = chatId).map { pagedMessages ->
            pagedMessages.map { localMessage ->
                localMessage.toMessage()
            }
        }
        return data.value == emptyFlow<Message>()
    }
}
