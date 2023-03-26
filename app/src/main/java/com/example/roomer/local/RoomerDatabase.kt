package com.example.roomer.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roomer.local.dao.FavouriteDao
import com.example.roomer.local.tables.LocalFavourite

@Database(entities = [LocalFavourite::class], version = 1)
abstract class RoomerDatabase: RoomDatabase() {
    abstract val favourites: FavouriteDao
}