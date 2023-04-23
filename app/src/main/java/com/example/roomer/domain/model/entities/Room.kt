package com.example.roomer.domain.model.entities

import com.example.roomer.data.room.entities.LocalRoom
import com.google.gson.annotations.SerializedName

data class Room(
    @SerializedName("id")
    override val id: Int,
    @SerializedName("month_price")
    val monthPrice: Int = 0,
    @SerializedName("host")
    val host: User? = null,
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
): BaseEntity {
    data class Photo(
        val photo: String
    )
}

fun Room.toLocalRoom() = LocalRoom(
    id,
    monthPrice,
    host?.userId ?: 0,
    description,
    fileContent,
    bathroomsCount,
    bedroomsCount,
    housingType,
    sharingType,
    location,
    title,
    isLiked
)
