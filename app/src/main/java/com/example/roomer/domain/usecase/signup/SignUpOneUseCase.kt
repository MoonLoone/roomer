package com.example.roomer.domain.usecase.signup

import com.example.roomer.data.repository.RoomerRepositoryInterface
import com.example.roomer.utils.ConstUseCase
import com.example.roomer.utils.Resource
import java.io.IOException
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SignUpOneUseCase(
    private val repository: RoomerRepositoryInterface
) {

    operator fun invoke(
        token: String,
        firstName: String,
        lastName: String,
        sex: String,
        birthDate: String
    ): Flow<Resource<String>> = flow {

        try {

            emit(Resource.Loading())
            val process = repository.putSignUpDataOne(token, firstName, lastName, sex, birthDate)

            if (process.isSuccessful) {
                coroutineScope {
                    emit(Resource.Success())
                }
            } else {
                val errMsg = process.errorBody()!!.string()
                emit(Resource.Error.GeneralError(message = errMsg))
            }
        } catch (e: IOException) {
            emit(Resource.Internet(ConstUseCase.internetErrorMessage))
        }
    }
}
