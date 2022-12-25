package com.example.roomer.state

data class LoginState(
    var isLoading : Boolean = false,
    var success : Int = -1,
    var error : String = "",
    var internetProblem : Boolean = false
)
