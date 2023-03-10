package com.example.roomer

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.roomer.presentation.screens.NavGraphs
import com.example.roomer.presentation.ui_components.Navbar
import com.example.roomer.utils.NavbarItem
import com.ramcosta.composedestinations.DestinationsNavHost

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            Scaffold(bottomBar = { Navbar(navController = navController) }) {
                val paddingValues = it
                DestinationsNavHost(navGraph = NavGraphs.root, navController = navController)
            }
        }
    }
}
