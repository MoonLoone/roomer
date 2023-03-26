package com.example.roomer.presentation.screens.entrance.signup.screen_three

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.roomer.R
import com.example.roomer.presentation.screens.destinations.InterestsScreenDestination
import com.example.roomer.presentation.ui_components.ButtonsRowMapped
import com.example.roomer.presentation.ui_components.DropdownTextFieldMapped
import com.example.roomer.presentation.ui_components.GreenButtonPrimary
import com.example.roomer.presentation.ui_components.SimpleAlertDialog
import com.example.roomer.utils.Consts
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun SignUpScreenThree(
    id: Int,
    navigator: DestinationsNavigator,
    signUpScreenThreeViewModel: SignUpScreenThreeViewModel = hiltViewModel()
) {
    var sleepTimeValue by rememberSaveable {
        mutableStateOf("N")
    }
    var alcoholAttitudeValue by rememberSaveable {
        mutableStateOf("I")
    }
    var smokingAttitudeValue by rememberSaveable {
        mutableStateOf("I")
    }
    var personalityValue by rememberSaveable {
        mutableStateOf("M")
    }
    var cleanHabitsValue by rememberSaveable {
        mutableStateOf("N")
    }
    val state = signUpScreenThreeViewModel.state.value

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
                text = stringResource(R.string.tell_us_about_your_living_habits),
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start
            )
            ButtonsRowMapped(
                label = stringResource(R.string.your_usual_sleep_time),
                values = mapOf(
                    Pair("N", "Night"),
                    Pair("D", "Day"),
                    Pair("O", "Occasionally")
                ),
                value = sleepTimeValue,
                onValueChange = { sleepTimeValue = it }
            )
            ButtonsRowMapped(
                label = stringResource(R.string.attitude_to_alcohol_title),
                values = mapOf(
                    Pair("P", "Positive"),
                    Pair("N", "Negative"),
                    Pair("I", "Indifferent")
                ),
                value = alcoholAttitudeValue,
                onValueChange = { alcoholAttitudeValue = it }
            )
            ButtonsRowMapped(
                label = stringResource(R.string.attitude_to_smoking_title),
                values = mapOf(
                    Pair("P", "Positive"),
                    Pair("N", "Negative"),
                    Pair("I", "Indifferent")
                ),
                value = smokingAttitudeValue,
                onValueChange = { smokingAttitudeValue = it }
            )
            ButtonsRowMapped(
                label = stringResource(R.string.personality_type_title),
                values = mapOf(
                    Pair("E", "Extraverted"),
                    Pair("I", "Introverted"),
                    Pair("M", "Mixed")
                ),
                value = personalityValue,
                onValueChange = { personalityValue = it }
            )
            DropdownTextFieldMapped(
                mapOfItems = mapOf(
                    Pair("N", "Neat"),
                    Pair("D", "It Depends"),
                    Pair("C", "Chaos")
                ),
                label = stringResource(R.string.clean_habits_title),
                value = cleanHabitsValue,
                onValueChange = { cleanHabitsValue = it }
            )
            GreenButtonPrimary(
                text = stringResource(R.string.continue_button),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                signUpScreenThreeViewModel.applyData(
                    sleepTimeValue,
                    alcoholAttitudeValue,
                    smokingAttitudeValue,
                    personalityValue,
                    cleanHabitsValue
                )
            }
            if (state.success) {
                navigator.navigate(InterestsScreenDestination(Consts.signUpThreeScreenId))
            }

            if (state.isLoading) {
                CircularProgressIndicator(
                    color = colorResource(id = R.color.primary_dark)
                )
            }
            if (state.internetProblem || state.error.isNotEmpty()) {
                SimpleAlertDialog(
                    title = stringResource(R.string.login_alert_dialog_title),
                    text = state.error
                ) {
                    signUpScreenThreeViewModel.clearState()
                }
            }
        }
    }
}
