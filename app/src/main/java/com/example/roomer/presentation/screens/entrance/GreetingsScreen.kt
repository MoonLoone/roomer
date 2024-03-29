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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomer.R
import com.example.roomer.presentation.screens.destinations.LoginScreenDestination
import com.example.roomer.presentation.screens.destinations.SignUpScreenDestination
import com.example.roomer.presentation.ui_components.GreenButtonOutline
import com.example.roomer.presentation.ui_components.GreenButtonPrimary
import com.example.roomer.utils.Constants
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun GreetingsScreen(
    navigator: DestinationsNavigator
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(
                start = dimensionResource(id = R.dimen.screen_start_margin),
                end = dimensionResource(id = R.dimen.screen_end_margin)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.greetings_screen_header_1),
            fontSize = 30.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(R.string.greetings_screen_header_2),
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.text_secondary)
        )
        GreenButtonPrimary(
            text = stringResource(R.string.login_button_label),
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        ) {
            navigator.navigate(
                LoginScreenDestination(
                    Constants.ScreensId.greetingScreenId
                )
            )
        }
        GreenButtonOutline(
            text = stringResource(R.string.sign_up_button_label),
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
        ) {
            navigator.navigate(SignUpScreenDestination)
        }
    }
}
