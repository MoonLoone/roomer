package com.example.roomer.data.repository

import com.example.roomer.domain.model.entities.Message
import com.example.roomer.domain.model.entities.MessageNotification
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.User
import retrofit2.Response

interface RoomerRepositoryInterface {

    suspend fun getChats(userId: Int): Response<List<Message>>

    suspend fun getCurrentUserInfo(
        token: String,
    ): Response<User>

    suspend fun getMessagesForChat(userId: Int, chatId: Int): Response<List<Message>>

    suspend fun getFilterRoommates(
        sex: String,
        employment: String,
        alcoholAttitude: String,
        smokingAttitude: String,
        sleepTime: String,
        personalityType: String,
        cleanHabits: String
    ): Response<List<User>>

    suspend fun getFilterRooms(
        monthPriceFrom: String,
        monthPriceTo: String,
        bedroomsCount: String,
        bathroomsCount: String,
        housingType: String
    ): Response<List<Room>>

    suspend fun messageChecked(messageId:Int, token: String): Response<Message>

    suspend fun getMessageNotifications(userId: Int): Response<List<MessageNotification>>
}
