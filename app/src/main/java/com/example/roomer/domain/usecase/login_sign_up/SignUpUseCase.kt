package com.example.roomer.domain.usecase.login_sign_up

import android.graphics.Bitmap
import com.example.roomer.data.repository.auth_repository.AuthRepositoryInterface
import com.example.roomer.domain.model.login_sign_up.InterestModel
import com.example.roomer.utils.Constants
import com.example.roomer.utils.Resource
import java.io.IOException
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SignUpUseCase(
    private val repository: AuthRepositoryInterface
) {
    fun loadInterests(): Flow<Resource<List<InterestModel>>> = flow {
        try {
            emit(Resource.Loading())

            val process = repository.getInterests()

            coroutineScope {
                emit(Resource.Success(process))
            }
        } catch (e: IOException) {
            emit(Resource.Internet(Constants.UseCase.internetErrorMessage))
        }
    }

    fun putSignUpData(
        token: String,
        firstName: String,
        lastName: String,
        sex: String,
        birthDate: String,
        avatar: Bitmap,
        aboutMe: String,
        employment: String,
        sleepTime: String,
        alcoholAttitude: String,
        smokingAttitude: String,
        personalityType: String,
        cleanHabits: String,
        interests: List<InterestModel>,
        city: String
    ): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())

            val processAvatar = repository.putSignUpAvatar(token, avatar)

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

            if (processAvatar.isSuccessful && processData.isSuccessful) {
                coroutineScope {
                    emit(Resource.Success(processData.body()!!.userId.toString()))
                }
            } else {
                emit(Resource.Error.GeneralError(message = "An error occurred"))
            }
        } catch (e: IOException) {
            emit(Resource.Internet(Constants.UseCase.internetErrorMessage))
        }
    }
}
