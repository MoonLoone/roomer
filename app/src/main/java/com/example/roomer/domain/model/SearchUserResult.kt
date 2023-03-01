package com.example.roomer.domain.model

data class SearchUserResult(
    val name: String,
    val location: String = "",
    val status: String = "Occasionally",
    val rate: String,
    val avatarPath: String = "",
)
