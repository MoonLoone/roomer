package com.example.roomer.utils

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation


@Composable
fun NavbarHostContainer(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = NavbarItem.Home.name,
    ) {
        NavbarItem.values().forEach {
            val navbarItem = it
            navbarItem.navHostController = navController
            composable(route = it.name) { navbarItem.composeViewFunction.invoke() }
        }
        profileGraph(navController)
        chatGraph(navController)
    }
}

fun NavGraphBuilder.profileGraph(navController: NavHostController) {
    navigation(
        startDestination = NavbarItem.Profile.destination, route = NavbarItem.Profile.name
    ) {
        composable(NavbarItem.Profile.destination){ NavbarItem.Profile.composeViewFunction.invoke() }
        composable(Screens.Account.name) { Screens.Account.composeViewFunction.invoke() }
        composable(Screens.Location.name) { Screens.Location.composeViewFunction.invoke() }
        composable(Screens.Logout.name) { Screens.Logout.composeViewFunction.invoke() }
        composable(Screens.Rating.name) { Screens.Rating.composeViewFunction.invoke() }
        composable(Screens.Settings.name) { Screens.Settings.composeViewFunction.invoke() }
    }
}

fun NavGraphBuilder.chatGraph(navController: NavHostController) {
    navigation(
        startDestination = NavbarItem.Chats.destination, route = NavbarItem.Chats.name
    ) {
        composable(NavbarItem.Chats.destination){ NavbarItem.Chats.composeViewFunction.invoke() }
        composable(Screens.Chat.name) { Screens.Chat.composeViewFunction.invoke() }
    }
}