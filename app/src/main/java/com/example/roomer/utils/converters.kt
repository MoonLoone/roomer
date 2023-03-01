package com.example.roomer.utils

import java.text.SimpleDateFormat
import java.util.Date

fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
    return format.format(date)
}
