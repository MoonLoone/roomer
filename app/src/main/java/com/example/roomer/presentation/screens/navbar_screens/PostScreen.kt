package com.example.roomer.presentation.screens.navbar_screens

import androidx.compose.runtime.Composable
import com.example.roomer.utils.NavbarManagement
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun PostScreen(
    navigator: DestinationsNavigator
) {
    NavbarManagement.showNavbar()
}
