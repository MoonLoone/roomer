package com.example.roomer.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.roomer.data.room.dao.CurrentUserDao
import com.example.roomer.data.room.dao.FavouriteDao
import com.example.roomer.data.room.dao.HistoryDao
import com.example.roomer.data.room.dao.MessageDao
import com.example.roomer.data.room.dao.UserDao
import com.example.roomer.data.room.entities.HistoryItem
import com.example.roomer.data.room.entities.LocalCurrentUser
import com.example.roomer.data.room.entities.LocalMessage
import com.example.roomer.data.room.entities.LocalRoom
import com.example.roomer.domain.model.entities.User

@Database(
    entities = [LocalRoom::class, User::class, LocalCurrentUser::class, LocalMessage::class, HistoryItem::class],
    version = 10
)
@TypeConverters(RoomerConverters::class)
abstract class RoomerDatabase : RoomDatabase() {
    abstract val favourites: FavouriteDao
    abstract val users: UserDao
    abstract val currentUser: CurrentUserDao
    abstract val messages: MessageDao
    abstract val history: HistoryDao
}
