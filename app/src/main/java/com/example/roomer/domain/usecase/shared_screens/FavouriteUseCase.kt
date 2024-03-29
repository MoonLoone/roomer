package com.example.roomer.domain.usecase.shared_screens

import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.utils.Constants
import com.example.roomer.utils.Resource
import java.io.IOException
import kotlinx.coroutines.flow.flow

abstract class FavouriteUseCase(private val roomerRepositoryInterface: RoomerRepositoryInterface) {

    fun addToFavourites(room: Room) = flow<Resource<String>> {
        try {
            emit(Resource.Loading())
            val process = roomerRepositoryInterface.addToFavourites(room.id)
            if (process.isSuccessful) {
                emit(Resource.Success())
            } else {
                emit(Resource.Error.GeneralError(Constants.Favourites.ADD_FAVOURITE_ERROR))
            }
        } catch (e: IOException) {
            emit(Resource.Internet(Constants.UseCase.internetErrorMessage))
        }
    }

    fun deleteFromFavourites(room: Room) = flow<Resource<String>> {
        emit(Resource.Loading())
        val process = roomerRepositoryInterface.deleteFromFavourites(room.id)
        try {
            if (process.isSuccessful) {
                emit(Resource.Success())
            } else {
                emit(Resource.Error.GeneralError(Constants.Favourites.ADD_FAVOURITE_ERROR))
            }
        } catch (e: IOException) {
            emit(Resource.Internet(process.message()))
        }
    }
}
