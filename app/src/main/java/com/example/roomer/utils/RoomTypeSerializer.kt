package com.example.roomer.utils

import com.example.roomer.domain.model.entities.Room
import com.google.gson.Gson
import com.ramcosta.composedestinations.navargs.DestinationsNavTypeSerializer
import com.ramcosta.composedestinations.navargs.NavTypeSerializer

@NavTypeSerializer
class RoomTypeSerializer : DestinationsNavTypeSerializer<Room> {
    override fun fromRouteString(routeStr: String): Room {
        return Gson().fromJson(routeStr, Room::class.java)
    }

    override fun toRouteString(value: Room): String {
        return Gson().toJson(value)
    }
}
