package com.example.roomer.retrofit

import com.example.roomer.models.RoomsFilterInfo
import com.example.roomer.models.UsersFilterInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface RetrofitService {

    @GET("/housing/")
    suspend fun filterRooms(
        @Query("month_price_from") monthPriceFrom: String,
        @Query("month_price_to") monthPriceTo: String,
        @Query("bedrooms_count") bedroomsCount: String,
        @Query("bathrooms_count") bathroomsCount: String,
        @Query("housing_type") housingType: String,
    ): Response<List<RoomsFilterInfo>>

    @GET("/profile/")
    suspend fun filterRoommates(
        @Query("sex") sex: String,
        @Query("employment") employment: String,
        @Query("alcohol_attitude") alcoholAttitude: String,
        @Query("smoking_attitude") smokingAttitude: String,
        @Query("sleep_time") sleepTime: String,
        @Query("personality_type") personalityType: String,
        @Query("clean_habits") cleanHabits: String,
    ): Response<List<UsersFilterInfo>>

}