package com.example.roomer.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roomer.domain.model.entities.User
import com.example.roomer.local.dao.UserDao
import com.example.roomer.local.dao.FavouriteDao
import com.example.roomer.local.entities.LocalRoom

@Database(entities = [LocalRoom::class, User::class], version = 1)
abstract class RoomerDatabase: RoomDatabase() {
    abstract val favourites: FavouriteDao
    abstract val users: UserDao
}