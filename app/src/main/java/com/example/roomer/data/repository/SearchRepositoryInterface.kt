package com.example.roomer.data.repository

import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.User
import retrofit2.Response

interface SearchRepositoryInterface {
    suspend fun getFilterRooms(
        monthPriceFrom: String,
        monthPriceTo: String,
        bedroomsCount: String,
        bathroomsCount: String,
        housingType: String,
    ): Response<List<Room>>

    suspend fun getFilterRoommates(
        sex: String?,
        employment: String,
        alcoholAttitude: String,
        smokingAttitude: String,
        sleepTime: String,
        personalityType: String,
        cleanHabits: String,
    ): Response<List<User>>
}
