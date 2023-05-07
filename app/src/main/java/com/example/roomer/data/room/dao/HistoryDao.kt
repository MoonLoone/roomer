package com.example.roomer.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.roomer.data.room.entities.HistoryItem
import com.example.roomer.utils.Constants

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(historyItem: HistoryItem)

    @Query("SELECT COUNT(*) FROM history")
    suspend fun count(): Long

    @Query("DELETE FROM history WHERE id = ${Constants.HISTORY_SIZE}")
    suspend fun deleteLast()

    @Query("DELETE FROM history")
    suspend fun clearHistory()

    @Query("SELECT * FROM history")
    suspend fun getHistory(): List<HistoryItem>

    suspend fun addToLocal(historyItem: HistoryItem){
        if (count() == Constants.HISTORY_SIZE) deleteLast()
        add(historyItem)
    }

}