package com.example.roomer.data.remote

import com.example.roomer.domain.model.RoomsFilterInfo
import com.example.roomer.domain.model.UsersFilterInfo
import com.example.roomer.domain.model.login.LoginDto
import com.example.roomer.domain.model.login.TokenDto
import com.example.roomer.domain.model.signup.IdModel
import com.example.roomer.domain.model.signup.SignUpDataModel
import com.example.roomer.domain.model.signup.SignUpModel
import com.example.roomer.domain.model.signup.interests.InterestModel
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

interface RoomerApi {

    @POST("/auth/token/login/")
    suspend fun login(@Body loginDto: LoginDto): Response<TokenDto>

    @POST("/auth/users/")
    suspend fun signUp(@Body signUpModel: SignUpModel): Response<IdModel>

    @GET("/interests/")
    suspend fun getInterests(): List<InterestModel>

    @Multipart
    @PUT("/auth/users/me/")
    suspend fun putSignUpAvatar(
        @Header("Authorization") token: String,
        @Part avatar: MultipartBody.Part,
    ): Response<IdModel>

    @PUT("/auth/users/me/")
    suspend fun putSignUpData(
        @Header("Authorization") token: String,
        @Body signUpDataModel: SignUpDataModel
    ): Response<IdModel>

    @GET("/housing/")
    suspend fun filterRooms(
        @Query("month_price_from") monthPriceFrom: String,
        @Query("month_price_to") monthPriceTo: String,
        @Query(" bedrooms_count") bedroomsCount: String,
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
