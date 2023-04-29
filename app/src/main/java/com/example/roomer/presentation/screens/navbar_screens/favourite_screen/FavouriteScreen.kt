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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.roomer.data.shared.HousingLike
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
        val listOfFavourites =
            favouriteViewModel.pagingData.collectAsState().value.collectAsLazyPagingItems()
        val scrollPosition = remember {
            mutableStateOf(0)
        }
        TopLine()
        FavouritesList(
            scrollPosition,
            listOfFavourites,
            favouriteViewModel
        )
    }
}

@Composable
private fun FavouritesList(
    scrollPosition: MutableState<Int>,
    listOfFavourites: LazyPagingItems<Room>?,
    housingLike: HousingLike
) {
    val state = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = state,
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.list_elements_margin)
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        listOfFavourites?.let {
            if (it.loadState.append is LoadState.NotLoading) {
                items(listOfFavourites) { room ->
                    room?.let {
                        RoomCard(recommendedRoom = room, isMiniVersion = false, housingLike)
                        scrollPosition.value = state.firstVisibleItemIndex
                    }
                    LaunchedEffect(coroutineScope) {
                        state.animateScrollToItem(scrollPosition.value)
                    }
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
