package com.example.roomer.data.local

import androidx.paging.PagingSource
import com.example.roomer.data.room.RoomerDatabase
import com.example.roomer.data.room.entities.HistoryItem
import com.example.roomer.data.room.entities.LocalCurrentUser
import com.example.roomer.data.room.entities.LocalMessage
import com.example.roomer.data.room.entities.LocalRoom
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.User
import com.example.roomer.domain.model.entities.toLocalRoom
import kotlinx.coroutines.flow.Flow

class RoomerStore(
    database: RoomerDatabase
) : RoomerStoreInterface {
    private val favourites = database.favourites
    private val users = database.users
    private val currentUser = database.currentUser
    private val messages = database.messages
    private val history = database.history

    override suspend fun addRoomToHistory(room: LocalRoom) {
        history.addToLocal(HistoryItem(room = room))
    }

    override suspend fun addUserToHistory(user: User) {
        history.addToLocal(HistoryItem(user = user))
    }

    override suspend fun getHistory(): List<HistoryItem> {
        return history.getHistory()
    }

    override suspend fun addFavourite(room: Room) {
        favourites.saveManyFavourites(listOf(room.toLocalRoom()))
    }

    override suspend fun addManyFavourites(favouriteRooms: List<Room>) {
        favourites.saveManyFavourites(
            favouriteRooms.map {
                it.toLocalRoom()
            }
        )
    }

    override suspend fun isFavouritesEmpty(): Boolean = favourites.count() == 0L

    override suspend fun deleteFavourite(room: Room) {
        favourites.deleteById(room.id)
    }

    override suspend fun getCurrentUser() = currentUser.read().toUser()

    override suspend fun addCurrentUser(user: User) = currentUser.create(user.toLocalCurrentUser())

    override suspend fun updateCurrentUser(user: User) = currentUser.update(
        user.toLocalCurrentUser()
    )

    override suspend fun deleteCurrentUser() = currentUser.delete()

    override suspend fun getAllUsers(): Flow<List<User>> = users.queryAll()

    override suspend fun deleteUser(user: User) {
        users.delete(user)
    }

    override suspend fun getUserById(userId: Int): User {
        return users.getUserById(userId)
    }

    override suspend fun addUser(user: User) {
        users.add(user)
    }

    override suspend fun addManyUsers(users: List<User>) {
        return this.users.addMany(users)
    }

    override suspend fun updateUser(user: User) = users.updateOne(user)

    override fun getPagingFavourites(): PagingSource<Int, LocalRoom> {
        return favourites.getPagingFavourites()
    }

    override suspend fun clearFavourites() {
        favourites.deleteAll()
    }

    override suspend fun addLocalMessage(message: LocalMessage) {
        messages.saveMessage(message)
    }

    override suspend fun clearLocalMessages() {
        messages.clear()
    }

    override suspend fun saveManyLocalMessages(manyMessages: List<LocalMessage>) {
        messages.saveManyMessages(manyMessages)
    }

    override fun getPagingMessages(): PagingSource<Int, LocalMessage> {
        return messages.getPagingMessages()
    }

    private fun LocalCurrentUser.toUser() = User(
        userId,
        firstName,
        lastName,
        avatar,
        employment,
        sex,
        alcoholAttitude,
        smokingAttitude,
        sleepTime,
        personalityType,
        cleanHabits,
        rating,
        interests,
        city,
        birthDate,
        aboutMe
    )

    private fun User.toLocalCurrentUser() = LocalCurrentUser(
        userId,
        firstName,
        lastName,
        avatar,
        employment,
        sex,
        alcoholAttitude,
        smokingAttitude,
        sleepTime,
        personalityType,
        cleanHabits,
        rating,
        interests,
        city,
        birthDate,
        aboutMe
    )
}
