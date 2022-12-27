package com.example.roomer.data.repository

import com.example.roomer.domain.model.interests.InterestModel
import com.example.roomer.domain.model.login.TokenDto
import com.example.roomer.domain.model.signup.IdModel
import retrofit2.Response

interface RoomerRepositoryInterface {
    suspend fun userLogin(email:String, password:String) : Response<TokenDto>
    suspend fun userSignUp(username: String, email:String, password:String) : Response<IdModel>
    suspend fun getInterests() : List<InterestModel>
}