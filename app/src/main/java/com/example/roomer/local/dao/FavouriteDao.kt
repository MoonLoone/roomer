package com.example.roomer.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.roomer.local.tables.LocalFavourite

@Dao
interface FavouriteDao {
    @Query("select * from favourite")
    fun queryAll(): List<LocalFavourite>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(newFavourite: LocalFavourite)
}