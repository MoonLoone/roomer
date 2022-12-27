package com.example.roomer.state

data class LoginScreenState(
    var isLoading : Boolean = false,
    var success : Boolean = false,
    var error : String = "",
    var internetProblem : Boolean = false
)
