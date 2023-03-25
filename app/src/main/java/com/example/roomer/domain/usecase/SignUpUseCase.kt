package com.example.roomer.domain.usecase

import android.graphics.Bitmap
import com.example.roomer.data.repository.RoomerRepositoryInterface
import com.example.roomer.domain.model.signup.interests.InterestModel
import com.example.roomer.utils.ConstUseCase
import com.example.roomer.utils.Resource
import java.io.IOException
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SignUpUseCase(
    private val repository: RoomerRepositoryInterface
) {
    fun loadInterests(): Flow<Resource<List<InterestModel>>> = flow {
        try {
            emit(Resource.Loading())

            val process = repository.getInterests()

            coroutineScope {
                emit(Resource.Success(process))
            }
        } catch (e: IOException) {
            emit(Resource.Internet(ConstUseCase.internetErrorMessage))
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
        interests: List<InterestModel>
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
                interests
            )

            if (processAvatar.isSuccessful && processData.isSuccessful) {
                coroutineScope {
                    emit(Resource.Success(processData.body()!!.id))
                }
            } else {
                emit(Resource.Error.GeneralError(message = "An error occurred"))
            }
        } catch (e: IOException) {
            emit(Resource.Internet(ConstUseCase.internetErrorMessage))
        }
    }
}
