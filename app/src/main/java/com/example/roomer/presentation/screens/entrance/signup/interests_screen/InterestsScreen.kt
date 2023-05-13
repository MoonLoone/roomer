package com.example.roomer.presentation.screens.entrance.signup.interests_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.roomer.R
import com.example.roomer.presentation.screens.destinations.HabitsScreenDestination
import com.example.roomer.presentation.screens.destinations.SplashScreenDestination
import com.example.roomer.presentation.screens.entrance.signup.SignUpViewModel
import com.example.roomer.presentation.ui_components.GreenButtonOutline
import com.example.roomer.presentation.ui_components.GreenButtonPrimary
import com.example.roomer.presentation.ui_components.InterestsButtons
import com.example.roomer.utils.navigation.SignUpNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@SignUpNavGraph
@Destination
@Composable
fun InterestsScreen(
    navigator: DestinationsNavigator,
    interestsScreenViewModel: InterestsScreenViewModel = hiltViewModel(),
    signUpViewModel: SignUpViewModel
) {
    val state by interestsScreenViewModel.state.collectAsState()
    val interests = interestsScreenViewModel.interests.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
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
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.list_elements_margin)
            )
        ) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth(),
                color = colorResource(id = R.color.primary_dark),
                progress = 0.8f
            )
            Text(
                text = stringResource(R.string.tell_us_about_interests),
                fontSize = integerResource(id = R.integer.label_text).sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            GreenButtonPrimary(
                text = stringResource(R.string.back_button_label)
            ) {
                navigator.navigate(HabitsScreenDestination())
            }
            GreenButtonPrimary(
                text = stringResource(R.string.finish_button_label)
            ) {
                interestsScreenViewModel.putSignUpData(
                    signUpViewModel.firstName,
                    signUpViewModel.lastName,
                    signUpViewModel.sex,
                    signUpViewModel.birthDate,
                    signUpViewModel.avatar!!,
                    signUpViewModel.personDescription,
                    signUpViewModel.employment,
                    signUpViewModel.sleepTime,
                    signUpViewModel.alcoholAttitude,
                    signUpViewModel.smokingAttitude,
                    signUpViewModel.personalityType,
                    signUpViewModel.cleanHabits,
                    signUpViewModel.interests,
                    signUpViewModel.city
                )
            }
        }
    }
    if (state.isInterestsLoaded) {
        InterestsButtons(
            label = stringResource(R.string.choose_10_maximum),
            values = interests,
            selectedItems = signUpViewModel.interests,
            onSelectedChange = { signUpViewModel.interests = it }
        )
    }
    if (state.isInterestsSent) {
        navigator.navigate(SplashScreenDestination)
    }
    if (state.isLoading) {
        CircularProgressIndicator(
            color = colorResource(id = R.color.primary_dark)
        )
    }
    if (state.internetProblem) {
        if (!state.isInterestsLoaded) {
            GreenButtonOutline(text = stringResource(R.string.retry)) {
                interestsScreenViewModel.getInterests()
            }
        }
    }
}
