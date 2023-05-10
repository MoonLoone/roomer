package com.example.roomer.data.room

import androidx.room.TypeConverter
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.login_sign_up.InterestModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RoomerConverters {
    @TypeConverter
    fun fromInterests(interests: List<InterestModel>?) = interests?.let { Gson().toJson(it) }

    @TypeConverter
    fun toInterests(interestsJson: String?): List<InterestModel>? {
        val myType = object : TypeToken<List<InterestModel>>() {}.type
        return interestsJson?.let { Gson().fromJson(it, myType) }
    }

    @TypeConverter
    fun fromPhotos(photos: List<Room.Photo>?) = photos?.let { Gson().toJson(it) }

    @TypeConverter
    fun toPhotos(photosJson: String?): List<Room.Photo>? {
        val myType = object : TypeToken<List<Room.Photo>>() {}.type
        return photosJson?.let { Gson().fromJson(it, myType) }
    }
}
