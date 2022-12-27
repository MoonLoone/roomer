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
    fun loadInterests(delay: Long) : Flow<Resource<List<InterestModel>>> = flow {
        try {
            emit(Resource.Loading())
            delay(delay)

            val process = repository.getInterests()

            coroutineScope {
                emit(Resource.Success(process))
            }

        } catch (e: IOException) {
            emit(Resource.Internet(ConstUseCase.internetErrorMessage))
        }
    }
}
