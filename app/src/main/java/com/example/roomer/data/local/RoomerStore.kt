package com.example.roomer.data.local

import androidx.paging.PagingSource
import com.example.roomer.data.room.RoomerDatabase
import com.example.roomer.data.room.entities.LocalCurrentUser
import com.example.roomer.data.room.entities.LocalRoom
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.User
import kotlinx.coroutines.flow.Flow

class RoomerStore(
    database: RoomerDatabase
) : RoomerStoreInterface {
    private val favourites = database.favourites
    private val users = database.users
    private val currentUser = database.currentUser

    override suspend fun addFavourite(room: Room) {
        favourites.saveManyFavourites(listOf(room.toLocalRoom()))
    }

    override suspend fun addManyFavourites(favouriteRooms: List<Room>) {
        favourites.saveManyFavourites(favouriteRooms.map { it.toLocalRoom() })
    }

    override suspend fun isFavouritesEmpty(): Boolean = favourites.count() == 0L
    override suspend fun deleteFavourite(room: Room) {
        favourites.delete(room.toLocalRoom())
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

    private fun Room.toLocalRoom() = LocalRoom(
        id,
        monthPrice,
        host?.userId ?: -1,
        description,
        fileContent,
        bathroomsCount,
        bedroomsCount,
        housingType,
        sharingType,
        location,
        title,
        isLiked
    )
    override suspend fun clearFavourites() {
        favourites.deleteAll()
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
        city
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
        city
    )
}
