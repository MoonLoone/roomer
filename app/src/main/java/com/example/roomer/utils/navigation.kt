package com.example.roomer.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation

/*@Composable
fun NavbarHostContainer(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavbarItem.Home.name,
    ) {
        NavbarItem.values().forEach {
            val navbarItem = it
            navbarItem.navHostController = navController
            composable(route = it.name) { navbarItem.composeViewFunction.invoke() }
        }
    }
}*/
