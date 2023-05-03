package com.example.roomer.presentation.screens.navbar_screens.post_screen.add_housing_screen

data class AddHousingState(
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val isLoading: Boolean = false,
    val isInternetProblem: Boolean = false
)
