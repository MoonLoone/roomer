package com.example.roomer.domain.model.entities

import com.google.gson.annotations.SerializedName

data class Room(
    val id: Int = 0,
    @SerializedName("month_price")
    val monthPrice: Int = 0,
    @SerializedName("host")
    val host: User = User(),
    @SerializedName("description")
    val description: String = "",
    @SerializedName("file_content")
    val fileContent: List<Photo>? = emptyList(),
    @SerializedName("bathrooms_count")
    val bathroomsCount: Int = 0,
    @SerializedName("bedrooms_count")
    val bedroomsCount: Int = 0,
    @SerializedName("housing_type")
    val housingType: String = "",
    @SerializedName("sharing_type")
    val sharingType: String = "",
    val location: String = "Ordinary location",
    val title: String = "Ordinary location",
    var isLiked: Boolean = false
) {
    data class Photo(
        val photo: String
    )
}
