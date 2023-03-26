package com.example.roomer.domain.usecase.login_sign_up

import com.example.roomer.data.repository.RoomerRepositoryInterface
import com.example.roomer.utils.Constants
import com.example.roomer.utils.Resource
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SplashScreenUseCase(
    private val repository: RoomerRepositoryInterface
) {

    operator fun invoke(token: String): Flow<Resource<String>> = flow {

        try {
            emit(Resource.Loading())
            val process = repository.getCurrentUserInfo(token)

            if (process.isSuccessful) emit(Resource.Success())
            else {
                val errMsg = process.errorBody()?.string()
                emit(Resource.Error.GeneralError(errMsg!!))
            }
        } catch (e: IOException) {
            emit(Resource.Internet(Constants.UseCase.internetErrorMessage))
        }
    }
}
