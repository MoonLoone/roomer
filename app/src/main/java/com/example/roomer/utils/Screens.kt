package com.example.roomer.utils

import androidx.compose.runtime.Composable
import com.example.roomer.presentation.screens.AccountScreen
import com.example.roomer.presentation.screens.MessageScreen
import com.example.roomer.presentation.screens.SearchRoomResults
import com.example.roomer.presentation.screens.SearchRoomScreen
import com.example.roomer.presentation.screens.SearchRoommateResults
import com.example.roomer.presentation.screens.SearchRoommateScreen

enum class Screens(val composeViewFunction: @Composable () -> Unit, val parentName: String) {
    Account(
        { AccountScreen() },
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
        { MessageScreen() },
        NavbarItem.Chats.name,
    ),
    SearchRoom(
        { SearchRoomScreen() },
        NavbarItem.Home.name,
    ),
    SearchRoommate(
        { SearchRoommateScreen() },
        NavbarItem.Home.name,
    ),
    SearchRoommateResults(
        { SearchRoommateResults() },
        NavbarItem.Home.name,
    ),
    SearchRoomResults(
        { SearchRoomResults() },
        NavbarItem.Home.name,
    )
}
