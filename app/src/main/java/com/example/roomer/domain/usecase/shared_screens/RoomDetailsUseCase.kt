package com.example.roomer.domain.usecase.shared_screens

import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.domain.model.comment.Comment
import com.example.roomer.utils.Constants
import com.example.roomer.utils.Resource
import kotlinx.coroutines.flow.flow
import java.io.IOException

class RoomDetailsUseCase(private val roomerRepositoryInterface: RoomerRepositoryInterface) :
    FavouriteUseCase(roomerRepositoryInterface) {

    fun checkIsFavourite(userId: Int, housingId: Int, token: String) = flow<Resource<String>> {
        try {
            emit(Resource.Loading())
            val processData = roomerRepositoryInterface.isRoomInFavourites(userId, housingId, token)
            if (processData.isSuccessful) {
                emit(Resource.Success())
            } else {
                emit(Resource.Error.GeneralError(message = "An error occurred"))
            }
        } catch (e: IOException) {
            emit(Resource.Internet(Constants.UseCase.internetErrorMessage))
        }
    }

}