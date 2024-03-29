package com.example.roomer.data.room.dao

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

    @Insert(entity = User::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(user: User)

    @Insert(entity = User::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMany(users: List<User>)

    @Query("SELECT * FROM user WHERE userId=:userId")
    suspend fun getUserById(userId: Int): User

    @Delete
    suspend fun delete(user: User)

    @Update(entity = User::class)
    suspend fun updateOne(user: User)

    @Query("DELETE FROM user")
    suspend fun deleteAll()
}
