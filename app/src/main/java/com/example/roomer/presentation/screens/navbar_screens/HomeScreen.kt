package com.example.roomer.presentation.screens.navbar_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomer.R
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.User
import com.example.roomer.presentation.screens.destinations.SearchRoomScreenDestination
import com.example.roomer.presentation.ui_components.RoomCard
import com.example.roomer.presentation.ui_components.SearchField
import com.example.roomer.presentation.ui_components.UserCard
import com.example.roomer.utils.NavbarManagement
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
) {
    NavbarManagement.showNavbar()
    val recommendedRooms = mutableListOf<Room>()
    val recommendedRoommates = mutableListOf<User>()
    for (i in 0..5) {
        recommendedRooms.add(
            Room()
        )
        recommendedRoommates.add(
            User(
                i,
                "Andrey $i",
                "",
                "",
            )
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                top = 18.dp,
                start = 40.dp,
                end = 40.dp
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(modifier = Modifier.fillMaxHeight()) {
                Text(
                    text = "Welcome back!",
                    style = TextStyle(
                        color = colorResource(id = R.color.text_secondary),
                        fontSize = 18.sp,
                    )
                )
                Text(
                    text = "Client name here",
                    style = TextStyle(
                        color = colorResource(id = R.color.text_secondary),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Image(
                painter = painterResource(id = R.drawable.ordinary_client),
                contentDescription = "Client avatar",
                modifier = Modifier
                    .height(56.dp)
                    .width(56.dp),
                alignment = Alignment.Center,
            )
        }
        SearchField(onNavigateToFriends = { navigator.navigate(SearchRoomScreenDestination) })
        Column(
            modifier = Modifier
                .scrollable(
                    rememberScrollState(),
                    orientation = Orientation.Vertical,
                )
                .fillMaxSize()
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Recently watched",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                )
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(148.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(recommendedRoommates.size - 2) { index ->
                        UserCard(recommendedRoommate = recommendedRoommates[index])
                    }
                    items(recommendedRooms.size - 2) { index ->
                        RoomCard(recommendedRoom = recommendedRooms[index], true)
                    }
                }
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Recommended rooms",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                )
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(148.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(recommendedRooms.size) { index ->
                        RoomCard(recommendedRoom = recommendedRooms[index], true)
                    }
                }
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Recommended roommates",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                )
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(148.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(recommendedRoommates.size) { index ->
                        UserCard(recommendedRoommate = recommendedRoommates[index])
                    }
                }
            }
        }
    }
}
