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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.roomer.R
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.presentation.screens.destinations.HomeScreenDestination
import com.example.roomer.presentation.ui_components.BackBtn
import com.example.roomer.presentation.ui_components.GreenButtonOutline
import com.example.roomer.presentation.ui_components.RoomCard
import com.example.roomer.utils.LoadingStates
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun SearchRoomResults(
    navigator: DestinationsNavigator,
) {
    val from = ""
    val to = ""
    val location = ""
    val bedrooms = ""
    val bathrooms = ""
    var apartmentType = ""
    apartmentType = when (apartmentType) {
        "Flat" -> "F"
        "Duplex" -> "DU"
        "House" -> "H"
        else -> "DO"
    }
    val viewModel: SearchRoomResultsViewModel = hiltViewModel()
    val rooms by viewModel.rooms.collectAsState()
    viewModel.loadRooms(from, to, bedrooms, bathrooms, apartmentType)
    val loadingState = viewModel.loadingState.collectAsState()
    when (loadingState.value) {
        LoadingStates.Success ->
            Column(
                modifier = Modifier.padding(
                    top = dimensionResource(id = R.dimen.screen_top_margin),
                    start = dimensionResource(id = R.dimen.screen_start_margin),
                    end = dimensionResource(id = R.dimen.screen_end_margin),
                ),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    BackBtn(onBackNavigation = { navigator.navigate(HomeScreenDestination) })
                    Text(
                        text = "Housing Results",
                        fontSize = integerResource(
                            id = R.integer.label_text
                        ).sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(
                        dimensionResource(id = R.dimen.list_elements_margin)
                    ),
                ) {
                    item {
                        if (rooms.isEmpty()) {
                            Text(
                                text = "Sorry, nothing here",
                                style = TextStyle(
                                    fontSize = integerResource(id = R.integer.label_text).sp,
                                )
                            )
                        }
                    }
                    items(rooms.size) { index ->
                        RoomCard(
                            recommendedRoom = Room(),
                            isMiniVersion = false
                        ) {}
                    }
                }
            }
        LoadingStates.Loading -> CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        LoadingStates.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Sorry, something went wrong. You should to retry",
                    style = TextStyle(
                        fontSize = integerResource(
                            id = R.integer.primary_text
                        ).sp,
                        color = Color.Black,
                    )
                )
                GreenButtonOutline(text = "Retry") {
                    viewModel.loadRooms(from, to, bedrooms, bathrooms, apartmentType)
                }
            }
        }
    }
}
