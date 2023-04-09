package com.example.roomer.local.dao

import androidx.room.*
import com.example.roomer.local.entities.LocalRoom
import com.example.roomer.local.entities.RoomWithHost

@Dao
interface FavouriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(newFavourites: List<LocalRoom>)

    @Delete
    suspend fun delete(room: LocalRoom)

    @Transaction
    @Query("SELECT * FROM favourite")
    fun queryAll(): List<RoomWithHost>

    @Query("SELECT COUNT(*) FROM favourite")
    suspend fun count(): Long
}