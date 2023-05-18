package com.example.roomer.presentation.screens.navbar_screens.post_screen.add_housing_screen

import com.example.roomer.utils.ScreenState

data class AddHousingState(
    override var success: Boolean = false,
    override var isLoading: Boolean = false,
    override var internetProblem: Boolean = false,
    override var error: String = "",
    var requestProblem: Boolean = false,
    var monthPriceIsNotInteger: Boolean = false,
    var monthPriceIsNotPositive: Boolean = false,
    var roomImagesIsEmpty: Boolean = false,
    var descriptionIsEmpty: Boolean = false,
    var titleIsEmpty: Boolean = false,
    var noLocationSet: Boolean = true
) : ScreenState
