package com.example.roomer.dependencyinjection

import com.example.roomer.api.RoomerApi
import com.example.roomer.api.repository.RoomerRepository
import com.example.roomer.api.repository.RoomerRepositoryInterface
import com.example.roomer.utils.Consts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun roomerRepository(api: RoomerApi) = RoomerRepository(api) as RoomerRepositoryInterface

    @Singleton
    @Provides
    fun injectBackendRetrofitApi() : RoomerApi {

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Consts.BASE_URL)
            .build()
            .create(RoomerApi::class.java)
    }
}