package com.example.roomer.utils

object UtilsFunctions {
    fun trimString(text: String, maxLength: Int, truncateText: String = "..."): String {
        if (maxLength >= text.length || maxLength < truncateText.length) return text
        return text.substring(startIndex = 0, endIndex = maxLength - 1)
            .dropLast(truncateText.length)
            .dropLastWhile { Character.isWhitespace(it) || it == '.' }
            .plus(truncateText)
    }

    fun bundleMapItemsToFitInOneLine(
        values: Map<String, String>,
        spaceBetweenItemsDp: Int
    ): List<List<Map.Entry<String, String>>> {
        val bundleList = mutableListOf<MutableList<Map.Entry<String, String>>>()
        var symbolsNow = 0
        var oneRowList = mutableListOf<Map.Entry<String, String>>()
        for (item in values) {
            if (symbolsNow + item.value.length - spaceBetweenItemsDp
                <= Constants.MAX_CHARS_AMOUNT_IN_BUTTONS_ROW
            ) {
                oneRowList.add(item)
                symbolsNow += item.value.length + spaceBetweenItemsDp
            } else {
                bundleList.add(oneRowList)
                oneRowList = mutableListOf(item)
                symbolsNow = item.value.length + spaceBetweenItemsDp
            }
        }
        if (oneRowList.isNotEmpty()) bundleList.add(oneRowList)
        return bundleList
    }
}
