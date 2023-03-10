package com.example.roomer.utils

import androidx.compose.runtime.Composable
import com.example.roomer.presentation.screens.AccountScreen
import com.example.roomer.presentation.screens.MessageScreen
import com.example.roomer.presentation.screens.SearchRoomResults
import com.example.roomer.presentation.screens.SearchRoomScreen
import com.example.roomer.presentation.screens.SearchRoommateResults
import com.example.roomer.presentation.screens.SearchRoommateScreen
import com.example.roomer.presentation.screens.destinations.AccountScreenDestination
import com.example.roomer.presentation.screens.destinations.MessageScreenDestination
import com.example.roomer.presentation.screens.destinations.SearchRoomResultsDestination
import com.example.roomer.presentation.screens.destinations.SearchRoomScreenDestination
import com.example.roomer.presentation.screens.destinations.SearchRoommateResultsDestination
import com.example.roomer.presentation.screens.destinations.SearchRoommateScreenDestination

enum class Screens(val composeViewFunction: @Composable () -> Unit, val parentName: String) {
    Account(
        { AccountScreenDestination() },
        NavbarItem.Profile.name,
    ),
    Location(
        { },
        NavbarItem.Profile.name,
    ),
    Rating(
        { },
        NavbarItem.Profile.name,
    ),
    Settings(
        { },
        NavbarItem.Profile.name,
    ),
    Logout(
        { },
        NavbarItem.Profile.name,
    ),
    Chat(
        { MessageScreenDestination() },
        NavbarItem.Chats.name,
    ),
    SearchRoom(
        { SearchRoomScreenDestination() },
        NavbarItem.Home.name,
    ),
    SearchRoommate(
        { SearchRoommateScreenDestination() },
        NavbarItem.Home.name,
    ),
    SearchRoommateResults(
        { SearchRoommateResultsDestination() },
        NavbarItem.Home.name,
    ),
    SearchRoomResults(
        { SearchRoomResultsDestination() },
        NavbarItem.Home.name,
    )
}
