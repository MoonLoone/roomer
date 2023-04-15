package com.example.roomer.domain.model.entities

import com.example.roomer.R
import java.util.*

data class RecommendedNotification(
    val text: String = "",
    val iconId: Int = R.drawable.homein,
    val time: Long = Date().time,
)
