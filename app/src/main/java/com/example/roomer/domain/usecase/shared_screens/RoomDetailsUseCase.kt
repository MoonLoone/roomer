package com.example.roomer.domain.usecase.shared_screens

import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface

class RoomDetailsUseCase(roomerRepositoryInterface: RoomerRepositoryInterface) :
    FavouriteUseCase(roomerRepositoryInterface) {
}