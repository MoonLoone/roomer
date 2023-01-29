package com.example.roomer.presentation.screens.entrance.signup.screen_three

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.roomer.R
import com.example.roomer.presentation.ui_components.ButtonsRow
import com.example.roomer.presentation.ui_components.DropdownTextField
import com.example.roomer.presentation.ui_components.GreenButtonPrimary
import com.example.roomer.presentation.ui_components.SimpleAlertDialog

@Preview
@Composable
fun SignUpScreenThree(
//    id: Int,
//    navigator: DestinationsNavigator,
    signUpScreenThreeViewModel: SignUpScreenThreeViewModel = viewModel()
) {
    var sleepTimeValue by rememberSaveable {
        mutableStateOf("Night")
    }
    var alcoholAttitudeValue by rememberSaveable {
        mutableStateOf("Indifferent")
    }
    var smokingAttitudeValue by rememberSaveable {
        mutableStateOf("Indifferent")
    }
    var personalityValue by rememberSaveable {
        mutableStateOf("Mixed")
    }
    var cleanHabitsValue by rememberSaveable {
        mutableStateOf("Neat")
    }
    val focusManager = LocalFocusManager.current
    val state = signUpScreenThreeViewModel.state.value


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .clickable { focusManager.clearFocus() },
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
                progress = 0.2f
            )
            Text(
                text = "Tell us about your living habits",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start
            )
            ButtonsRow(
                label = "Your usual sleep time",
                values = listOf("Night", "Day", "Occasionally"),
                value = sleepTimeValue,
                onValueChange = { sleepTimeValue = it }
            )
            ButtonsRow(
                label = "Attitude to alcohol",
                values = listOf("Positive", "Negative", "Indifferent"),
                value = alcoholAttitudeValue,
                onValueChange = { alcoholAttitudeValue = it }
            )
            ButtonsRow(
                label = "Attitude to smoking",
                values = listOf("Positive", "Negative", "Indifferent"),
                value = smokingAttitudeValue,
                onValueChange = { smokingAttitudeValue = it }
            )
            ButtonsRow(
                label = "Personality type",
                values = listOf("Extraverted", "Introverted", "Mixed"),
                value = personalityValue,
                onValueChange = { personalityValue = it }
            )
            DropdownTextField(
                listOfItems = listOf("Neat", "It Depends", "Chaos"),
                label = "Clean habits",
                value = cleanHabitsValue,
                onValueChange = { cleanHabitsValue = it }
            )
            GreenButtonPrimary(
                text = "Continue",
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
                //TODO add navigation to the next screen
            }

            if (state.isLoading) {
                CircularProgressIndicator(
                    color = colorResource(id = R.color.primary_dark)
                )
            }
            if (state.internetProblem || state.error.isNotEmpty()) {
                SimpleAlertDialog(title = stringResource(R.string.login_alert_dialog_title), text = state.error) {
                    signUpScreenThreeViewModel.clearState()
                }
            }
        }
    }
}