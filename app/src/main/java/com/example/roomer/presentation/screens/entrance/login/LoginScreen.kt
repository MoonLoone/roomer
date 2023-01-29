package com.example.roomer.presentation.screens.entrance.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.roomer.R
import com.example.roomer.presentation.screens.destinations.InterestsScreenDestination
import com.example.roomer.presentation.screens.destinations.MainScreenDestination
import com.example.roomer.presentation.screens.destinations.SignUpScreen1Destination
import com.example.roomer.presentation.ui_components.EmailField
import com.example.roomer.presentation.ui_components.GreenButtonPrimary
import com.example.roomer.presentation.ui_components.PasswordField
import com.example.roomer.presentation.ui_components.SimpleAlertDialog
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun LoginScreen(
    id: Int,
    navigator: DestinationsNavigator,
    loginScreenViewModel: LoginScreenViewModel = viewModel()
) {
    val state = loginScreenViewModel.state.value
    val focusManager = LocalFocusManager.current
    var emailValue by rememberSaveable {
        mutableStateOf("")
    }
    var passwordValue by rememberSaveable {
        mutableStateOf("")
    }
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
                .padding(
                    start = integerResource(id = R.integer.screen_padding_size).dp,
                    end = integerResource(id = R.integer.screen_padding_size).dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(integerResource(id = R.integer.elements_margin_size).dp),
        ){
            Text(
                text = stringResource(R.string.login_screen_title),
                fontSize = integerResource(id = R.integer.label_text_size).sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            EmailField(
                value = emailValue,
                onValueChange = { emailValue = it },
                enabled = !state.isLoading,
                errorMessage = "",
                label = stringResource(R.string.email_label),
                placeholder = stringResource(R.string.email_placeholder)
            )
            PasswordField(
                value = passwordValue,
                onValueChange = { passwordValue = it },
                enabled = !state.isLoading,
                placeholder = stringResource(R.string.password_placeholder),
                errorMessage = "",
                label = stringResource(R.string.password_label)
                )
            GreenButtonPrimary(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(R.string.login_button_text),
                enabled = !state.isLoading,
                ) {
                loginScreenViewModel.getUserLogin(emailValue, passwordValue)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.login_screen_supported_text),
                    fontSize = integerResource(id = R.integer.primary_text_size).sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
                ClickableText(
                    text = AnnotatedString(stringResource(R.string.login_screen_sign_up_text)),
                    modifier = Modifier
                        .padding(start= integerResource(id = R.integer.elements_margin_size_small).dp),
                    style = TextStyle(
                        fontSize = integerResource(id = R.integer.primary_text_size).sp,
                        fontWeight = FontWeight.Medium,
                        color = colorResource(id = R.color.primary_dark),
                        textAlign = TextAlign.Center

                    ),
                    onClick = {
                        navigator.navigate(SignUpScreen1Destination(1))
                    }
                )
            }
            if (state.isLoading) {
                CircularProgressIndicator(
                    color = colorResource(id = R.color.primary_dark)
                )
            }
            if (state.error.isNotEmpty()) {
                SimpleAlertDialog(title = stringResource(R.string.login_alert_dialog_title), text = state.error) {
                    loginScreenViewModel.clearViewModel()
                }
                passwordValue = ""
            }
            if (state.success) {
                if (id == 0) {
                    navigator.navigate(MainScreenDestination(1))
                }
                else {
                    navigator.navigate(InterestsScreenDestination(1))
                }
            }
        }
    }
}