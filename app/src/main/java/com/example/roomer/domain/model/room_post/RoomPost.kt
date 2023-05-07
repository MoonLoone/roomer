package com.example.roomer.domain.model.room_post

import com.google.gson.annotations.SerializedName

data class RoomPost(
    @SerializedName("month_price")
    val monthPrice: String,
    @SerializedName("host")
    val host: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("bathrooms_count")
    val bathroomsCount: String,
    @SerializedName("bedrooms_count")
    val bedroomsCount: String,
    @SerializedName("housing_type")
    val housingType: String,
    @SerializedName("sharing_type")
    val sharingType: String,
    val location: String = "Ordinary location",
    val title: String = "Ordinary location"
)
