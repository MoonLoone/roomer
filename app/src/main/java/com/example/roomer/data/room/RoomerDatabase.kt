package com.example.roomer.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.roomer.data.room.dao.CurrentUserDao
import com.example.roomer.data.room.dao.FavouriteDao
import com.example.roomer.data.room.dao.UserDao
import com.example.roomer.data.room.entities.LocalCurrentUser
import com.example.roomer.data.room.entities.LocalRoom
import com.example.roomer.domain.model.entities.User

@Database(entities = [LocalRoom::class, User::class, LocalCurrentUser::class], version = 4)
@TypeConverters(RoomerConverters::class)
abstract class RoomerDatabase : RoomDatabase() {
    abstract val favourites: FavouriteDao
    abstract val users: UserDao
    abstract val currentUser: CurrentUserDao
}
