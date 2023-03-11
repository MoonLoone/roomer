package com.example.roomer.utils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

object NavbarManagement {

    var navbarState: MutableState<Boolean> = mutableStateOf(false)

    fun showNavbar(){
        navbarState.value = true
    }

    fun hideNavbar(){
        navbarState.value = false
    }

}