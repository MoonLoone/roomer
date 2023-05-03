package com.example.roomer.data.repository.roomer_repository

import android.graphics.Bitmap
import com.example.roomer.data.local.RoomerStoreInterface
import com.example.roomer.data.remote.RoomerApi
import com.example.roomer.domain.model.entities.Message
import com.example.roomer.domain.model.entities.MessageNotification
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.User
import com.example.roomer.domain.model.room_post.RoomPost
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.ByteArrayOutputStream
import kotlin.random.Random
import kotlin.random.nextUInt

class RoomerRepository @Inject constructor(
    private val roomerApi: RoomerApi,
    private val roomerStore: RoomerStoreInterface
) : RoomerRepositoryInterface {
    override suspend fun getChats(userId: Int): Response<List<Message>> {
        return roomerApi.getChatsForUser(userId, "")
    }

    override suspend fun getCurrentUserInfo(token: String): Response<User> {
        val refToken = "Token ".plus(token)
        return roomerApi.getCurrentUserInfo(refToken)
    }

    override suspend fun getMessagesForChat(
        userId: Int,
        chatId: Int,
        offset: Int,
        limit: Int
    ): Response<List<Message>> {
        return roomerApi.getChatsForUser(userId, chatId.toString(), offset, limit)
    }

    override suspend fun getFilterRooms(
        monthPriceFrom: String,
        monthPriceTo: String,
        bedroomsCount: String,
        bathroomsCount: String,
        housingType: String
    ): Response<List<Room>> {
        return roomerApi.filterRooms(
            monthPriceFrom,
            monthPriceTo,
            bedroomsCount,
            bathroomsCount,
            housingType
        )
    }

    override suspend fun getFilterRoommates(
        sex: String,
        employment: String,
        alcoholAttitude: String,
        smokingAttitude: String,
        sleepTime: String,
        personalityType: String,
        cleanHabits: String
    ): Response<List<User>> {
        return roomerApi.filterRoommates(
            sex,
            employment,
            alcoholAttitude,
            smokingAttitude,
            sleepTime,
            personalityType,
            cleanHabits
        )
    }

    override suspend fun getLocalFavourites(): Flow<List<Room>> = roomerStore.getFavourites()

    override suspend fun addLocalFavourite(room: Room) = roomerStore.addFavourite(room)

    override suspend fun deleteLocalFavourite(room: Room) = roomerStore.deleteFavourite(room)

    override suspend fun isLocalFavouritesEmpty(): Boolean = roomerStore.isFavouritesEmpty()

    override suspend fun getLocalCurrentUser() = roomerStore.getCurrentUser()

    override suspend fun addLocalCurrentUser(user: User) = roomerStore.addCurrentUser(user)

    override suspend fun updateLocalCurrentUser(user: User) = roomerStore.updateCurrentUser(user)

    override suspend fun deleteLocalCurrentUser() = roomerStore.deleteCurrentUser()

    override suspend fun updateLocalUser(user: User) = roomerStore.updateUser(user)

    override suspend fun getAllLocalUsers(): Flow<List<User>> = roomerStore.getAllUsers()

    override suspend fun deleteLocalUser(user: User) = roomerStore.deleteUser(user)

    override suspend fun addLocalUser(user: User) = roomerStore.addUser(user)

    override suspend fun addManyLocalUsers(users: List<User>) = roomerStore.addManyUsers(users)

    override suspend fun getLocalUserById(userId: Int): User = roomerStore.getUserById(userId)

    override suspend fun messageChecked(messageId: Int, token: String): Response<Message> {
        val refToken = "Token ".plus(token)
        return roomerApi.messageChecked(messageId, refToken)
    }

    override suspend fun getMessageNotifications(userId: Int): Response<List<MessageNotification>> {
        return roomerApi.getNotifications(userId)
    }

    override suspend fun putRoom(token: String, room: RoomPost) = roomerApi.postAdvertisement(token, room)
    override suspend fun putRoomPhotos(
        token: String,
        filesContent: List<Bitmap>
    ): Response<Room> {
        val refToken = "Token ".plus(token)
        val list = mutableListOf<MultipartBody.Part>()
        filesContent.forEach {
            val stream = ByteArrayOutputStream()
            it.compress(Bitmap.CompressFormat.JPEG, 80, stream)
            val byteArray = stream.toByteArray()
            list.add(MultipartBody.Part.createFormData(
                 "file_content",
                Random.nextUInt(8000000u).toString().plus(".jpeg"),
                byteArray.toRequestBody("image/*".toMediaTypeOrNull(), 0, byteArray.size)
            ))
        }

       return roomerApi.postAdvertisement(refToken, list)
    }

    override suspend fun getCurrentUserRooms(token: String, hostId: Int): Response<List<Room>> = roomerApi.getCurrentUserAdvertisements(token, hostId)
}
