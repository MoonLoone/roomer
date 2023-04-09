package com.example.roomer.data.repository

import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.User

interface RoomerStoreInterface {
    fun getFavourites(): List<Room>
    suspend fun addFavourite(room: Room)
    suspend fun insertFavourites(favouriteRooms: List<Room>)
    suspend fun isFavouritesEmpty(): Boolean
    suspend fun deleteFavourite(room: Room)
    fun getCurrentUser(): User
    suspend fun deleteCurrentUser()
    fun getAllUsers(): List<User>
    suspend fun deleteUser(user: User)
    fun getUserById(userId: Int): User
    suspend fun addUser(user: User)
    suspend fun addManyUsers(users: List<User>)
    suspend fun updateUser(user: User)
}