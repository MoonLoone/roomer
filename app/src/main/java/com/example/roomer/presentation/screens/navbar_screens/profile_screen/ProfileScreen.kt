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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.roomer.R
import com.example.roomer.presentation.screens.destinations.AccountScreenDestination
import com.example.roomer.presentation.screens.destinations.FollowsScreenDestination
import com.example.roomer.presentation.screens.destinations.SplashScreenDestination
import com.example.roomer.presentation.screens.navbar_screens.profile_screen.ProfileScreenViewModel
import com.example.roomer.presentation.ui_components.BasicConfirmDialog
import com.example.roomer.presentation.ui_components.ProfileContentLine
import com.example.roomer.presentation.ui_components.SimpleAlertDialog
import com.example.roomer.utils.NavbarManagement
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun ProfileScreen(
    navigator: DestinationsNavigator,
    viewModel: ProfileScreenViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
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
            onClick = {
                navigator.navigate(AccountScreenDestination)
            }
        )
        ProfileContentLine(
            text = stringResource(id = R.string.follows_profile),
            iconId = R.drawable.follow_fill,
            onClick = { navigator.navigate(FollowsScreenDestination) }
        )
        ProfileContentLine(
            stringResource(R.string.rating_label),
            R.drawable.rating_icon,
            onClick = {
            }
        )
        ProfileContentLine(
            stringResource(R.string.settings_label),
            R.drawable.settings_icon,
            onClick = {
            }
        )
        ProfileContentLine(
            stringResource(R.string.logout_label),
            R.drawable.logout_icon,
            onClick = {
                viewModel.markStateLogout()
            }
        )
        if (state.isLogout) {
            BasicConfirmDialog(
                text = stringResource(R.string.logout_confirm),
                confirmOnClick = { viewModel.logout() },
                dismissOnClick = { viewModel.clearState() }
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(
                color = colorResource(id = R.color.primary_dark)
            )
        }
        if (state.error.isNotEmpty()) {
            SimpleAlertDialog(
                title = stringResource(R.string.login_alert_dialog_text),
                text = state.error
            ) {
                viewModel.clearState()
            }
        }
    }
    if (state.isLogout && state.success) navigator.navigate(SplashScreenDestination)
}
