package com.example.roomer.presentation.screens.navbar_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomer.R
import com.example.roomer.presentation.screens.destinations.AccountScreenDestination
import com.example.roomer.presentation.screens.destinations.FollowsScreenDestination
import com.example.roomer.presentation.ui_components.ProfileContentLine
import com.example.roomer.utils.NavbarManagement
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun ProfileScreen(
    navigator: DestinationsNavigator
) {
    NavbarManagement.showNavbar()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                top = dimensionResource(id = R.dimen.screen_top_margin),
                start = dimensionResource(id = R.dimen.screen_start_margin),
                end = dimensionResource(id = R.dimen.screen_end_margin)
            )
    ) {
        Text(
            text = stringResource(R.string.profile_title),
            textAlign = TextAlign.Start,
            fontSize = integerResource(id = R.integer.label_text).sp
        )
        Image(
            painter = painterResource(id = R.drawable.ordinary_client),
            contentDescription = stringResource(R.string.user_avatar_content_description),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 24.dp, bottom = 16.dp)
                .width(152.dp)
                .height(152.dp)
                .clickable {
                }
        )
        ProfileContentLine(
            stringResource(R.string.account_label),
            R.drawable.account_icon,
            onNavigateToFriends = {
                navigator.navigate(AccountScreenDestination)
            }
        )
        ProfileContentLine(
            text = stringResource(id = R.string.follows_profile), iconId = R.drawable.settings_icon,
            onNavigateToFriends = {navigator.navigate(FollowsScreenDestination)}
        )
        ProfileContentLine(
            stringResource(R.string.rating_label),
            R.drawable.rating_icon,
            onNavigateToFriends = {
            }
        )
        ProfileContentLine(
            stringResource(R.string.settings_label),
            R.drawable.settings_icon,
            onNavigateToFriends = {
            }
        )
        ProfileContentLine(
            stringResource(R.string.logout_label),
            R.drawable.logout_icon,
            onNavigateToFriends = {
            }
        )
    }
}
