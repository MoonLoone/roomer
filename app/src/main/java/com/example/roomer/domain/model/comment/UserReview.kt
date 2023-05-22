package com.example.roomer.domain.model.comment

import com.example.roomer.domain.model.entities.User
import com.google.gson.annotations.SerializedName

data class UserReview(
    @SerializedName("id")
    val id: String,
    @SerializedName("score")
    val score: Int,
    @SerializedName("author")
    val author: User,
    @SerializedName("receiver")
    val receiver: Int,
    @SerializedName("comment")
    val comment: String,
    @SerializedName("is_anon")
    val isAnonymous: Boolean
)
