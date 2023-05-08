package com.example.roomer.domain.usecase.navbar_screens

import androidx.compose.runtime.MutableState
import com.example.roomer.data.repository.model.RecommendedMateModel
import com.example.roomer.data.repository.model.RecommendedRoomModel
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.data.room.entities.HistoryItem
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.User
import com.example.roomer.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow

class HomeScreenUseCase(
    private val roomerRepository: RoomerRepositoryInterface
) {

    suspend fun getCurrentUserInfo(user: MutableState<User>): Resource<String> {
        user.value = roomerRepository.getLocalCurrentUser()
        return if (user.value != User()) Resource.Success() else Resource.Error.GeneralError(
            USER_ERROR
        )
    }

    suspend fun getRecommendedRooms(
        rooms: MutableStateFlow<List<Room>>,
        currentUser: User
    ): Resource<String> {
        val response = roomerRepository.getRecommendedRooms(
            RecommendedRoomModel(
                location = currentUser.city,
            )
        )
        return if (response.isSuccessful) {
            rooms.value = response.body() ?: emptyList()
            Resource.Success()
        } else {
            Resource.Error.GeneralError(response.errorBody()?.string() ?: "")
        }
    }

    suspend fun getRecommendedMates(
        mates: MutableStateFlow<List<User>>,
        currentUser: User
    ): Resource<String> {
        val response = roomerRepository.getRecommendedMates(
            RecommendedMateModel(
                location = currentUser.city,
                sleepTime = currentUser.sleepTime,
                interests = currentUser.interests?.associate { it.id.toString() to it.interest }
                    ?: emptyMap(),
            )
        )
        return if (response.isSuccessful) {
            mates.value = response.body() ?: emptyList()
            Resource.Success()
        } else {
            Resource.Error.GeneralError(response.errorBody()?.string() ?: "")
        }
    }

    suspend fun getRecently(history: MutableStateFlow<List<HistoryItem>>): Resource<String> {
        history.value = roomerRepository.getHistory()
        return if (history.value.isEmpty()) Resource.Success() else Resource.Error.GeneralError(
            HISTORY_EMPTY
        )
    }

    private companion object {
        const val USER_ERROR = "User not found"
        const val HISTORY_EMPTY = "History is empty"
    }

}