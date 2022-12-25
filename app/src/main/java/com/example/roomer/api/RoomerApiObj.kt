package com.example.roomer.api

import com.example.roomer.utils.Consts
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RoomerApiObj {
    private val retrofit = Retrofit.Builder()
        .baseUrl(Consts.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val api: RoomerApi = retrofit.create()
}