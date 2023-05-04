package com.example.roomer.utils

object UtilsFunctions {
    fun trimString(text: String, maxLength: Int, truncateText: String = "..."): String {
        if (maxLength >= text.length || maxLength < truncateText.length) return text
        return text.substring(startIndex = 0, endIndex = maxLength - 1)
            .dropLast(truncateText.length)
            .dropLastWhile { Character.isWhitespace(it) || it == '.' }
            .plus(truncateText)
    }
}