package com.example.roomer.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roomer.domain.model.entities.User
import com.example.roomer.room.dao.UserDao
import com.example.roomer.room.dao.FavouriteDao
import com.example.roomer.room.entities.LocalRoom

@Database(entities = [LocalRoom::class, User::class], version = 1)
abstract class RoomerDatabase: RoomDatabase() {
    abstract val favourites: FavouriteDao
    abstract val users: UserDao
}