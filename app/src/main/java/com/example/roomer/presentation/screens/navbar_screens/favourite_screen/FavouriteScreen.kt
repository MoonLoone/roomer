package com.example.roomer.presentation.screens.navbar_screens.favourite_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.roomer.R
import com.example.roomer.data.shared.housing_like.HousingLikeInterface
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.presentation.screens.destinations.RoomDetailsScreenDestination
import com.example.roomer.presentation.ui_components.RoomCard
import com.example.roomer.utils.NavbarManagement
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun FavouriteScreen(
    navigator: DestinationsNavigator,
    favouriteViewModel: FavouriteViewModel = hiltViewModel()
) {
    NavbarManagement.showNavbar()
    Column(
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.list_elements_margin)
        )
    ) {
        val listOfFavourites =
            favouriteViewModel.pagingData.value.collectAsLazyPagingItems()
        TopLine()
        FavouritesList(
            listOfFavourites,
            favouriteViewModel.housingLike
        ) { room -> navigator.navigate(RoomDetailsScreenDestination(room)) }
    }
}

@Composable
private fun FavouritesList(
    listOfFavourites: LazyPagingItems<Room>?,
    housingLike: HousingLikeInterface,
    navigateToRoom: (Room) -> Unit
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.list_elements_margin)
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        listOfFavourites?.let {
            items(listOfFavourites) { room ->
                room?.let {
                    RoomCard(
                        recommendedRoom = room,
                        isMiniVersion = false,
                        likeHousing = housingLike,
                        onClick = { navigateToRoom(room) }
                    )
                }
            }
            if (it.loadState.append is LoadState.Loading) {
                item {
                    CircularProgressIndicator()
                }
            }
            if (it.loadState.append is LoadState.Error) {
                item {
                    ErrorText()
                }
            }
        }
    }
}

@Composable
private fun ErrorText() {
    Text(
        text = stringResource(id = androidx.compose.ui.R.string.default_error_message),
        style = TextStyle(
            color = colorResource(
                id = R.color.black
            ),
            fontSize = integerResource(id = R.integer.primary_text).sp
        )
    )
}

@Composable
private fun TopLine() {
    Text(
        text = stringResource(R.string.favourite_screen_title),
        style = TextStyle(
            fontSize = integerResource(id = R.integer.label_text).sp,
            fontWeight = FontWeight.Bold
        ),
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}
