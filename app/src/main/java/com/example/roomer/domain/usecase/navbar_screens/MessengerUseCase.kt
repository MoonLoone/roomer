package com.example.roomer.domain.usecase.navbar_screens

import com.example.roomer.data.repository.roomer_repository.RoomerRepository
import com.example.roomer.domain.model.entities.Message
import com.example.roomer.utils.Constants
import com.example.roomer.utils.Resource
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class MessengerUseCase(
    private val roomerRepository: RoomerRepository,
) {

    fun loadChats(userId: Int): Flow<Resource<List<Message>>> = flow {
        try {
            emit(Resource.Loading())

            val process = roomerRepository.getChats(userId)

            if (process.isSuccessful) {
                coroutineScope {
                    emit(Resource.Success(process.body()))
                }
            } else {
                emit(Resource.Error.GeneralError(message = "Error with chat loads"))
            }
        } catch (e: IOException) {
            emit(Resource.Internet(Constants.UseCase.internetErrorMessage))
        }
    }
}
