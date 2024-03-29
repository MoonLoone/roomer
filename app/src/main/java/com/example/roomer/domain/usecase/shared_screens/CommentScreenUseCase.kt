package com.example.roomer.domain.usecase.shared_screens

import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.domain.model.comment.UserReview
import com.example.roomer.utils.Constants
import com.example.roomer.utils.Resource
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CommentScreenUseCase(
    private val repository: RoomerRepositoryInterface
) {
    fun getReviews(
        token: String,
        receiverId: Int
    ): Flow<Resource<List<UserReview>>> = flow {
        try {
            emit(Resource.Loading())

            val processData = repository.getReviews(
                token,
                receiverId
            )

            if (processData.isSuccessful) {
                emit(Resource.Success(processData.body()!!))
            } else {
                emit(Resource.Error.GeneralError(message = "An error occurred"))
            }
        } catch (e: IOException) {
            emit(Resource.Internet(Constants.UseCase.internetErrorMessage))
        }
    }
}
