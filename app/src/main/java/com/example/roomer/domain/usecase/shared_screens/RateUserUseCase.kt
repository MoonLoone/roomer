package com.example.roomer.domain.usecase.shared_screens

import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.domain.model.comment.Comment
import com.example.roomer.utils.Constants
import com.example.roomer.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class RateUserUseCase(
    private val repository: RoomerRepositoryInterface
) {
    fun sendReview(
        token: String,
        authorId: Int,
        receiverId: Int,
        score: Int,
        isAnonymous: Boolean,
        comment: String
    ): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())

            val processData = repository.addComment(
                token,
                Comment(authorId, receiverId, score, isAnonymous, comment)
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