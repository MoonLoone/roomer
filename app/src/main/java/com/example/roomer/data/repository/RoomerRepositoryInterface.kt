package com.example.roomer.data.repository

import com.example.roomer.domain.model.interests.InterestModel
import com.example.roomer.domain.model.login.TokenDto
import com.example.roomer.domain.model.signup.IdModel
import retrofit2.Response

interface RoomerRepositoryInterface {
    suspend fun userLogin(email:String, password:String) : Response<TokenDto>
    suspend fun userSignUp(username: String, email:String, password:String) : Response<IdModel>
    suspend fun getInterests() : List<InterestModel>
    suspend fun putInterests(token: String, interests: List<InterestModel>) : Response<IdModel>
    suspend fun putSignUpDataOne(
        token: String,
        firstName: String,
        lastName: String,
        sex: String,
        birthDate: String
        ) : Response<IdModel>
    suspend fun putSignUpDataThree(
        token: String,
        sleepTime: String,
        alcoholAttitude: String,
        smokingAttitude: String,
        personalityType: String,
        cleanHabits: String
        ) : Response<IdModel>
}