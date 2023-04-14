package com.example.roomer.utils.converters

fun convertTimeDateFromBackend(rawData: String): String {
    return rawData.substring(5, rawData.indexOf('T'))
}
