package com.example.roomer.domain.usecase.favourite_screen

import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.usecase.shared_screens.FavouriteUseCase

class FavouriteScreenUseCase(private val roomerRepositoryInterface: RoomerRepositoryInterface) :
    FavouriteUseCase(roomerRepositoryInterface) {

    suspend fun deleteLocalFavourite(room: Room) {
        roomerRepositoryInterface.deleteLocalFavourite(room)
    }
}
