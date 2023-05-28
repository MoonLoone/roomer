package com.example.roomer.domain.model.pojo

import com.google.gson.annotations.SerializedName

open class RawData(
    @SerializedName("count")
    val count: Int = 0,
    @SerializedName("next")
    val next: String? = null,
    @SerializedName("previous")
    val previous: String? = null,
    @SerializedName("page")
    val page: Int = 0,
    @SerializedName("lastPage")
    val lastPage: Int = 0
)
