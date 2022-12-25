package com.example.roomer.api.repository

import com.example.roomer.api.RoomerApi
import com.example.roomer.api.dto.LoginDto
import com.example.roomer.api.dto.TokenDto
import retrofit2.Response
import javax.inject.Inject

class RoomerRepository @Inject constructor(
    private val api: RoomerApi
    ) : RoomerRepositoryInterface {
    override suspend fun userLogin(email: String, password: String): Response<TokenDto> {
        return api.login(LoginDto(email, password))
    }
}