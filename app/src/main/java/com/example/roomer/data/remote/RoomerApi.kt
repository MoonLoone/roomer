package com.example.roomer.data.remote

import com.example.roomer.domain.model.RoomsFilterInfo
import com.example.roomer.domain.model.UsersFilterInfo
import com.example.roomer.domain.model.login.LoginDto
import com.example.roomer.domain.model.login.TokenDto
import com.example.roomer.domain.model.signup.IdModel
import com.example.roomer.domain.model.signup.SignUpModel
import com.example.roomer.domain.model.signup.interests.InterestModel
import com.example.roomer.domain.model.signup.interests.PutInterestsModel
import com.example.roomer.domain.model.signup.signup_one.SignUpOneModel
import com.example.roomer.domain.model.signup.signup_three.SignUpThreeModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Query


interface RoomerApi {

    @POST("/auth/token/login/")
    suspend fun login(@Body loginDto: LoginDto): Response<TokenDto>

    @POST("/auth/users/")
    suspend fun signUp(@Body signUpModel: SignUpModel): Response<IdModel>

    @GET("/interests/")
    suspend fun getInterests(): List<InterestModel>

    @PUT("/auth/users/me/")
    suspend fun putInterests(
        @Header("Authorization") token: String,
        @Body putInterestsModel: PutInterestsModel
    ): Response<IdModel>

    @PUT("/auth/users/me/")
    suspend fun putSignUpDataOne(
        @Header("Authorization") token: String,
        @Body SignUpOneModel: SignUpOneModel
    ): Response<IdModel>

    @PUT("/auth/users/me/")
    suspend fun putSignUpDataThree(
        @Header("Authorization") token: String,
        @Body SignUpThreeModel: SignUpThreeModel
    ): Response<IdModel>

    @Multipart
    @PUT("/auth/users/me/")
    suspend fun putSignUpDataTwo(
        @Header("Authorization") token: String,
        @Part avatar: MultipartBody.Part,
        @PartMap signUpTwoModel: HashMap<String, RequestBody>
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
