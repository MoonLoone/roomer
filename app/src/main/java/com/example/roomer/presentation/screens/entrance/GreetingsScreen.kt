package com.example.roomer.presentation.screens.entrance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.roomer.R
import com.example.roomer.presentation.screens.destinations.HomeScreenDestination
import com.example.roomer.presentation.screens.destinations.LoginScreenDestination
import com.example.roomer.presentation.screens.destinations.SignUpScreen1Destination
import com.example.roomer.presentation.ui_components.GreenButtonOutline
import com.example.roomer.presentation.ui_components.GreenButtonPrimary
import com.example.roomer.utils.Consts
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun StartScreen(
    navigator: DestinationsNavigator,
    greetingScreenViewModel: GreetingScreenViewModel = viewModel()
) {
    if (greetingScreenViewModel.isUserAuthorized) navigator.navigate(HomeScreenDestination())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 40.dp, end = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Welcome to Roomer",
            fontSize = 30.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Get started with us",
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.text_secondary)
        )
        GreenButtonPrimary(
            text = "Login",
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
        ) {
            navigator.navigate(LoginScreenDestination(Consts.greetingScreenId))
        }
        GreenButtonOutline(
            text = "Sign Up",
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
        ) {
            navigator.navigate(SignUpScreen1Destination(Consts.greetingScreenId))
        }
    }
}
