package com.example.roomer.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.roomer.R
import com.example.roomer.ui_components.*

enum class NavbarItem(
    val iconUnSelected: Int,
    val iconSelected: Int,
    val description: String,
    val composeViewFunction: @Composable () -> Unit,
    var navHostController: NavHostController? = null,
    val destination:String = "",
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
        { ChatsScreen()},
        destination = "Chats screen",
    ),
    Profile(
        R.drawable.profun,
        R.drawable.profin,
        "Profile",
        { ProfileScreen() },
        destination = "Profile screen",
    )
}
