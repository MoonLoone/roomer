package com.example.roomer.data.repository

import com.example.roomer.data.remote.RoomerApi
import com.example.roomer.domain.model.interests.InterestModel
import com.example.roomer.domain.model.login.LoginDto
import com.example.roomer.domain.model.login.TokenDto
import com.example.roomer.domain.model.signup.IdModel
import com.example.roomer.domain.model.signup.SignUpModel
import retrofit2.Response

class RoomerRepository (
    private val roomerApi: RoomerApi
    ) : RoomerRepositoryInterface {
    override suspend fun userLogin(
        email: String,
        password: String
    ): Response<TokenDto> {
        return roomerApi.login(LoginDto(email, password))
    }

    override suspend fun userSignUp(
        username: String,
        email: String,
        password: String
    ): Response<IdModel> {
        return roomerApi.signUp(SignUpModel(username, password, email))
    }

    override suspend fun getInterests(): List<InterestModel> {
        return roomerApi.getInterests()
    }
}