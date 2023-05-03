package com.example.roomer.utils.converters

import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

fun convertTimeDateFromBackend(rawData: String): String {
    val separatorIndex = rawData.indexOf('T')
    val date = rawData.substring(5, separatorIndex)
    val time = rawData.substring(separatorIndex, separatorIndex+5)
    return date
}
