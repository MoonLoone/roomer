package com.example.roomer.domain.usecase

import com.example.roomer.data.repository.SearchRepositoryInterface
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.utils.Constants
import com.example.roomer.utils.Resource
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class SearchUseCase (private val repository: SearchRepositoryInterface
) {
    fun loadRooms(
        monthPriceFrom: String,
        monthPriceTo: String,
        bedroomsCount: String,
        bathroomsCount: String,
        housingType: String,
    ): Flow<Resource<List<Room>>> = flow {
        try {
            emit(Resource.Loading())

            val process = repository.getFilterRooms(monthPriceFrom,
                monthPriceTo, bedroomsCount, bathroomsCount, housingType
            )

            coroutineScope {
                emit(Resource.Success(process.body()))
            }
        } catch (e: IOException) {
            emit(Resource.Internet(Constants.UseCase.internetErrorMessage))
        }
    }
}
