package com.example.roomer.data.repository.roomer_repository

import android.graphics.Bitmap
import androidx.paging.PagingData
import com.example.roomer.data.room.entities.LocalCurrentUser
import com.example.roomer.data.room.entities.LocalMessage
import com.example.roomer.data.room.entities.LocalRoom
import com.example.roomer.domain.model.entities.Message
import com.example.roomer.domain.model.entities.MessageNotification
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.User
import com.example.roomer.domain.model.pojo.ChatRawData
import com.example.roomer.domain.model.room_post.RoomPost
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RoomerRepositoryInterface {

    suspend fun addRoomToLocalHistory(room: LocalRoom)

    suspend fun addMateToLocalHistory(user: LocalCurrentUser)

    suspend fun addLocalMessage(message: LocalMessage)

    suspend fun getChats(userId: Int): Response<ChatRawData>

    suspend fun getFavouritesForUser(): Flow<PagingData<LocalRoom>>

    suspend fun likeHousing(housingId: Int): Response<String>

    suspend fun dislikeHousing(housingId: Int): Response<String>

    suspend fun getCurrentUserInfo(
        token: String
    ): Response<User>

    suspend fun getMessagesForChat(
        userId: Int,
        chatId: Int,
        offset: Int = 0,
        limit: Int = 10
    ): Response<ChatRawData>

    suspend fun getMessages(
        limit: Int = 10,
        chatId: String
    ): Flow<PagingData<LocalMessage>>

    suspend fun getFilterRooms(
        monthPriceFrom: String?,
        monthPriceTo: String?,
        location: String?,
        bedroomsCount: String?,
        bathroomsCount: String?,
        housingType: String?
    ): Response<List<Room>>

    suspend fun getFilterRoommates(
        sex: String?,
        location: String?,
        ageFrom: String?,
        ageTo: String?,
        employment: String?,
        alcoholAttitude: String?,
        smokingAttitude: String?,
        sleepTime: String?,
        personalityType: String?,
        cleanHabits: String?,
        interests: Map<String, String>
    ): Response<List<User>>

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

    suspend fun postRoom(token: String, room: RoomPost): Response<Room>

    suspend fun putRoomPhotos(
        token: String,
        roomId: Int,
        filesContent: List<Bitmap>
    ): Response<Room>

    suspend fun getCurrentUserRooms(token: String, hostId: Int): Response<List<Room>>
}
