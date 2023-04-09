package com.example.roomer.data.repository

import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.User
import com.example.roomer.local.RoomerDatabase
import com.example.roomer.local.entities.LocalRoom
import com.example.roomer.local.entities.RoomWithHost

class RoomerStore(
    database: RoomerDatabase
) : RoomerStoreInterface {
    private val favourites = database.favourites
    private val users = database.users

    override fun getFavourites(): List<Room> = favourites.queryAll().map { it.toRoom() }

    override suspend fun addFavourite(room: Room) {
        favourites.save(listOf(room.toLocalRoom()))
    }

    override suspend fun insertFavourites(favouriteRooms: List<Room>) {
        favourites.save(favouriteRooms.map { it.toLocalRoom() })
    }

    override suspend fun isFavouritesEmpty(): Boolean = favourites.count() == 0L
    override suspend fun deleteFavourite(room: Room) {
        favourites.delete(room.toLocalRoom())
    }

    override fun getCurrentUser(): User {
        return users.getCurrentUser()
    }

    override suspend fun deleteCurrentUser() {
        users.deleteCurrentUser()
    }

    override fun getAllUsers(): List<User> {
        return users.queryAll()
    }

    override suspend fun deleteUser(user: User) {
        users.delete(user)
    }

    override fun getUserById(userId: Int): User {
        return users.getUserById(userId)
    }

    override suspend fun addUser(user: User) {
        users.add(user)
    }

    override suspend fun addManyUsers(users: List<User>) {
        return this.users.addMany(users)
    }

    override suspend fun updateUser(user: User) {
        users.updateOneUser(user)
    }

    private fun Room.toLocalRoom() = LocalRoom(
        id,
        monthPrice,
        host?.userId?.toLong() ?: 0,
        description,
        fileContent[0].photo,
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
}