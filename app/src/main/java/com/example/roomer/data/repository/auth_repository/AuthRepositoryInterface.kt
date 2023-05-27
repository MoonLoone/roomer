package com.example.roomer.data.repository.auth_repository

import android.graphics.Bitmap
import com.example.roomer.domain.model.entities.User
import com.example.roomer.domain.model.login_sign_up.IdModel
import com.example.roomer.domain.model.login_sign_up.InterestModel
import com.example.roomer.domain.model.login_sign_up.TokenDto
import retrofit2.Response

interface AuthRepositoryInterface {
    suspend fun userLogin(email: String, password: String): Response<TokenDto>

    suspend fun userSignUpPrimary(
        username: String,
        email: String,
        password: String
    ): Response<IdModel>

    suspend fun getInterests(): List<InterestModel>

    suspend fun putSignUpData(
        token: String,
        firstName: String,
        lastName: String,
        sex: String,
        birthDate: String,
        aboutMe: String,
        employment: String,
        sleepTime: String,
        alcoholAttitude: String,
        smokingAttitude: String,
        personalityType: String,
        cleanHabits: String,
        interests: List<InterestModel>,
        city: String
    ): Response<User>

    suspend fun putSignUpAvatar(
        token: String,
        avatar: Bitmap
    ): Response<User>

    suspend fun logout(
        token: String
    ): Response<Unit>
}
