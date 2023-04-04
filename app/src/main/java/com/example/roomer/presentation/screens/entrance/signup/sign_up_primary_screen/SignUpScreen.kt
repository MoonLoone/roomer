package com.example.roomer.presentation.screens.entrance.signup.sign_up_primary_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.roomer.R
import com.example.roomer.presentation.screens.destinations.LoginScreenDestination
import com.example.roomer.presentation.ui_components.EmailField
import com.example.roomer.presentation.ui_components.GreenButtonPrimary
import com.example.roomer.presentation.ui_components.IconedTextField
import com.example.roomer.presentation.ui_components.PasswordField
import com.example.roomer.presentation.ui_components.SimpleAlertDialog
import com.example.roomer.utils.Constants
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun SignUpScreen(
    navigator: DestinationsNavigator,
    signUpScreenViewModel: SignUpScreenViewModel = hiltViewModel()
) {
    var emailValue by rememberSaveable {
        mutableStateOf("")
    }
    var passwordValue by rememberSaveable {
        mutableStateOf("")
    }
    var confirmPasswordValue by rememberSaveable {
        mutableStateOf("")
    }
    var usernameValue by rememberSaveable {
        mutableStateOf("")
    }
    val state = signUpScreenViewModel.state.value
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
                .padding(
                    start = dimensionResource(id = R.dimen.screen_start_margin),
                    end = dimensionResource(id = R.dimen.screen_end_margin)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.list_elements_margin)),
        ) {
            Text(
                text = "Sign Up",
                fontSize = integerResource(id = R.integer.label_text).sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            IconedTextField(
                title = "Username",
                placeholder = "Enter username here",
                onValueChange = {
                    usernameValue = it
                    if (state.isUsernameError)
                        signUpScreenViewModel.clearState()
                },
                value = usernameValue,
                icon = Icons.Filled.VerifiedUser,
                enabled = !state.isLoading,
                isError = state.isUsernameError,
                errorMessage = state.error
            )
            EmailField(
                value = emailValue,
                onValueChange = {
                    emailValue = it
                    if (state.isEmailError)
                        signUpScreenViewModel.clearState()
                },
                enabled = !state.isLoading,
                label = stringResource(id = R.string.email_label),
                placeholder = stringResource(id = R.string.email_placeholder),
                errorMessage = state.error,
                isError = state.isEmailError
            )
            PasswordField(
                value = passwordValue,
                onValueChange = {
                    passwordValue = it
                    if (state.isPasswordError)
                        signUpScreenViewModel.clearState()
                },
                enabled = !state.isLoading,
                label = stringResource(id = R.string.password_label),
                placeholder = stringResource(id = R.string.password_placeholder),
                isError = state.isPasswordError,
                errorMessage = state.error
            )
            PasswordField(
                value = confirmPasswordValue,
                label = stringResource(R.string.conf_pass_label),
                placeholder = stringResource(R.string.conf_pass_placeholder),
                onValueChange = {
                    confirmPasswordValue = it
                    if (state.isConfPasswordError)
                        signUpScreenViewModel.clearState()
                },
                enabled = !state.isLoading,
                isError = state.isConfPasswordError,
                errorMessage = state.error
            )
            GreenButtonPrimary(
                enabled = !state.isLoading,
                text = "Confirm",
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                signUpScreenViewModel.signUpUser(
                    emailValue,
                    passwordValue,
                    usernameValue,
                    confirmPasswordValue
                )
            }
            if (state.success) {
                navigator.navigate(LoginScreenDestination(Constants.ScreensId.signUpScreenId))
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
                ) {
                    signUpScreenViewModel.clearState()
                }
            }
        }
    }
}
