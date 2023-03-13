package com.example.roomer.presentation.screens.entrance.signup.habits_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.roomer.R
import com.example.roomer.presentation.screens.destinations.AboutMeAvatarScreenDestination
import com.example.roomer.presentation.screens.destinations.HabitsScreenDestination
import com.example.roomer.presentation.screens.destinations.InterestsScreenDestination
import com.example.roomer.presentation.screens.destinations.PrimaryUserInfoScreenDestination
import com.example.roomer.presentation.screens.entrance.signup.SignUpViewModel
import com.example.roomer.presentation.ui_components.ButtonsRowMapped
import com.example.roomer.presentation.ui_components.DropdownTextFieldMapped
import com.example.roomer.presentation.ui_components.GreenButtonPrimary
import com.example.roomer.presentation.ui_components.SimpleAlertDialog
import com.example.roomer.utils.Consts
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun HabitsScreen(
    navigator: DestinationsNavigator,
    signUpViewModel: SignUpViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth(),
                color = colorResource(id = R.color.primary_dark),
                progress = 0.6f
            )
            Text(
                text = "Tell us about your living habits",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start
            )
            ButtonsRowMapped(
                label = "Your usual sleep time",
                values = mapOf(
                    Pair("N", "Night"),
                    Pair("D", "Day"),
                    Pair("O", "Occasionally")
                ),
                value = signUpViewModel.sleepTime,
                onValueChange = {
                    signUpViewModel.sleepTime = it
                }
            )
            ButtonsRowMapped(
                label = "Attitude to alcohol",
                values = mapOf(
                    Pair("P", "Positive"),
                    Pair("N", "Negative"),
                    Pair("I", "Indifferent")
                ),
                value = signUpViewModel.alcoholAttitude,
                onValueChange = {
                    signUpViewModel.alcoholAttitude = it
                }
            )
            ButtonsRowMapped(
                label = "Attitude to smoking",
                values = mapOf(
                    Pair("P", "Positive"),
                    Pair("N", "Negative"),
                    Pair("I", "Indifferent")
                ),
                value = signUpViewModel.smokingAttitude,
                onValueChange = {
                    signUpViewModel.smokingAttitude = it
                }
            )
            ButtonsRowMapped(
                label = "Personality type",
                values = mapOf(
                    Pair("E", "Extraverted"),
                    Pair("I", "Introverted"),
                    Pair("M", "Mixed")
                ),
                value = signUpViewModel.personalityType,
                onValueChange = {
                    signUpViewModel.personalityType = it
                }
            )
            DropdownTextFieldMapped(
                mapOfItems = mapOf(
                    Pair("N", "Neat"),
                    Pair("D", "It Depends"),
                    Pair("C", "Chaos")
                ),
                label = "Clean habits",
                value = signUpViewModel.cleanHabits,
                onValueChange = {
                    signUpViewModel.cleanHabits = it
                }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                GreenButtonPrimary(
                    text = "Go Back",
                ) { navigator.navigate(AboutMeAvatarScreenDestination) }
                GreenButtonPrimary(
                    text = "Continue",
                ) { navigator.navigate(InterestsScreenDestination) }
            }
        }
    }
}
