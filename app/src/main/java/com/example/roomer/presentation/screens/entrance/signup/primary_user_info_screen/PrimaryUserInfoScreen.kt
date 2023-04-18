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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(id = R.dimen.screen_start_margin),
                    end = dimensionResource(id = R.dimen.screen_end_margin)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.list_elements_margin)
            )
        ) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                color = colorResource(id = R.color.primary_dark),
                progress = 0.2f
            )
            Text(
                text = stringResource(R.string.tell_us_more_about_you),
                fontSize = integerResource(id = R.integer.label_text).sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start
            )
            UsualTextField(
                title = stringResource(R.string.first_name),
                placeholder = stringResource(R.string.first_name_placeholder),
                value = signUpViewModel.firstName,
                onValueChange = {
                    signUpViewModel.firstName = it
                }
            )
            UsualTextField(
                title = stringResource(R.string.last_name),
                placeholder = stringResource(R.string.last_name_placeholder),
                value = signUpViewModel.lastName,
                onValueChange = {
                    signUpViewModel.lastName = it
                }
            )
            DateField(
                label = stringResource(R.string.date_of_birth),
                value = signUpViewModel.birthDate,
                onValueChange = {
                    signUpViewModel.birthDate = it
                }
            )
            SexField(
                value = signUpViewModel.sex,
                onValueChange = {
                    signUpViewModel.sex = it
                }
            )
            GreenButtonPrimary(
                text = stringResource(R.string.continue_button_label),
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
                    title = stringResource(R.string.login_alert_dialog_text),
                    text = uiState.errorMessage
                ) { signUpViewModel.clearError() }
            }
        }
    }
}
