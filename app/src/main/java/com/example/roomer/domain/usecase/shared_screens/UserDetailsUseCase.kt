package com.example.roomer.domain.usecase.shared_screens

import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserDetailsUseCase(private val roomerRepositoryInterface: RoomerRepositoryInterface) {

    suspend fun checkIsFollowed(currentUserId: Int, userId: Int): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        val response = roomerRepositoryInterface.checkIsFollowed(currentUserId, userId)
        if (response.isSuccessful) emit(Resource.Success())
        else emit(Resource.Error.GeneralError(response.errorBody().toString()))
    }

}