package com.example.roomer.data.repository

import com.example.roomer.domain.model.entities.Message
import com.example.roomer.domain.model.entities.User
import retrofit2.Response

interface RoomerRepositoryInterface {

    suspend fun getChats(userId: Int): Response<List<Message>>

    suspend fun getCurrentUserInfo(
        token: String,
    ): Response<User>

    suspend fun getMessagesForChat(userId: Int, chatId: Int): Response<List<Message>>
}
