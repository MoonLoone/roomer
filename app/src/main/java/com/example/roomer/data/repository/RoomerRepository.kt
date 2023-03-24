package com.example.roomer.data.repository

import com.example.roomer.data.remote.RoomerApi
import com.example.roomer.domain.model.entities.Message
import com.example.roomer.domain.model.entities.User
import retrofit2.Response
import javax.inject.Inject

class RoomerRepository @Inject constructor(private val roomerApi: RoomerApi) : RoomerRepositoryInterface{
    override suspend fun getChats(userId: Int): Response<List<Message>> {
        return roomerApi.getChatsForUser(userId)
    }

    override suspend fun getCurrentUserInfo(token: String): Response<User> {
        return roomerApi.getCurrentUserInfo(token)
    }


}
