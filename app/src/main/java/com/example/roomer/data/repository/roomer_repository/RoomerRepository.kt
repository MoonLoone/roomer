package com.example.roomer.data.repository.roomer_repository

import android.graphics.Bitmap
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.roomer.data.local.RoomerStoreInterface
import com.example.roomer.data.remote.RoomerApi
import com.example.roomer.data.repository.model.RecommendedMateModel
import com.example.roomer.data.repository.model.RecommendedRoomModel
import com.example.roomer.data.room.entities.HistoryItem
import com.example.roomer.data.room.entities.LocalMessage
import com.example.roomer.data.room.entities.LocalRoom
import com.example.roomer.domain.model.city.CityModel
import com.example.roomer.domain.model.entities.Message
import com.example.roomer.domain.model.entities.MessageNotification
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.User
import com.example.roomer.domain.model.pojo.ChatRawData
import com.example.roomer.domain.model.room_post.RoomPost
import com.example.roomer.utils.Constants
import com.example.roomer.utils.PagingFactories
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import kotlin.random.Random
import kotlin.random.nextUInt
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response

class RoomerRepository @Inject constructor(
    private val roomerApi: RoomerApi,
    private val roomerStore: RoomerStoreInterface
) : RoomerRepositoryInterface {

    override suspend fun addRoomToLocalHistory(room: LocalRoom) {
        roomerStore.addRoomToHistory(room)
    }

    override suspend fun addRoommateToLocalHistory(user: User) {
        roomerStore.addUserToHistory(user)
    }

    override suspend fun getHistory(): List<HistoryItem> {
        return roomerStore.getHistory()
    }

    override suspend fun addLocalMessage(message: LocalMessage) {
        roomerStore.addLocalMessage(message)
    }

    override suspend fun getChats(userId: Int): Response<ChatRawData> {
        return roomerApi.getChatsForUser(userId, "")
    }

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getFavouritesForUser(): Flow<PagingData<LocalRoom>> {
        val user = getLocalCurrentUser()
        val pager = Pager(
            PagingConfig(
                pageSize = Constants.Chat.PAGE_SIZE,
                maxSize = Constants.Chat.CASH_SIZE,
                initialLoadSize = Constants.Chat.INITIAL_SIZE
            ),
            remoteMediator = PagingFactories.createFavouritesMediator(
                roomerApi,
                roomerStore,
                user.userId
            ),
            pagingSourceFactory = { roomerStore.getPagingFavourites() }
        )
        return pager.flow
    }

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getMessages(limit: Int, chatId: String): Flow<PagingData<LocalMessage>> {
        val user = getLocalCurrentUser()
        val pager = Pager(
            PagingConfig(
                pageSize = Constants.Chat.PAGE_SIZE,
                maxSize = Constants.Chat.CASH_SIZE,
                initialLoadSize = Constants.Chat.INITIAL_SIZE
            ),
            remoteMediator = PagingFactories.createMessagesMediator(
                roomerApi,
                roomerStore,
                user.userId,
                chatId
            ),
            pagingSourceFactory = {
                roomerStore.getPagingMessages()
            }
        )
        return pager.flow
    }

    override suspend fun likeHousing(housingId: Int): Response<String> {
        val user = getLocalCurrentUser()
        return roomerApi.addToFavourite(user.userId, housingId)
    }

    override suspend fun dislikeHousing(housingId: Int): Response<String> {
        val user = getLocalCurrentUser()
        return roomerApi.deleteFavourite(user.userId, housingId)
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
    ): Response<ChatRawData> {
        return roomerApi.getChatsForUser(userId, chatId.toString())
    }

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

    override suspend fun getFilterRooms(
        monthPriceFrom: String?,
        monthPriceTo: String?,
        location: String?,
        bedroomsCount: String?,
        bathroomsCount: String?,
        housingType: String?
    ): Response<List<Room>> {
        return roomerApi.filterRooms(
            monthPriceFrom = monthPriceFrom,
            monthPriceTo = monthPriceTo,
            location = location,
            bedroomsCount = bedroomsCount,
            bathroomsCount = bathroomsCount,
            housingType = housingType
        )
    }

    override suspend fun getFilterRoommates(
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
    ): Response<List<User>> {
        return roomerApi.filterRoommates(
            sex = sex,
            location = location,
            ageFrom = ageFrom,
            ageTo = ageTo,
            employment = employment,
            alcoholAttitude = alcoholAttitude,
            smokingAttitude = smokingAttitude,
            sleepTime = sleepTime,
            personalityType = personalityType,
            cleanHabits = cleanHabits,
            interests = interests
        )
    }

    override suspend fun getRecommendedRooms(
        recommendedRoomModel: RecommendedRoomModel
    ): Response<List<Room>> {
        with(recommendedRoomModel) {
            return roomerApi.filterRooms(
                limit = Constants.Home.RECOMMENDED_ROOMS_SIZE,
                monthPriceFrom = monthPriceFrom,
                monthPriceTo = monthPriceTo,
                location = location,
                bedroomsCount = bedroomsCount,
                bathroomsCount = bathroomsCount,
                housingType = housingType
            )
        }
    }

    override suspend fun getRecommendedMates(
        recommendedMateModel: RecommendedMateModel
    ): Response<List<User>> {
        with(recommendedMateModel) {
            return roomerApi.filterRoommates(
                limit = Constants.Home.RECOMMENDED_MATES_SIZE,
                sex = sex,
                location = location,
                ageFrom = ageFrom,
                ageTo = ageTo,
                employment = employment,
                alcoholAttitude = alcoholAttitude,
                smokingAttitude = smokingAttitude,
                sleepTime = sleepTime,
                personalityType = personalityType,
                cleanHabits = cleanHabits,
                interests = interests
            )
        }
    }

    override suspend fun postRoom(token: String, room: RoomPost): Response<Room> {
        val refToken = "Token ".plus(token)
        return roomerApi.postAdvertisement(refToken, room)
    }

    override suspend fun putRoomPhotos(
        token: String,
        roomId: Int,
        filesContent: List<Bitmap>
    ): Response<Room> {
        val refToken = "Token ".plus(token)
        val list = mutableListOf<MultipartBody.Part>()
        filesContent.forEach {
            val stream = ByteArrayOutputStream()
            it.compress(Bitmap.CompressFormat.JPEG, Constants.JPEG_QUALITY, stream)
            val byteArray = stream.toByteArray()
            list.add(
                MultipartBody.Part.createFormData(
                    "file_content",
                    Random.nextUInt(8000000u).toString().plus(".jpeg"),
                    byteArray.toRequestBody("image/*".toMediaTypeOrNull(), 0, byteArray.size)
                )
            )
        }

        return roomerApi.putAdvertisement(refToken, roomId, list)
    }

    override suspend fun getCurrentUserRooms(token: String, hostId: Int): Response<List<Room>> {
        val refToken = "Token ".plus(token)
        return roomerApi.getCurrentUserAdvertisements(refToken, hostId)
    }

    override suspend fun getCities(token: String): Response<List<CityModel>> {
        val refToken = "Token ".plus(token)
        return roomerApi.getCities(refToken)
    }
}
