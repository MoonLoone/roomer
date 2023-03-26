package com.example.roomer.converters

fun convertTimeDateFromBackend(rawData: String): String {
    return rawData.substring(5, rawData.indexOf('T'))
}
