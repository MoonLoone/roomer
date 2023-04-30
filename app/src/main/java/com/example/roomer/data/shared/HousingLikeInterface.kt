package com.example.roomer.data.shared

import com.example.roomer.domain.model.entities.Room

interface HousingLikeInterface {

    suspend fun likeHousing(housing: Room)

    suspend fun dislikeHousing(housing: Room)
}
