package com.example.roomer.utils

object UtilsFunctions {
    fun trimString(text: String, maxLength: Int, truncateText: String = "...") =
        text.substring(startIndex = 0, endIndex = maxLength - 1)
            .dropLast(truncateText.length)
            .dropLastWhile { Character.isWhitespace(it) || it == '.' }
            .plus(truncateText)
}
