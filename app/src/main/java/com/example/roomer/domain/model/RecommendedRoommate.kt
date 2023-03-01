package com.example.roomer.domain.model

data class RecommendedRoommate(
    val id: Int,
    val name: String,
    val rating: Double,
    val imagePath: String = "",
)
