package com.example.roomer.retrofit

import retrofit2.http.GET


interface RetrofitService {

    @GET("/users")
    suspend fun getUsers()
}