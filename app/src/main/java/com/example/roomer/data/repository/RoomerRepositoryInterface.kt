package com.example.roomer.data.repository

import com.example.roomer.domain.model.entities.Message
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

    fun getLocalCurrentUser(): User

    suspend fun deleteLocalCurrentUser()

    suspend fun updateLocalUser(user: User)

    fun getAllLocalUsers(): List<User>

    suspend fun deleteLocalUser(user: User)

    suspend fun addLocalUser(user: User)

    suspend fun addManyLocalUsers(users: List<User>)

    fun getUserById(userId: Int): User
}
