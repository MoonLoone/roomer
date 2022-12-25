package com.example.roomer.api

import com.example.roomer.api.dto.LoginDto
import com.example.roomer.api.dto.TokenDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RoomerApi {
    @POST("/auth/users/")
    suspend fun login(@Body loginDto: LoginDto) : Response<TokenDto>
}