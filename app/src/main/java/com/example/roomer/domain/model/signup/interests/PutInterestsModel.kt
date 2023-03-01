package com.example.roomer.domain.model.signup.interests

import com.google.gson.annotations.SerializedName

data class PutInterestsModel(
    @SerializedName("interests") val interests: List<InterestModel>
)
