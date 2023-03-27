package com.example.roomer.data.repository

import com.example.roomer.data.remote.RoomerApi
import com.example.roomer.domain.model.entities.Message
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.User
import javax.inject.Inject
import retrofit2.Response

class RoomerRepository @Inject constructor(private val roomerApi: RoomerApi) :
    RoomerRepositoryInterface {
    override suspend fun getChats(userId: Int): Response<List<Message>> {
        return roomerApi.getChatsForUser(userId)
    }

    override suspend fun getCurrentUserInfo(token: String): Response<User> {
        val refToken = "Token ".plus(token)
        return roomerApi.getCurrentUserInfo(refToken)
    }

    override suspend fun getMessagesForChat(userId: Int, chatId: Int): Response<List<Message>> {
        return roomerApi.getChatsForUser(userId, chatId.toString())
    }
}
