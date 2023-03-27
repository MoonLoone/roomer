package com.example.roomer.domain.model.entities

import com.google.gson.annotations.SerializedName

data class Room(
    @SerializedName("month_price")
    val monthPrice: Int = 0,
    @SerializedName("host")
    val host: Int = 0,
    @SerializedName("description")
    val description: String = "",
    @SerializedName("photo")
    val photo: String = "",
    @SerializedName("bathrooms_count")
    val bathroomsCount: Int = 0,
    @SerializedName("bedrooms_count")
    val bedroomsCount: Int = 0,
    @SerializedName("housing_type")
    val housingType: String = "",
    @SerializedName("sharing_type")
    val sharingType: String = "",
    @SerializedName("location")
    val location: String = "Ordinary location",
    @SerializedName("title")
    val title: String = "Ordinary location",
    val isLiked: Boolean = false,
)
