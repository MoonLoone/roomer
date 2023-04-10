package com.example.roomer.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.roomer.room.entities.LocalRoom
import com.example.roomer.room.entities.RoomWithHost
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(newFavourites: List<LocalRoom>)

    @Delete
    suspend fun delete(room: LocalRoom)

    @Transaction
    @Query("SELECT * FROM favourite")
    fun queryAll(): Flow<List<RoomWithHost>>

    @Query("SELECT COUNT(*) FROM favourite")
    suspend fun count(): Long
}