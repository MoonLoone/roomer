package com.example.roomer.di

import android.content.Context
import androidx.room.Room
import com.example.roomer.data.local.RoomerStore
import com.example.roomer.data.local.RoomerStoreInterface
import com.example.roomer.data.room.RoomerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideRoomerDatabase(@ApplicationContext context: Context): RoomerDatabase {
        return Room
            .databaseBuilder(context, RoomerDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideRoomerStore(database: RoomerDatabase): RoomerStoreInterface {
        return RoomerStore(database)
    }
}
