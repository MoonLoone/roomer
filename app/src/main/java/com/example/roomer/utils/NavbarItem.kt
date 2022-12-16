package com.example.roomer.utils

import androidx.compose.runtime.Composable
import com.example.roomer.R
import com.example.roomer.ui_components.*

enum class NavbarItem(
    val iconUnSelected: Int,
    val iconSelected: Int,
    val description: String,
    val composeViewFunction: @Composable () -> Unit
) {
    Home(
        R.drawable.homeun,
        R.drawable.homein,
        "Home",
        { HomeScreen() }
    ),
    Favourite(
        R.drawable.favun,
        R.drawable.favin,
        "Favourite",
        {},
    ),
    Post(
        R.drawable.postun,
        R.drawable.postin,
        "Post",
        {},
    ),
    Chats(
        R.drawable.chatun,
        R.drawable.chatin,
        "Chat",
        {},
    ),
    Profile(
        R.drawable.profun,
        R.drawable.profin,
        "Profile",
        { ProfileScreen() },
    )
}