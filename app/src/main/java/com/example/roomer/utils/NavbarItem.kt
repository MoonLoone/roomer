package com.example.roomer.utils

import com.example.roomer.R
import com.example.roomer.presentation.screens.destinations.ChatsScreenDestination
import com.example.roomer.presentation.screens.destinations.FavouriteScreenDestination
import com.example.roomer.presentation.screens.destinations.HomeScreenDestination
import com.example.roomer.presentation.screens.destinations.PostScreenDestination
import com.example.roomer.presentation.screens.destinations.ProfileScreenDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

enum class NavbarItem(
    val iconUnSelected: Int,
    val iconSelected: Int,
    val direction: DirectionDestinationSpec,
) {
    Home(
        R.drawable.homeun,
        R.drawable.homein,
        HomeScreenDestination,
    ),
    Favourite(
        R.drawable.favun,
        R.drawable.favin,
        FavouriteScreenDestination,
    ),
    Post(
        R.drawable.postun,
        R.drawable.postin,
        PostScreenDestination,
    ),
    Chats(
        R.drawable.chatun,
        R.drawable.chatin,
        ChatsScreenDestination,
    ),
    Profile(
        R.drawable.profun,
        R.drawable.profin,
        ProfileScreenDestination
    )
}
