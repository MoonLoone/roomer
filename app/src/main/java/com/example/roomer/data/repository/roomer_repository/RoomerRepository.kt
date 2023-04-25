package com.example.roomer.data.repository.roomer_repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.roomer.data.local.RoomerStoreInterface
import com.example.roomer.data.paging.RoomerPagingSource
import com.example.roomer.data.remote.RoomerApi
import com.example.roomer.domain.model.entities.Message
import com.example.roomer.domain.model.entities.MessageNotification
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.User
import com.example.roomer.utils.Constants
import com.example.roomer.utils.PagingFactories
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class RoomerRepository @Inject constructor(
    private val roomerApi: RoomerApi,
    private val roomerStore: RoomerStoreInterface
) : RoomerRepositoryInterface {

    override var pagingSource: RoomerPagingSource<Room>? = null

    override suspend fun getChats(userId: Int): Response<List<Message>> {
        return roomerApi.getChatsForUser(userId, "")
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getFavourites(
        userId: Int,
        limit: Int
    ): Flow<PagingData<Room>> {
        val pager = Pager(
            PagingConfig(
                pageSize = Constants.Chat.PAGE_SIZE,
                maxSize = Constants.Chat.CASH_SIZE,
                initialLoadSize = Constants.Chat.INITIAL_SIZE
            ),
            remoteMediator = PagingFactories.createFavouritesMediator(
                apiFunction = { offset ->
                    roomerApi.getFavourites(userId, offset, limit).body()?.map {
                        val housing = it.housing ?: Room(0)
                        housing.isLiked = true
                        housing
                    }
                },
                saveFunction = { response ->
                    roomerStore.addManyFavourites(response as List<Room>)
                    pagingSource?.invalidate()
                },
                deleteFunction = {
                    roomerStore.clearFavourites()
                }
            ),
            pagingSourceFactory = {
                pagingSource = PagingFactories.createFavouritesPagingSource { offset, limit ->
                    val source = roomerStore.getFavourites(limit, offset)
                    source
                }
                pagingSource!!
            }
        )
        return pager.flow
    }

    override suspend fun likeHousing(housingId: Int, userId: Int): Response<String> {
        return roomerApi.addToFavourite(userId, housingId)
    }

    override suspend fun dislikeHousing(housingId: Int, userId: Int): Response<String> {
        return roomerApi.deleteFavourite(userId, housingId)
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

    override suspend fun getLocalFavourites(): List<Room> = roomerStore.getFavourites()

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
            monthPriceFrom,
            monthPriceTo,
            location,
            bedroomsCount,
            bathroomsCount,
            housingType
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
            sex,
            location,
            ageFrom,
            ageTo,
            employment,
            alcoholAttitude,
            smokingAttitude,
            sleepTime,
            personalityType,
            cleanHabits,
            interests
        )
    }
}
