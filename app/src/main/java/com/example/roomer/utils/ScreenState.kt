package com.example.roomer.utils

interface ScreenState {
    var success: Boolean
    var isLoading: Boolean
    var error: String
    var internetProblem: Boolean
}
