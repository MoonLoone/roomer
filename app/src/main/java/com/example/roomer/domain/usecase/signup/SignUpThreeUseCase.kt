package com.example.roomer.domain.usecase.signup

import com.example.roomer.data.repository.RoomerRepository
import com.example.roomer.utils.ConstUseCase
import com.example.roomer.utils.Resource
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class SignUpThreeUseCase (
    private val repository: RoomerRepository
) {

    operator fun invoke(
        token: String,
        sleepTime: String,
        alcoholAttitude: String,
        smokingAttitude: String,
        personalityType: String,
        cleanHabits: String
    ): Flow<Resource<String>> = flow {

        try {
            emit(Resource.Loading())
            val process = repository.putSignUpDataThree(
                token,
                sleepTime,
                alcoholAttitude,
                smokingAttitude,
                personalityType,
                cleanHabits
            )

            if (process.isSuccessful) {
                coroutineScope {
                    emit(Resource.Success())
                }
            }
            else {
                val errMsg = process.errorBody()!!.string()
                emit(Resource.Error.GeneralError(message = errMsg))
            }

        } catch (e: IOException) {
            emit(Resource.Internet(ConstUseCase.internetErrorMessage))
        }
    }
}