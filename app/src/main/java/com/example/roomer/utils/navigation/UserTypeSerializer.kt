package com.example.roomer.utils.navigation

import com.example.roomer.domain.model.entities.User
import com.google.gson.Gson
import com.ramcosta.composedestinations.navargs.DestinationsNavTypeSerializer
import com.ramcosta.composedestinations.navargs.NavTypeSerializer

@NavTypeSerializer
class UserTypeSerializer : DestinationsNavTypeSerializer<User> {
    override fun fromRouteString(routeStr: String): User {
        return Gson().fromJson(routeStr, User::class.java)
    }

    override fun toRouteString(value: User): String {
        return Gson().toJson(value)
    }
}
