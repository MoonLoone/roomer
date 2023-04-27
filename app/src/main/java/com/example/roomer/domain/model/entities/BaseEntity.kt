package com.example.roomer.domain.model.entities

import androidx.room.Ignore
import com.google.gson.annotations.SerializedName

open class BaseEntity {
    @SerializedName("id")
    var id: Int = 0
}
