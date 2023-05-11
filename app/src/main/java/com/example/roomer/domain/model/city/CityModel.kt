package com.example.roomer.domain.model.city

import com.google.gson.annotations.SerializedName

data class CityModel(
    @SerializedName("id") val id: Int,
    @SerializedName("city") val city: String
)