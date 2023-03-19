package com.example.roomer.presentation.screens.entrance.signup.screen_one

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.remember
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.roomer.R
import com.example.roomer.presentation.screens.destinations.SignUpScreenTwoDestination
import com.example.roomer.presentation.ui_components.DateField
import com.example.roomer.presentation.ui_components.GreenButtonPrimary
import com.example.roomer.presentation.ui_components.SexField
import com.example.roomer.presentation.ui_components.SimpleAlertDialog
import com.example.roomer.presentation.ui_components.UsualTextField
import com.example.roomer.utils.Constants
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun SignUpScreenOne(
    id: Int,
    navigator: DestinationsNavigator,
    signUpScreenOneViewModel: SignUpScreenOneViewModel = hiltViewModel()
) {
    var firstNameValue by rememberSaveable {
        mutableStateOf("")
    }
    var lastNameValue by rememberSaveable {
        mutableStateOf("")
    }
    var birthDateValue by rememberSaveable {
        mutableStateOf("2022-01-27")
    }
    var sexValue by rememberSaveable {
        mutableStateOf("M")
    }
    val state = signUpScreenOneViewModel.state.value
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) { focusManager.clearFocus() },
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
                text = "Tell us more about you",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start
            )
            UsualTextField(
                title = "First Name",
                placeholder = "Your first name here",
                value = firstNameValue,
                onValueChange = {
                    firstNameValue = it
                    signUpScreenOneViewModel.clearState()
                },
                enabled = !state.isLoading,
            )
            UsualTextField(
                title = "Last Name",
                placeholder = "Your last name here",
                value = lastNameValue,
                onValueChange = {
                    lastNameValue = it
                    signUpScreenOneViewModel.clearState()
                },
                enabled = !state.isLoading,
            )
            DateField(
                label = "Date Of Birth",
                value = birthDateValue,
                onValueChange = { birthDateValue = it },
                enabled = !state.isLoading
            )
            SexField(
                value = sexValue,
                onValueChange = { sexValue = it },
                enabled = !state.isLoading
            )
            GreenButtonPrimary(
                enabled = !state.isLoading,
                text = "Continue",
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                signUpScreenOneViewModel.applyData(
                    firstNameValue,
                    lastNameValue,
                    sexValue,
                    birthDateValue
                )
            }
            if (state.isLoading) {
                CircularProgressIndicator(
                    color = colorResource(id = R.color.primary_dark)
                )
            }
            if (state.internetProblem) {
                SimpleAlertDialog(
                    title = stringResource(R.string.login_alert_dialog_title),
                    text = state.error
                ) { signUpScreenOneViewModel.clearState() }
            }
            if (state.success) {
                navigator.navigate(SignUpScreenTwoDestination(Constants.ScreensId.signUpTwoScreenId))
            }
        }
    }
}
