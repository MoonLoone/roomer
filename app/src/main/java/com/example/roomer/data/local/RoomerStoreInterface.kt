package com.example.roomer.data.local

import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.User
import kotlinx.coroutines.flow.Flow

interface RoomerStoreInterface {
    suspend fun getFavourites(): Flow<List<Room>>
    suspend fun addFavourite(room: Room)
    suspend fun insertFavourites(favouriteRooms: List<Room>)
    suspend fun isFavouritesEmpty(): Boolean
    suspend fun deleteFavourite(room: Room)
    fun getCurrentUser(): User
    suspend fun deleteCurrentUser()
    fun getAllUsers(): Flow<List<User>>
    suspend fun deleteUser(user: User)
    fun getUserById(userId: Int): User
    suspend fun addUser(user: User)
    suspend fun addManyUsers(users: List<User>)
    suspend fun updateUser(user: User)
}