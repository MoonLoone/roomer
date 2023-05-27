package com.example.roomer.domain.usecase.shared_screens

import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.utils.Constants
import com.example.roomer.utils.Resource
import java.io.IOException
import kotlinx.coroutines.flow.flow

open class FollowUseCase(private val roomerRepositoryInterface: RoomerRepositoryInterface) {

    suspend fun follow(currentUserId: Int, followUserId: Int, token: String) = flow<Resource<String>> {
        try {
            emit(Resource.Loading())
            val process = roomerRepositoryInterface.followToUser(currentUserId, followUserId, token)
            if (process.isSuccessful) {
                emit(Resource.Success())
            } else {
                emit(Resource.Error.GeneralError(process.errorBody().toString()))
            }
        } catch (e: IOException) {
            emit(Resource.Internet(Constants.UseCase.internetErrorMessage))
        }
    }

    suspend fun unfollow(currentUserId: Int, followUserId: Int, token: String) =
        flow<Resource<String>> {
            emit(Resource.Loading())
            val process = roomerRepositoryInterface.unFollowUser(currentUserId, followUserId, token)
            if (process.isSuccessful) {
                emit(Resource.Success())
            } else {
                emit(Resource.Error.GeneralError(process.errorBody().toString()))
            }
        }
}
