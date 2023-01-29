package com.example.roomer.data.repository

import com.example.roomer.data.remote.RoomerApi
import com.example.roomer.domain.model.interests.InterestModel
import com.example.roomer.domain.model.interests.PutInterestsModel
import com.example.roomer.domain.model.login.LoginDto
import com.example.roomer.domain.model.login.TokenDto
import com.example.roomer.domain.model.signup.IdModel
import com.example.roomer.domain.model.signup.SignUpModel
import com.example.roomer.domain.model.signupone.SignUpOneModel
import com.example.roomer.domain.model.signupthree.SignUpThreeModel
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

    override suspend fun putInterests(
        token: String,
        interests: List<InterestModel>
    ): Response<IdModel> {
        val refToken = "Token ".plus(token)

        return roomerApi.putInterests(
            token = refToken,
            PutInterestsModel(interests)
        )
    }

    override suspend fun putSignUpDataOne(
        token: String,
        firstName: String,
        lastName: String,
        sex: String,
        birthDate: String
    ): Response<IdModel> {
        val refToken = "Token ".plus(token)

        return roomerApi.putSignUpDataOne(
            refToken,
            SignUpOneModel(birthDate, sex, firstName, lastName)
        )
    }

    override suspend fun putSignUpDataThree(
        token: String,
        sleepTime: String,
        alcoholAttitude: String,
        smokingAttitude: String,
        personalityType: String,
        cleanHabits: String
    ): Response<IdModel> {
        val refToken = "Token ".plus(token)
        return roomerApi.putSignUpDataThree(
            refToken,
            SignUpThreeModel(
                sleepTime,
                alcoholAttitude,
                smokingAttitude,
                personalityType,
                cleanHabits
                )
        )
    }

}