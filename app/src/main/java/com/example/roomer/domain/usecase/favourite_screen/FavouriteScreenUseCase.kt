package com.example.roomer.domain.usecase.favourite_screen

import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.usecase.shared.FavouriteUseCase
import com.example.roomer.utils.Constants
import com.example.roomer.utils.Resource
import kotlinx.coroutines.flow.flow
import java.io.IOException

class FavouriteScreenUseCase(private val roomerRepositoryInterface: RoomerRepositoryInterface) :
    FavouriteUseCase(roomerRepositoryInterface) {

        fun deleteLocalFavourite(room: Room) = flow<Resource<String>>{
            val process = roomerRepositoryInterface.deleteLocalFavourite(room)
        }

}