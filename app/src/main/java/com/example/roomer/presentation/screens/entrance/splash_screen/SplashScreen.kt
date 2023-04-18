package com.example.roomer.presentation.screens.entrance.splash_screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.roomer.R
import com.example.roomer.presentation.screens.destinations.GreetingsScreenDestination
import com.example.roomer.presentation.screens.destinations.HomeScreenDestination
import com.example.roomer.presentation.ui_components.SimpleAlertDialog
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator,
    splashScreenViewModel: SplashScreenViewModel = hiltViewModel()
) {
    val state by splashScreenViewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.roomer_logo),
            contentDescription = stringResource(
                R.string.logo_content_description
            )
        )
    }
    if (state.isInternetProblem) {
        SimpleAlertDialog(
            title = stringResource(R.string.login_alert_dialog_title),
            text = state.errorMessage
        ) {
            splashScreenViewModel.clearState()
            splashScreenViewModel.verifyToken()
        }
    }
    if (state.isError) {
        navigator.navigate(GreetingsScreenDestination)
    }
    if (state.isSuccess) {
        navigator.navigate(HomeScreenDestination)
    }
}
