package com.example.roomer.presentation.screens.search_screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.roomer.R
import com.example.roomer.presentation.screens.destinations.HomeScreenDestination
import com.example.roomer.presentation.screens.destinations.UserDetailsScreenDestination
import com.example.roomer.presentation.ui_components.BackBtn
import com.example.roomer.presentation.ui_components.GreenButtonOutline
import com.example.roomer.presentation.ui_components.UserCardResult
import com.example.roomer.utils.LoadingStates
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun SearchRoommateResults(
    navigator: DestinationsNavigator,
    sex: String?,
    location: String?,
    ageFrom: String?,
    ageTo: String?,
    employment: String?,
    alcoholAttitude: String?,
    smokingAttitude: String?,
    sleepTime: String?,
    personalityType: String?,
    cleanHabits: String?,
    interests: String?
) {
    val viewModel: SearchRoommateResultViewModel = hiltViewModel()
    val location = if (location == "") null else location
    val sex = if (sex == "A") null else sex
    val interests = if (interests != "") interests?.split("\n") else null
    viewModel.loadRoommates(
        sex,
        location,
        ageFrom,
        ageTo,
        employment,
        alcoholAttitude,
        smokingAttitude,
        sleepTime,
        personalityType,
        cleanHabits,
        interests
    )
    val roommates by viewModel.roommates.collectAsState()
    val loadingState = viewModel.loadingState.collectAsState()
    when (loadingState.value) {
        LoadingStates.Success -> Column(
            modifier = Modifier.padding(
                top = dimensionResource(id = R.dimen.screen_top_margin),
                start = dimensionResource(id = R.dimen.screen_start_margin),
                end = dimensionResource(id = R.dimen.screen_end_margin),
                bottom = dimensionResource(id = R.dimen.screen_nav_bottom_margin)
            ),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BackBtn(onBackNavigation = { navigator.navigate(HomeScreenDestination) })
                Text(
                    text = stringResource(R.string.roommate_results),
                    fontSize = integerResource(
                        id = R.integer.label_text
                    ).sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(
                    dimensionResource(id = R.dimen.list_elements_margin)
                )
            ) {
                item {
                    if (roommates.isEmpty()) {
                        Text(
                            text = stringResource(R.string.sorry_nothing_here),
                            style = TextStyle(
                                fontSize = integerResource(
                                    id = R.integer.label_text
                                ).sp
                            )
                        )
                    }
                }
                items(roommates.size) { index ->
                    UserCardResult(roommates[index]) {
                        navigator.navigate(UserDetailsScreenDestination(roommates[index]))
                    }
                }
            }
        }

        LoadingStates.Loading -> CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        LoadingStates.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(
                    dimensionResource(id = R.dimen.list_elements_margin)
                )
            ) {
                Text(
                    text = stringResource(R.string.something_went_wrong),
                    style = TextStyle(
                        fontSize = integerResource(
                            id = R.integer.primary_text
                        ).sp,
                        color = Color.Black
                    )
                )
                GreenButtonOutline(text = stringResource(R.string.retry)) {
                    viewModel.loadRoommates(
                        sex,
                        location,
                        ageFrom,
                        ageTo,
                        employment,
                        alcoholAttitude,
                        smokingAttitude,
                        sleepTime,
                        personalityType,
                        cleanHabits,
                        interests
                    )
                }
            }
        }
    }
}
