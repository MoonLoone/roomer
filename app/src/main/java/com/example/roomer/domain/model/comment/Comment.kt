package com.example.roomer.domain.model.comment

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("author_id")
    val authorId: Int,
    @SerializedName("receiver_id")
    val receiverId: Int,
    @SerializedName("score")
    val score: Int,
    @SerializedName("is_anon")
    val isAnonymous: Boolean,
    @SerializedName("comment")
    val comment: String
)
