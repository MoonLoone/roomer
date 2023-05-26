package com.example.roomer.domain.usecase.shared_screens

import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.domain.usecase.shared.FollowUseCase
import com.example.roomer.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserDetailsUseCase(private val roomerRepositoryInterface: RoomerRepositoryInterface) :
    FollowUseCase(roomerRepositoryInterface) {

    suspend fun checkIsFollowed(
        currentUserId: Int,
        userId: Int,
        token: String
    ): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        val process = roomerRepositoryInterface.checkIsFollowed(currentUserId, userId, token)
        if (process.isSuccessful) {
            emit(Resource.Success())
        } else {
            emit(Resource.Error.GeneralError(process.errorBody().toString()))
        }
    }
}
