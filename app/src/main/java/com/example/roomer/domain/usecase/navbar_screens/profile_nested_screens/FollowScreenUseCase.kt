package com.example.roomer.domain.usecase.navbar_screens.profile_nested_screens

import androidx.compose.runtime.MutableState
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.domain.model.entities.Follow
import com.example.roomer.domain.model.entities.User
import com.example.roomer.domain.usecase.shared_screens.FollowUseCase
import com.example.roomer.utils.Constants
import com.example.roomer.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FollowScreenUseCase(private val roomerRepositoryInterface: RoomerRepositoryInterface) :
    FollowUseCase(roomerRepositoryInterface) {

    suspend fun getFollows(
        currentUserId: Int,
        token: String,
        follows: MutableState<List<Follow>>
    ): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        follows.value =
            roomerRepositoryInterface.getFollows(currentUserId, token).body() ?: emptyList()
        if (follows.value.isNotEmpty()) {
            emit(Resource.Success())
        } else {
            emit(Resource.Error.GeneralError(Constants.Follows.ERROR_EMPTY_LIST))
        }
    }

    suspend fun getCurrentUser(currentUser: MutableState<User>): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        currentUser.value = roomerRepositoryInterface.getLocalCurrentUser()
        if (currentUser.value != User()) {
            emit(Resource.Success())
        } else {
            emit(Resource.Error.GeneralError(Constants.Follows.ERROR_UNAUTHORIZED))
        }
    }
}
