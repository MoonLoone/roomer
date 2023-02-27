package com.example.roomer.domain.model

import com.google.gson.annotations.SerializedName

data class RoomsFilterInfo(
    @SerializedName("month_price")
    val monthPrice: Int = 0,
    @SerializedName("host")
    val host: UsersFilterInfo? = null,
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
    val location:String = "Ordinary location",
    @SerializedName("title")
    val title:String = "Ordinary location",
)