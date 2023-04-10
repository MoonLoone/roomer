package com.example.roomer.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.roomer.domain.model.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun queryAll(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMany(users: List<User>)

    @Query("SELECT * FROM user WHERE isCurrentUser=1")
    fun getCurrentUser(): User

    @Query("DELETE FROM user WHERE isCurrentUser=1")
    suspend fun deleteCurrentUser()

    @Query("SELECT * FROM user WHERE userId=:userId")
    fun getUserById(userId: Int): User

    @Delete
    suspend fun delete(user: User)

    @Update(entity = User::class)
    suspend fun updateOneUser(user: User)

    @Query("DELETE FROM user")
    suspend fun clearAll()
}