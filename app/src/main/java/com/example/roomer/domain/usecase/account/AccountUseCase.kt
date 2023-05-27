package com.example.roomer.domain.usecase.account

import com.example.roomer.data.repository.auth_repository.AuthRepositoryInterface
import com.example.roomer.domain.model.entities.User
import com.example.roomer.domain.model.login_sign_up.InterestModel
import com.example.roomer.utils.Constants
import com.example.roomer.utils.Resource
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AccountUseCase(
    private val repository: AuthRepositoryInterface
) {
    fun putProfileData(
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
    ): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading())

            val processData = repository.putSignUpData(
                token,
                firstName,
                lastName,
                sex,
                birthDate,
                aboutMe,
                employment,
                sleepTime,
                alcoholAttitude,
                smokingAttitude,
                personalityType,
                cleanHabits,
                interests,
                city
            )

            if (processData.isSuccessful) {
                emit(Resource.Success(processData.body()))
            } else {
                emit(Resource.Error.GeneralError(message = Constants.UseCase.generalError))
            }
        } catch (e: IOException) {
            emit(Resource.Internet(Constants.UseCase.internetErrorMessage))
        }
    }
}
