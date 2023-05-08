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
    suspend fun addMany(manyHistoryItems: List<HistoryItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(historyItem: HistoryItem)

    @Query("SELECT COUNT(*) FROM history")
    suspend fun count(): Long

    @Query("DELETE FROM history WHERE id = 0")
    suspend fun deleteFirst()

    @Query("DELETE FROM history")
    suspend fun clearHistory()

    @Query("SELECT * FROM history ORDER BY id ")
    suspend fun getHistory(): List<HistoryItem>

    suspend fun addToLocal(historyItem: HistoryItem) {
        add(historyItem)
        if (count() >= Constants.HISTORY_SIZE) {
            val history = getHistory().dropLast(1).map {
                val item = it
                item.historyId--
                item
            }
            clearHistory()
            addMany(history)
        }
    }
}
