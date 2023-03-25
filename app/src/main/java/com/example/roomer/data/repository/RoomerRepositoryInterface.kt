package com.example.roomer.data.repository

import android.graphics.Bitmap
import com.example.roomer.domain.model.RoomsFilterInfo
import com.example.roomer.domain.model.UsersFilterInfo
import com.example.roomer.domain.model.login_sign_up.TokenDto
import com.example.roomer.domain.model.login_sign_up.IdModel
import com.example.roomer.domain.model.login_sign_up.interests.InterestModel
import retrofit2.Response

interface RoomerRepositoryInterface {
    suspend fun userLogin(email: String, password: String): Response<TokenDto>

    suspend fun userSignUpPrimary(username: String, email: String, password: String): Response<IdModel>

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
        interests: List<InterestModel>
    ): Response<IdModel>

    suspend fun putSignUpAvatar(
        token: String,
        avatar: Bitmap
    ): Response<IdModel>

    suspend fun getFilterRooms(
        monthPriceFrom: String,
        monthPriceTo: String,
        bedroomsCount: String,
        bathroomsCount: String,
        housingType: String,
    ): Response<List<RoomsFilterInfo>>

    suspend fun getFilterRoommates(
        sex: String,
        employment: String,
        alcoholAttitude: String,
        smokingAttitude: String,
        sleepTime: String,
        personalityType: String,
        cleanHabits: String,
    ): Response<List<UsersFilterInfo>>
}
