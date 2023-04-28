package com.example.roomer.presentation.screens.navbar_screens.favourite_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.roomer.domain.model.entities.Room
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
        val listOfFavourites = favouriteViewModel.pagingData.collectAsState().value.collectAsLazyPagingItems()
        TopLine()
        FavouritesList(
            listOfFavourites
        ) { roomId -> favouriteViewModel.dislikeHousing(roomId) }
    }
}

@Composable
private fun FavouritesList(
    listOfFavourites: LazyPagingItems<Room>?,
    onDislikeRoom: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.list_elements_margin)
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        listOfFavourites?.let {
            items(listOfFavourites) { room ->
                room?.let {
                    RoomCard(recommendedRoom = room, isMiniVersion = false) {
                        if (!room.isLiked) {
                            onDislikeRoom.invoke(room.id)
                        }
                    }
                }
            }
        }
        if (listOfFavourites?.loadState?.append is LoadState.Loading) {
            item {
                CircularProgressIndicator()
            }
        }
    }
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
