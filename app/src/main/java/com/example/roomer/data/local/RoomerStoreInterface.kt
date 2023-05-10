package com.example.roomer.data.local

import androidx.paging.PagingSource
import com.example.roomer.data.room.entities.HistoryItem
import com.example.roomer.data.room.entities.LocalMessage
import com.example.roomer.data.room.entities.LocalRoom
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.User
import kotlinx.coroutines.flow.Flow

interface RoomerStoreInterface {
    suspend fun addRoomToHistory(room: LocalRoom)
    suspend fun addUserToHistory(user: User)
    suspend fun getHistory(): List<HistoryItem>
    suspend fun addFavourite(room: Room)
    suspend fun addManyFavourites(favouriteRooms: List<Room>)
    suspend fun isFavouritesEmpty(): Boolean
    suspend fun deleteFavourite(room: Room)
    suspend fun getCurrentUser(): User
    suspend fun addCurrentUser(user: User)
    suspend fun updateCurrentUser(user: User)
    suspend fun deleteCurrentUser()
    suspend fun getAllUsers(): Flow<List<User>>
    suspend fun deleteUser(user: User)
    suspend fun getUserById(userId: Int): User
    suspend fun addUser(user: User)
    suspend fun addManyUsers(users: List<User>)
    suspend fun updateUser(user: User)
    fun getPagingFavourites(): PagingSource<Int, LocalRoom>
    suspend fun clearFavourites()
    suspend fun addLocalMessage(message: LocalMessage)
    suspend fun clearLocalMessages()
    suspend fun saveManyLocalMessages(manyMessages: List<LocalMessage>)
    fun getPagingMessages(): PagingSource<Int, LocalMessage>
}
