package com.example.roomer.domain.usecase.search

import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.User
import com.example.roomer.utils.Constants
import com.example.roomer.utils.Resource
import java.io.IOException
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchUseCase(
    private val repository: RoomerRepositoryInterface
) {
    fun loadRooms(
        monthPriceFrom: String?,
        monthPriceTo: String?,
        location: String?,
        bedroomsCount: String?,
        bathroomsCount: String?,
        housingType: String?
    ): Flow<Resource<List<Room>>> = flow {
        try {
            emit(Resource.Loading())

            val process = repository.getFilterRooms(
                monthPriceFrom,
                monthPriceTo,
                location,
                bedroomsCount,
                bathroomsCount,
                housingType
            )

            if (process.isSuccessful) {
                emit(Resource.Success(process.body()))
            } else {
                val errMsg = process.errorBody()?.string()
                emit(Resource.Error.GeneralError(errMsg!!))
            }
        } catch (e: IOException) {
            emit(Resource.Internet(Constants.UseCase.internetErrorMessage))
        }
    }

    fun loadRoommates(
        sex: String?,
        location: String?,
        ageFrom: String?,
        ageTo: String?,
        employment: String?,
        alcoholAttitude: String?,
        smokingAttitude: String?,
        sleepTime: String?,
        personalityType: String?,
        cleanHabits: String?,
        interests: List<String>?
    ): Flow<Resource<List<User>>> = flow {
        try {
            emit(Resource.Loading())

            val interestsMap = interests?.associate { "interests" to it } ?: mapOf()
            val process = repository.getFilterRoommates(
                sex,
                location,
                ageFrom,
                ageTo,
                employment,
                alcoholAttitude,
                smokingAttitude,
                sleepTime,
                personalityType,
                cleanHabits,
                interestsMap
            )

            if (process.isSuccessful) {
                coroutineScope {
                    emit(Resource.Success(process.body()))
                }
            } else {
                val errMsg = process.errorBody()?.string()
                emit(Resource.Error.GeneralError(errMsg!!))
            }
        } catch (e: IOException) {
            emit(Resource.Internet(Constants.UseCase.internetErrorMessage))
        }
    }
}
