package com.example.roomer.api.repository

import com.example.roomer.api.dto.TokenDto
import retrofit2.Response

interface RoomerRepositoryInterface {
    suspend fun userLogin(email:String, password:String) : Response<TokenDto>

}