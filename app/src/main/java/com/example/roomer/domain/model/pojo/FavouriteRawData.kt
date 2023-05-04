package com.example.roomer.domain.model.pojo

import com.google.gson.annotations.SerializedName

data class FavouriteRawData(
    @SerializedName("results")
    val results: List<Favourite>? = null
) : RawData()
