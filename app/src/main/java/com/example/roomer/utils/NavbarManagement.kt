package com.example.roomer.utils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.roomer.R
import com.example.roomer.presentation.screens.destinations.FavouriteScreenDestination
import com.example.roomer.presentation.screens.destinations.HomeScreenDestination
import com.example.roomer.presentation.screens.destinations.MessengerScreenDestination
import com.example.roomer.presentation.screens.destinations.PostScreenDestination
import com.example.roomer.presentation.screens.destinations.ProfileScreenDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

object NavbarManagement {

    var navbarState: MutableState<Boolean> = mutableStateOf(false)

    fun showNavbar() {
        navbarState.value = true
    }

    fun hideNavbar() {
        navbarState.value = false
    }

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
        Messenger(
            R.drawable.chatun,
            R.drawable.chatin,
            MessengerScreenDestination,
        ),
        Profile(
            R.drawable.profun,
            R.drawable.profin,
            ProfileScreenDestination
        )
    }

}
