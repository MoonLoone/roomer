package com.example.roomer.data.repository

import com.example.roomer.data.remote.RoomerApi
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.User
import retrofit2.Response
import javax.inject.Inject

class SearchRepository @Inject constructor(private val roomerApi: RoomerApi) :
    SearchRepositoryInterface {

    override suspend fun getFilterRooms(
        monthPriceFrom: String,
        monthPriceTo: String,
        bedroomsCount: String,
        bathroomsCount: String,
        housingType: String
    ): Response<List<Room>> {
        return roomerApi.filterRooms(
            monthPriceFrom,
            monthPriceTo,
            bedroomsCount,
            bathroomsCount,
            housingType,
        )
    }

    override suspend fun getFilterRoommates(
        sex: String,
        employment: String,
        alcoholAttitude: String,
        smokingAttitude: String,
        sleepTime: String,
        personalityType: String,
        cleanHabits: String
    ): Response<List<User>> {
        return roomerApi.filterRoommates(
            sex,
            employment,
            alcoholAttitude,
            smokingAttitude,
            sleepTime,
            personalityType,
            cleanHabits,
        )
    }
}
