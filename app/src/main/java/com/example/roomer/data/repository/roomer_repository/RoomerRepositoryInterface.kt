package com.example.roomer.data.repository.roomer_repository

import com.example.roomer.domain.model.entities.Message
import com.example.roomer.domain.model.entities.MessageNotification
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.User
import kotlinx.coroutines.flow.Flow
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

    suspend fun getLocalFavourites(): Flow<List<Room>>

    suspend fun addLocalFavourite(room: Room)

    suspend fun deleteLocalFavourite(room: Room)

    suspend fun isLocalFavouritesEmpty(): Boolean

    suspend fun getLocalCurrentUser(): User

    suspend fun addLocalCurrentUser(user: User)

    suspend fun updateLocalCurrentUser(user: User)

    suspend fun deleteLocalCurrentUser()

    suspend fun updateLocalUser(user: User)

    suspend fun getAllLocalUsers(): Flow<List<User>>

    suspend fun deleteLocalUser(user: User)

    suspend fun addLocalUser(user: User)

    suspend fun addManyLocalUsers(users: List<User>)

    suspend fun getLocalUserById(userId: Int): User

    suspend fun messageChecked(messageId: Int, token: String): Response<Message>

    suspend fun getMessageNotifications(userId: Int): Response<List<MessageNotification>>
}
