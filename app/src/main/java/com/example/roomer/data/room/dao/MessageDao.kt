package com.example.roomer.data.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.roomer.data.room.entities.LocalMessage

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMessage(message: LocalMessage)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveManyMessages(messages: List<LocalMessage>)

    @Delete
    fun deleteMessage(message: LocalMessage)

    @Query("DELETE FROM messages")
    fun clear()

    @Query("SELECT * FROM messages ORDER BY id DESC")
    fun getPagingMessages(): PagingSource<Int, LocalMessage>
}
