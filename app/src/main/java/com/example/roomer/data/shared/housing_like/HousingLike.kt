package com.example.roomer.data.shared.housing_like

import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.data.room.RoomerDatabase
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.toLocalRoom
import javax.inject.Inject

class HousingLike @Inject constructor(
    private val roomerRepositoryInterface: RoomerRepositoryInterface,
    private val roomerDatabase: RoomerDatabase
) : HousingLikeInterface {

    override suspend fun likeHousing(housing: Room) {
        roomerRepositoryInterface.likeHousing(housing.id)
        roomerDatabase.favourites.save(housing.toLocalRoom())
    }

    override suspend fun dislikeHousing(housing: Room) {
        roomerRepositoryInterface.dislikeHousing(housing.id)
        roomerDatabase.favourites.deleteById(housing.id)
    }
}
