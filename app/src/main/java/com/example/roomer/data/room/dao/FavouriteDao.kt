package com.example.roomer.data.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.roomer.data.room.entities.LocalRoom
import com.example.roomer.data.room.entities.RoomWithHost
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(newFavourites: List<LocalRoom>)

    @Delete
    suspend fun delete(room: LocalRoom)

    @Query("DELETE FROM favourite WHERE roomId=:id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM favourite")
    suspend fun deleteAll()

    @Transaction
    @Query("SELECT * FROM favourite")
    fun queryAll(): Flow<List<RoomWithHost>>

    @Transaction
    @Query("SELECT * FROM favourite")
    fun getPagingFavourites(): PagingSource<Int, RoomWithHost>

    @Query("SELECT * FROM favourite LIMIT :limit OFFSET :offset")
    suspend fun getAll(limit: Int, offset: Int): List<RoomWithHost>

    @Query("SELECT COUNT(*) FROM favourite")
    suspend fun count(): Long
}
