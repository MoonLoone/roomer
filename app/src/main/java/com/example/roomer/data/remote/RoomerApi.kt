package com.example.roomer.data.remote

import com.example.roomer.domain.model.signup.interests.InterestModel
import com.example.roomer.domain.model.signup.interests.PutInterestsModel
import com.example.roomer.domain.model.login.LoginDto
import com.example.roomer.domain.model.login.TokenDto
import com.example.roomer.domain.model.signup.IdModel
import com.example.roomer.domain.model.signup.SignUpModel
import com.example.roomer.domain.model.signup.signup_one.SignUpOneModel
import com.example.roomer.domain.model.signup.signup_three.SignUpThreeModel
import com.example.roomer.domain.model.signup.signup_two.SignUpTwoModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface RoomerApi {

    @POST("/auth/token/login/")
    suspend fun login(@Body loginDto: LoginDto) : Response<TokenDto>

    @POST("/auth/users/")
    suspend fun signUp(@Body signUpModel: SignUpModel) : Response<IdModel>

    @GET("/interests/")
    suspend fun getInterests() : List<InterestModel>

    @PUT("/auth/users/me/")
    suspend fun putInterests(
        @Header("Authorization") token: String,
        @Body putInterestsModel: PutInterestsModel
    ) : Response<IdModel>

    @PUT("/auth/users/me/")
    suspend fun putSignUpDataOne(
        @Header("Authorization") token: String,
        @Body SignUpOneModel: SignUpOneModel
    ) : Response<IdModel>

    @PUT("/auth/users/me/")
    suspend fun putSignUpDataThree(
        @Header("Authorization") token: String,
        @Body SignUpThreeModel: SignUpThreeModel
    ) : Response<IdModel>

    @Multipart
    @PUT("/auth/users/me/")
    suspend fun putSignUpDataTwo(
        @Header("Authorization") token: String,
        @Part avatar: MultipartBody.Part,
        @PartMap signUpTwoModel: HashMap<String, RequestBody>
        ) : Response<IdModel>
}