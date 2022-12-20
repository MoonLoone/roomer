package com.example.roomer.models

data class RecommendedRoommate(
    val id: Int,
    val name:String,
    val rating: Double,
    val imagePath: String = "",
)
