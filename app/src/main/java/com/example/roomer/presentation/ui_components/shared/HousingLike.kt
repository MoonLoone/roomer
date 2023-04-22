package com.example.roomer.presentation.ui_components.shared

import com.example.roomer.data.repository.roomer_repository.RoomerRepository

class HousingLike (val roomerRepository: RoomerRepository) {

    suspend fun likeHousing(housingId: Int, userId: Int){
        roomerRepository.likeHousing(housingId,userId)
    }

    suspend fun dislikeHousing(housingId: Int, userId: Int){
        roomerRepository.dislikeHousing(housingId,userId)
    }

}