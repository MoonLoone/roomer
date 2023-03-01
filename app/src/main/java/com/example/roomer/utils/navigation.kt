package com.example.roomer.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation

@Composable
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
        profileGraph(navController)
        chatGraph(navController)
        homeGraph(navController)
    }
}

fun NavGraphBuilder.profileGraph(navController: NavHostController) {
    navigation(
        startDestination = NavbarItem.Profile.destination,
        route = NavbarItem.Profile.name
    ) {
        composable(NavbarItem.Profile.destination) { NavbarItem.Profile.composeViewFunction.invoke() }
        composable(Screens.Account.name) { Screens.Account.composeViewFunction.invoke() }
        composable(Screens.Location.name) { Screens.Location.composeViewFunction.invoke() }
        composable(Screens.Logout.name) { Screens.Logout.composeViewFunction.invoke() }
        composable(Screens.Rating.name) { Screens.Rating.composeViewFunction.invoke() }
        composable(Screens.Settings.name) { Screens.Settings.composeViewFunction.invoke() }
    }
}

fun NavGraphBuilder.chatGraph(navController: NavHostController) {
    navigation(
        startDestination = NavbarItem.Chats.destination,
        route = NavbarItem.Chats.name
    ) {
        composable(NavbarItem.Chats.destination) { NavbarItem.Chats.composeViewFunction.invoke() }
        composable(Screens.Chat.name) { Screens.Chat.composeViewFunction.invoke() }
    }
}

fun NavGraphBuilder.homeGraph(navController: NavHostController) {
    navigation(
        startDestination = NavbarItem.Home.destination,
        route = NavbarItem.Home.name
    ) {
        composable(NavbarItem.Home.destination) { NavbarItem.Home.composeViewFunction.invoke() }
        composable(Screens.SearchRoom.name) { Screens.SearchRoom.composeViewFunction.invoke() }
        composable(
            Screens.SearchRoomResults.name ) {
            Screens.SearchRoomResults.composeViewFunction.invoke()
        }
        composable(Screens.SearchRoommate.name) { Screens.SearchRoommate.composeViewFunction.invoke() }
        composable(
            Screens.SearchRoommateResults.name) { Screens.SearchRoommateResults.composeViewFunction.invoke() }
    }
}
