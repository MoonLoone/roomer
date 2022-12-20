package com.example.roomer.models

data class RecommendedRoom(
    val id: Int,
    val name: String,
    val location: String,
    val roomImagePath: String,
    val isLiked:Boolean,
)