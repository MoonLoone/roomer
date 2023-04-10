package com.example.roomer.presentation.screens.navbar_screens.favourite_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
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
    val listOfFavourites = favouriteViewModel.favourites.value
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.list_elements_margin)
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Text(
                text = "Favourite",
                style = TextStyle(
                    fontSize = integerResource(id = R.integer.label_text).sp,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        items(listOfFavourites.size) { index ->
            RoomCard(recommendedRoom = listOfFavourites[index], isMiniVersion = false) { isLiked ->
                if (isLiked) favouriteViewModel.addToFavourites(listOfFavourites[index])
                else favouriteViewModel.removeLocalFavourite(listOfFavourites[index])
            }
        }
    }
}
