package com.example.roomer.data.repository

import android.graphics.Bitmap
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.User
import com.example.roomer.domain.model.login.TokenDto
import com.example.roomer.domain.model.signup.IdModel
import com.example.roomer.domain.model.signup.interests.InterestModel
import retrofit2.Response

interface AuthRepositoryInterface {
    suspend fun userLogin(email: String, password: String): Response<TokenDto>

    suspend fun userSignUp(username: String, email: String, password: String): Response<IdModel>

    suspend fun getInterests(): List<InterestModel>

    suspend fun putInterests(token: String, interests: List<InterestModel>): Response<IdModel>

    suspend fun putSignUpDataOne(
        token: String,
        firstName: String,
        lastName: String,
        sex: String,
        birthDate: String
    ): Response<IdModel>

    suspend fun putSignUpDataThree(
        token: String,
        sleepTime: String,
        alcoholAttitude: String,
        smokingAttitude: String,
        personalityType: String,
        cleanHabits: String
    ): Response<IdModel>

    suspend fun putSignUpDataTwo(
        token: String,
        avatar: Bitmap,
        aboutMe: String,
        employment: String
    ): Response<IdModel>

    suspend fun getFilterRooms(
        monthPriceFrom: String,
        monthPriceTo: String,
        bedroomsCount: String,
        bathroomsCount: String,
        housingType: String,
    ): Response<List<Room>>

    suspend fun getFilterRoommates(
        sex: String,
        employment: String,
        alcoholAttitude: String,
        smokingAttitude: String,
        sleepTime: String,
        personalityType: String,
        cleanHabits: String,
    ): Response<List<User>>

}
