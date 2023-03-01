package com.example.roomer.presentation.screens.entrance

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun MainScreen(id: Int, navigator: DestinationsNavigator) {
    Text(text = "Main Screen")
}