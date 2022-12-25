package com.example.roomer.api.repository

import com.example.roomer.api.RoomerApi
import com.example.roomer.api.dto.LoginDto
import com.example.roomer.api.dto.TokenDto
import retrofit2.Response

class RoomerRepository (
    private val roomerApi: RoomerApi
        ) : RoomerRepositoryInterface {
    override suspend fun userLogin(email: String, password: String): Response<TokenDto> {
        return roomerApi.login(LoginDto(email, password))
    }
}