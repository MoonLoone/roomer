package com.example.roomer.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.roomer.data.room.entities.LocalCurrentUser

@Dao
interface CurrentUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(user: LocalCurrentUser)

    @Query("SELECT * FROM currentUser LIMIT 1")
    suspend fun read(): LocalCurrentUser?

    @Update
    suspend fun update(user: LocalCurrentUser)

    @Query("DELETE FROM currentUser")
    suspend fun delete()
}
