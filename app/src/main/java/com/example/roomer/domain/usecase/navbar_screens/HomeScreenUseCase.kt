package com.example.roomer.domain.usecase.navbar_screens

import androidx.compose.runtime.MutableState
import com.example.roomer.data.repository.model.RecommendedMateModel
import com.example.roomer.data.repository.model.RecommendedRoomModel
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.data.room.entities.HistoryItem
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.User
import com.example.roomer.utils.Resource
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class HomeScreenUseCase(
    private val roomerRepository: RoomerRepositoryInterface
) {

    suspend fun getCurrentUserInfo(user: MutableState<User>): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            user.value = roomerRepository.getLocalCurrentUser()
            if (user.value != User()) {
                emit(Resource.Success())
            } else {
                emit(
                    Resource.Error.GeneralError(
                        USER_ERROR
                    )
                )
            }
        } catch (e: IOException) {
            emit(Resource.Internet(USER_ERROR))
        }
    }

    suspend fun getRecommendedRooms(
        rooms: MutableStateFlow<List<Room>>,
        currentUser: User
    ): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val response = roomerRepository.getRecommendedRooms(
                RecommendedRoomModel(
                    location = currentUser.city
                )
            )
            emit(
                if (response.isSuccessful) {
                    rooms.value = response.body() ?: emptyList()
                    Resource.Success()
                } else {
                    Resource.Error.GeneralError(response.errorBody()?.string() ?: "")
                }
            )
        } catch (e: IOException) {
            emit(Resource.Internet(ROOM_ERROR))
        }
    }

    suspend fun getRecommendedRoommates(
        mates: MutableStateFlow<List<User>>,
        currentUser: User
    ): Flow<Resource<String>> = flow {
        try {
            val response = roomerRepository.getRecommendedMates(
                RecommendedMateModel(
                    location = currentUser.city,
                    sleepTime = currentUser.sleepTime,
                    interests = currentUser.interests?.associate { it.id.toString() to it.interest }
                        ?: emptyMap()
                )
            )
            emit(
                if (response.isSuccessful) {
                    mates.value = response.body() ?: emptyList()
                    Resource.Success()
                } else {
                    Resource.Error.GeneralError(response.errorBody()?.string() ?: "")
                }
            )
        } catch (e: IOException) {
            emit(Resource.Internet(MATES_ERROR))
        }
    }

    suspend fun getRecently(
        history: MutableStateFlow<List<HistoryItem>>
    ): Flow<Resource<String>> = flow {
        history.value = roomerRepository.getHistory()
        emit(
            if (history.value.isNotEmpty()) {
                Resource.Success()
            } else {
                Resource.Error.GeneralError(
                    HISTORY_EMPTY
                )
            }
        )
    }

    private companion object {
        const val USER_ERROR = "User not found"
        const val ROOM_ERROR = "No recommended rooms"
        const val MATES_ERROR = "No recommended mates"
        const val HISTORY_EMPTY = "History is empty"
    }
}
