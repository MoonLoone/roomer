package com.example.roomer.presentation.screens.entrance.login

data class LoginScreenState(
    var isLoading : Boolean = false,
    var success : Boolean = false,
    var error : String = "",
    var internetProblem : Boolean = false
)
