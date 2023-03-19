package com.example.roomer.domain.usecase

import com.example.roomer.data.repository.AuthRepositoryInterface
import com.example.roomer.domain.model.signup.interests.InterestModel
import com.example.roomer.utils.ConstUseCase
import com.example.roomer.utils.Resource
import java.io.IOException
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InterestsUseCase(
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
            emit(Resource.Internet(ConstUseCase.internetErrorMessage))
        }
    }
    fun putInterests(
        token: String,
        interests: List<InterestModel>
    ): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())

            val process = repository.putInterests(token, interests)

            if (process.isSuccessful) {
                coroutineScope {
                    emit(Resource.Success(process.body()!!.id))
                }
            } else {
                emit(Resource.Error.GeneralError(message = "An error occurred"))
            }
        } catch (e: IOException) {
            emit(Resource.Internet(ConstUseCase.internetErrorMessage))
        }
    }
}
