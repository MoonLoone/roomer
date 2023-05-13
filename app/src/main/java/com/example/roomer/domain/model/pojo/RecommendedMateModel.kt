package com.example.roomer.domain.model.pojo

data class RecommendedMateModel(
    val sex: String? = null,
    val location: String? = null,
    val ageFrom: String? = null,
    val ageTo: String? = null,
    val employment: String? = null,
    val alcoholAttitude: String? = null,
    val smokingAttitude: String? = null,
    val sleepTime: String? = null,
    val personalityType: String? = null,
    val cleanHabits: String? = null,
    val interests: Map<String, String> = emptyMap()
)
