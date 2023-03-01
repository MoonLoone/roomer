package com.example.roomer.domain.model

data class RecommendedRoom(
    val id: Int,
    val name: String,
    val location: String,
    val roomImagePath: String,
    val isLiked: Boolean,
)
