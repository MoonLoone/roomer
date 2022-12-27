package com.example.roomer.data.remote

import com.example.roomer.domain.model.interests.InterestModel
import com.example.roomer.domain.model.login.LoginDto
import com.example.roomer.domain.model.login.TokenDto
import com.example.roomer.domain.model.signup.IdModel
import com.example.roomer.domain.model.signup.SignUpModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RoomerApi {

    @POST("/auth/token/login/")
    suspend fun login(@Body loginDto: LoginDto) : Response<TokenDto>

    @POST("/auth/users/")
    suspend fun signUp(@Body signUpModel: SignUpModel) : Response<IdModel>

    @GET("/interests/")
    suspend fun getInterests() : List<InterestModel>

}