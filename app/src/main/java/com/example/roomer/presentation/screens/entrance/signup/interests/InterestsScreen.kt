package com.example.roomer.presentation.screens.entrance.signup.interests

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.roomer.domain.model.signup.interests.InterestModel
import com.example.roomer.presentation.screens.destinations.HomeScreenDestination
import com.example.roomer.presentation.ui_components.GreenButtonOutline
import com.example.roomer.presentation.ui_components.GreenButtonPrimary
import com.example.roomer.presentation.ui_components.InterestsButtons
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun InterestsScreen(
    id: Int,
    navigator: DestinationsNavigator,
    interestsScreenViewModel: InterestsScreenViewModel = hiltViewModel()
) {

    val state = interestsScreenViewModel.state.value
    val interests = interestsScreenViewModel.interests.value
    val selectedItems = rememberSaveable {
        mutableStateOf<List<InterestModel>>(emptyList())
    }
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
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth(),
                color = colorResource(id = R.color.primary_dark),
                progress = 0.8f
            )
            Text(
                text = stringResource(R.string.tell_us_about_interests),
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start
            )
            if (state.isInterestsLoaded) {
                InterestsButtons(
                    label = stringResource(R.string.choose_10_maximum),
                    values = interests,
                    selectedItems = selectedItems.value,
                    onSelectedChange = { selectedItems.value = it }
                )
            }
            if (state.isInterestsSent) {
                navigator.navigate(HomeScreenDestination())
            }
            if (state.isLoading) {
                CircularProgressIndicator(
                    color = colorResource(id = R.color.primary_dark)
                )
            }
            if (state.internetProblem) {
                if (!state.isInterestsLoaded)
                    GreenButtonOutline(text = stringResource(R.string.retry)) {
                        interestsScreenViewModel.getInterests()
                    }
            }
            GreenButtonPrimary(
                text = stringResource(R.string.apply_button),
                modifier = Modifier
                    .fillMaxWidth(),
                enabled = state.isInterestsLoaded
            ) {
                interestsScreenViewModel.putInterests(selectedItems.value)
            }
        }
    }
}
