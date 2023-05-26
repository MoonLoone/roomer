package com.example.roomer.domain.usecase.profile_screen

import com.example.roomer.data.repository.auth_repository.AuthRepositoryInterface
import com.example.roomer.utils.Constants
import com.example.roomer.utils.Resource
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProfileScreenUseCase(private val repository: AuthRepositoryInterface) {

    suspend fun logout(
        token: String
    ): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())

            val response = repository.logout(token)

            if (response.isSuccessful) {
                emit(Resource.Success())
            } else {
                emit(Resource.Error.GeneralError(message = Constants.UseCase.generalError))
            }
        } catch (e: IOException) {
            emit(Resource.Internet(Constants.UseCase.internetErrorMessage))
        }
    }
}
