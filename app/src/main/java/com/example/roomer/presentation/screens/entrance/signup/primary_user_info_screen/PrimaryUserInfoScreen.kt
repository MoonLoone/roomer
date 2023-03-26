package com.example.roomer.presentation.screens.entrance.signup.primary_user_info_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.example.roomer.R
import com.example.roomer.presentation.screens.destinations.AboutMeAvatarScreenDestination
import com.example.roomer.presentation.screens.entrance.signup.SignUpViewModel
import com.example.roomer.presentation.ui_components.DateField
import com.example.roomer.presentation.ui_components.GreenButtonPrimary
import com.example.roomer.presentation.ui_components.SexField
import com.example.roomer.presentation.ui_components.SimpleAlertDialog
import com.example.roomer.presentation.ui_components.UsualTextField
import com.example.roomer.utils.SignUpNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@SignUpNavGraph(start = true)
@Destination
@Composable
fun PrimaryUserInfoScreen(
    navigator: DestinationsNavigator,
    signUpViewModel: SignUpViewModel
) {
    val uiState by signUpViewModel.uiState.collectAsState()
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
                modifier = Modifier.fillMaxWidth(),
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
                value = signUpViewModel.firstName,
                onValueChange = {
                    signUpViewModel.firstName = it
                },
            )
            UsualTextField(
                title = "Last Name",
                placeholder = "Your last name here",
                value = signUpViewModel.lastName,
                onValueChange = {
                    signUpViewModel.lastName = it
                },
            )
            DateField(
                label = "Date Of Birth",
                value = signUpViewModel.birthDate,
                onValueChange = {
                    signUpViewModel.birthDate = it
                },
            )
            SexField(
                value = signUpViewModel.sex,
                onValueChange = {
                    signUpViewModel.sex = it
                },
            )
            GreenButtonPrimary(
                text = "Continue",
                modifier = Modifier.fillMaxWidth()
            ) {
                signUpViewModel.primaryUserInfoPageValidate()
            }
            if (uiState.isValid) {
                signUpViewModel.clearState()
                navigator.navigate(AboutMeAvatarScreenDestination)
            }
            if (uiState.isError) {
                SimpleAlertDialog(
                    title = stringResource(R.string.login_alert_dialog_title),
                    text = uiState.errorMessage
                ) { signUpViewModel.clearError() }
            }
        }
    }
}
