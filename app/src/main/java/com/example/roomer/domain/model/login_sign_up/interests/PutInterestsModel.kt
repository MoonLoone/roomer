package com.example.roomer.domain.model.login_sign_up.interests

import com.google.gson.annotations.SerializedName

data class PutInterestsModel(
    @SerializedName("interests") val interests: List<InterestModel>,
)
