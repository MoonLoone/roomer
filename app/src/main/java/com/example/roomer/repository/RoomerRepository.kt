package com.example.roomer.repository

import com.example.roomer.models.RoomsFilterInfo
import com.example.roomer.models.UsersFilterInfo
import com.example.roomer.retrofit.RetrofitBuilder
import retrofit2.Response

object RoomerRepository {

    suspend fun getFilterRooms(
        monthPriceFrom: String,
        monthPriceTo: String,
        bedroomsCount: String,
        bathroomsCount: String,
        housingType: String,
    ): Response<List<RoomsFilterInfo>> {
        return RetrofitBuilder.apiService.filterRooms(
            monthPriceFrom,
            monthPriceTo,
            bedroomsCount,
            bathroomsCount,
            housingType,
        )
    }


    suspend fun getFilterRoommates(
        sex: String,
        employment: String,
        alcoholAttitude: String,
        smokingAttitude: String,
        sleepTime: String,
        personalityType: String,
        cleanHabits: String,
    ): Response<List<UsersFilterInfo>> {
        return RetrofitBuilder.apiService.filterRoommates(
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