package com.example.roomer.domain.model.entities

import com.google.gson.annotations.SerializedName

open class BaseEntity(
    @SerializedName("id")
    var id: Int = 0,
    var page: Int = 1,
    var lastPage: Int = 1,
)
