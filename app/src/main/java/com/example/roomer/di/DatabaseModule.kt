package com.example.roomer.di

import android.content.Context
import androidx.room.Room
import com.example.roomer.data.repository.RoomerStore
import com.example.roomer.data.repository.RoomerStoreInterface
import com.example.roomer.local.RoomerDatabase
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
            .build()
    }
    @Singleton
    @Provides
    fun provideRoomerStore(database: RoomerDatabase): RoomerStoreInterface {
        return RoomerStore(database)
    }
}