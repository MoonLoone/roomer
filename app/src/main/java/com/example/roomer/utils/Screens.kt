package com.example.roomer.utils

import androidx.compose.runtime.Composable
import com.example.roomer.presentation.ui_components.AccountScreen
import com.example.roomer.presentation.ui_components.MessageScreen

enum class Screens(val composeViewFunction: @Composable () -> Unit, val parentName: String) {
    Account(
        { AccountScreen() },
        NavbarItem.Profile.name,
    ),
    Location(
        {  },
        NavbarItem.Profile.name,
    ),
    Rating(
        {  },
        NavbarItem.Profile.name,
    ),
    Settings(
        {  },
        NavbarItem.Profile.name,
    ),
    Logout(
        {  },
        NavbarItem.Profile.name,
    ),
    Chat(
        { MessageScreen() },
        NavbarItem.Chats.name,
    )
}