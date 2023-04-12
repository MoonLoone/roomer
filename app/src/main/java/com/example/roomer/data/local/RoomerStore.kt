package com.example.roomer.data.local

import com.example.roomer.data.room.RoomerDatabase
import com.example.roomer.data.room.entities.LocalCurrentUser
import com.example.roomer.data.room.entities.LocalRoom
import com.example.roomer.data.room.entities.RoomWithHost
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomerStore(
    database: RoomerDatabase
) : RoomerStoreInterface {
    private val favourites = database.favourites
    private val users = database.users
    private val currentUser = database.currentUser

    override suspend fun getFavourites(): Flow<List<Room>> = favourites.queryAll()
        .map { localRoomList -> localRoomList.map { localRoom -> localRoom.toRoom() } }

    override suspend fun addFavourite(room: Room) {
        favourites.save(listOf(room.toLocalRoom()))
    }

    override suspend fun addManyFavourites(favouriteRooms: List<Room>) {
        favourites.save(favouriteRooms.map { it.toLocalRoom() })
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

    private fun Room.toLocalRoom() = LocalRoom(
        id,
        monthPrice,
        host?.userId ?: 0,
        description,
        fileContent.first().photo,
        bathroomsCount,
        bedroomsCount,
        housingType,
        sharingType,
        location,
        title,
        isLiked
    )

    private fun RoomWithHost.toRoom() = Room(
        room.roomId,
        room.monthPrice,
        host,
        room.description,
        listOf(Room.Photo(room.photo)),
        room.bathroomsCount,
        room.bedroomsCount,
        room.housingType,
        room.sharingType,
        room.location,
        room.title,
        room.isLiked
    )

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
        rating
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
        rating
    )
}
