package com.example.roomer.utils

object UtilsFunctions {
    fun trimString(text: String, maxLength: Int, truncateText: String = "..."): String {
        if (maxLength >= text.length || maxLength < truncateText.length) return text
        return text.substring(startIndex = 0, endIndex = maxLength - 1)
            .dropLast(truncateText.length)
            .dropLastWhile { Character.isWhitespace(it) || it == '.' }
            .plus(truncateText)
    }

    fun bundleMapItemsByScreenWidth(values: Map<String, String>): List<List<Map.Entry<String, String>>> {
        val bundleList = mutableListOf<MutableList<Map.Entry<String, String>>>()
        val symbolsRowMax = 120
        var symbolsNow = 0
        var oneRowList = mutableListOf<Map.Entry<String, String>>()
        for (item in values) {
            if (symbolsNow + item.value.length <= symbolsRowMax) {
                oneRowList.add(item)
                symbolsNow += item.value.length
            } else {
                bundleList.add(oneRowList)
                oneRowList = mutableListOf()
                symbolsNow = 0
            }
        }
        if (oneRowList.isNotEmpty()) bundleList.add(oneRowList)
        return bundleList
    }
}
