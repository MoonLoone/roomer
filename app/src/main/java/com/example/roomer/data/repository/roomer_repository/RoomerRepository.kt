package com.example.roomer.data.repository.roomer_repository

import com.example.roomer.data.local.RoomerStoreInterface
import com.example.roomer.data.remote.RoomerApi
import com.example.roomer.domain.model.entities.Message
import com.example.roomer.domain.model.entities.MessageNotification
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.User
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class RoomerRepository @Inject constructor(
    private val roomerApi: RoomerApi,
    private val roomerStore: RoomerStoreInterface
) : RoomerRepositoryInterface {
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
}
