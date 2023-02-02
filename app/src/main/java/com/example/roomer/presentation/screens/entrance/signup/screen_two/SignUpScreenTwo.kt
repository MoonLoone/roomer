package com.example.roomer.presentation.screens.entrance.signup.screen_two

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.roomer.R
import com.example.roomer.presentation.screens.destinations.SignUpScreenThreeDestination
import com.example.roomer.presentation.ui_components.*
import com.example.roomer.utils.Consts
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.io.File

@Destination
@Composable
fun SignUpScreenTwo(
    id: Int,
    navigator: DestinationsNavigator,
    signUpScreenTwoViewModel: SignUpScreenTwoViewModel = viewModel()
) {
    val avatarBitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }
    var aboutMeValue by rememberSaveable {
        mutableStateOf("")
    }
    var employmentValue by rememberSaveable {
        mutableStateOf("E")
    }
    val state = signUpScreenTwoViewModel.state.value
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
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth(),
                color = colorResource(id = R.color.primary_dark),
                progress = 0.4f
            )
            Text(
                text = "Just basic profile info",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start
            )
            Text(
                text = "Add profile picture",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start
            )
            ProfilePicture(
                enabled = !state.isLoading,
                bitmapValue = avatarBitmap.value,
                onBitmapValueChange = { avatarBitmap.value = it }
            )
            UsualTextField(
                enabled = !state.isLoading,
                title = "Write something about you",
                placeholder = "About Me",
                value = aboutMeValue,
                onValueChange = { aboutMeValue = it }
            )
            DropdownTextFieldMapped(
                enabled = !state.isLoading,
                mapOfItems = mapOf(
                    Pair("NE", "Not Employed"),
                    Pair("E", "Employed"),
                    Pair("S", "Searching For Work")
                ),
                label = "What you currently do?",
                value = employmentValue,
                onValueChange = { employmentValue = it }
            )
            //TODO Implement address field
            GreenButtonPrimary(
                enabled = !state.isLoading,
                text = "Confirm",
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                signUpScreenTwoViewModel.applyData(avatarBitmap.value, aboutMeValue, employmentValue)
            }
            if (state.success) {
                navigator.navigate(SignUpScreenThreeDestination(Consts.signUpTwoScreenId))
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
                    signUpScreenTwoViewModel.clearState()
                }
            }
        }
    }
}