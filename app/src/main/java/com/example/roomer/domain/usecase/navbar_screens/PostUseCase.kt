package com.example.roomer.domain.usecase.navbar_screens

import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.utils.Constants
import com.example.roomer.utils.Resource
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class PostUseCase(
    private val repository: RoomerRepositoryInterface
) {
    fun getCurrentUserRoomData(
        token: String,
        host: Int
    ): Flow<Resource<List<Room>>> = flow {
        try {
            emit(Resource.Loading())

            val processData = repository.getCurrentUserRooms(
                token,
                host
            )

            if (processData.isSuccessful) {
                coroutineScope {
                    emit(Resource.Success(processData.body()!!))
                }
            } else {
                emit(Resource.Error.GeneralError(message = "An error occurred"))
            }
        } catch (e: IOException) {
            emit(Resource.Internet(Constants.UseCase.internetErrorMessage))
        }
    }
}
