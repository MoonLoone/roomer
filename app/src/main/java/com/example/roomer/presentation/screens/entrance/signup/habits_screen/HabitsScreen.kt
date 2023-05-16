package com.example.roomer.presentation.screens.entrance.signup.habits_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.roomer.R
import com.example.roomer.presentation.screens.destinations.AboutMeAvatarScreenDestination
import com.example.roomer.presentation.screens.destinations.InterestsScreenDestination
import com.example.roomer.presentation.screens.entrance.signup.SignUpViewModel
import com.example.roomer.presentation.ui_components.ButtonsRowMapped
import com.example.roomer.presentation.ui_components.DropdownTextFieldMapped
import com.example.roomer.presentation.ui_components.GreenButtonPrimary
import com.example.roomer.utils.navigation.SignUpNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@SignUpNavGraph
@Destination
@Composable
fun HabitsScreen(
    navigator: DestinationsNavigator,
    signUpViewModel: SignUpViewModel
) {
    val columnScroll = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(
                start = dimensionResource(id = R.dimen.screen_start_margin),
                end = dimensionResource(id = R.dimen.screen_end_margin)
            ),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(columnScroll),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    dimensionResource(id = R.dimen.list_elements_margin)
                )
            ) {
                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.sign_up_top_padding)))
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = colorResource(id = R.color.primary_dark),
                    progress = 0.6f
                )
                Text(
                    text = stringResource(R.string.tell_us_about_your_living_habits),
                    fontSize = integerResource(id = R.integer.label_text).sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Start
                )
                ButtonsRowMapped(
                    label = stringResource(R.string.your_usual_sleep_time),
                    values = mapOf(
                        Pair("N", stringResource(R.string.night)),
                        Pair("D", stringResource(R.string.day)),
                        Pair("O", stringResource(R.string.occasionally))
                    ),
                    value = signUpViewModel.sleepTime,
                    onValueChange = {
                        signUpViewModel.sleepTime = it
                    }
                )
                ButtonsRowMapped(
                    label = stringResource(R.string.attitude_to_alcohol_label),
                    values = mapOf(
                        Pair("P", stringResource(R.string.positive)),
                        Pair("N", stringResource(R.string.negative)),
                        Pair("I", stringResource(R.string.indifferent))
                    ),
                    value = signUpViewModel.alcoholAttitude,
                    onValueChange = {
                        signUpViewModel.alcoholAttitude = it
                    }
                )
                ButtonsRowMapped(
                    label = stringResource(R.string.attitude_to_smoking_label),
                    values = mapOf(
                        Pair("P", stringResource(R.string.positive)),
                        Pair("N", stringResource(R.string.negative)),
                        Pair("I", stringResource(R.string.indifferent))
                    ),
                    value = signUpViewModel.smokingAttitude,
                    onValueChange = {
                        signUpViewModel.smokingAttitude = it
                    }
                )
                ButtonsRowMapped(
                    label = stringResource(R.string.personality_type_label),
                    values = mapOf(
                        Pair("E", stringResource(R.string.extraverted)),
                        Pair("I", stringResource(R.string.introverted)),
                        Pair("M", stringResource(R.string.mixed))
                    ),
                    value = signUpViewModel.personalityType,
                    onValueChange = {
                        signUpViewModel.personalityType = it
                    }
                )
                DropdownTextFieldMapped(
                    mapOfItems = mapOf(
                        Pair("N", stringResource(R.string.neat)),
                        Pair("D", stringResource(R.string.it_depends)),
                        Pair("C", stringResource(R.string.chaos))
                    ),
                    label = stringResource(R.string.clean_habits_label),
                    value = signUpViewModel.cleanHabits,
                    onValueChange = {
                        signUpViewModel.cleanHabits = it
                    }
                )
                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.sign_up_bottom_padding)))
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(id = R.dimen.back_further_buttons_padding))
            ,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            GreenButtonPrimary(
                text = stringResource(R.string.back_button_label)
            ) { navigator.navigate(AboutMeAvatarScreenDestination) }
            GreenButtonPrimary(
                text = stringResource(R.string.continue_button_label)
            ) { navigator.navigate(InterestsScreenDestination) }
        }
    }
}
