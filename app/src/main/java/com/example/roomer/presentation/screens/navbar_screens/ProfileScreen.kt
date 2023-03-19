package com.example.roomer.presentation.screens.navbar_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomer.R
import com.example.roomer.presentation.screens.destinations.AccountScreenDestination
import com.example.roomer.presentation.ui_components.ProfileContentLine
import com.example.roomer.utils.NavbarManagement
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun ProfileScreen(
    navigator: DestinationsNavigator,
) {
    NavbarManagement.showNavbar()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                top = 24.dp,
                start = 40.dp,
                end = 40.dp
            )
    ) {
        Text(
            text = stringResource(R.string.profile_title),
            textAlign = TextAlign.Start,
            fontSize = integerResource(id = R.integer.label_text_size).sp,
        )
        Image(
            painter = painterResource(id = R.drawable.ordinary_client),
            contentDescription = "Client avatar",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 24.dp, bottom = 16.dp)
                .width(152.dp)
                .height(152.dp)
                .clip(RoundedCornerShape(100))
                .clickable {
                },
        )
        ProfileContentLine(
            "Account",
            R.drawable.account_icon,
            onNavigateToFriends = {
                navigator.navigate(AccountScreenDestination)
            }
        )
        ProfileContentLine(
            "Location",
            R.drawable.location_icon,
            onNavigateToFriends = {
            },
        )
        ProfileContentLine(
            "Rating",
            R.drawable.rating_icon,
            onNavigateToFriends = {
            },
        )
        ProfileContentLine(
            "Settings",
            R.drawable.settings_icon,
            onNavigateToFriends = {
            },
        )
        ProfileContentLine(
            "Logout",
            R.drawable.logout_icon,
            onNavigateToFriends = {
            },
        )
    }
}
