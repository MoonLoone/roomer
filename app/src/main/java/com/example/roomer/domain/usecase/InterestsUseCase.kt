package com.example.roomer.domain.usecase

import com.example.roomer.data.repository.RoomerRepository
import com.example.roomer.domain.model.interests.InterestModel
import com.example.roomer.utils.ConstUseCase
import com.example.roomer.utils.Resource
import kotlinx.coroutines.Delay
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import java.io.IOException

class InterestsUseCase(
    private val repository: RoomerRepository
) {
    fun loadInterests() : Flow<Resource<List<InterestModel>>> = flow {
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
    ) : Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())

            val process = repository.putInterests(token, interests)

            if (process.isSuccessful) {
                coroutineScope {
                    emit(Resource.Success(process.body()!!.id))
                }
            }
            else {
                emit(Resource.Error.GeneralError(message = "An error occurred"))
            }

        } catch (e: IOException) {
            emit(Resource.Internet(ConstUseCase.internetErrorMessage))
        }
    }
}
