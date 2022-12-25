package com.example.roomer.models

data class SearchUserResult(
    val name:String,
    val location: String,
    val status: String,
    val rate: String,
    val avatarPath: String = "",
)