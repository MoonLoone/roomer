package com.example.roomer.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.roomer.R
import com.example.roomer.domain.model.MessageToList
import com.example.roomer.domain.model.RecommendedRoom
import com.example.roomer.domain.model.RecommendedRoommate
import com.example.roomer.presentation.ui_components.MessageItem
import com.example.roomer.presentation.ui_components.ProfileContentLine
import com.example.roomer.presentation.ui_components.RoomCard
import com.example.roomer.presentation.ui_components.SearchField
import com.example.roomer.presentation.ui_components.UserCard
import com.example.roomer.utils.Screens
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun ProfileScreen(
    navigator: DestinationsNavigator,

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                top = 24.dp,
                start = 40.dp,
                end = 40.dp
            )
    ) {
        Text(
            text = stringResource(R.string.profile_title),
            textAlign = TextAlign.Start,
            fontSize = integerResource(id = R.integer.label_text_size).sp,
        )
        Image(
            painter = painterResource(id = R.drawable.ordinary_client),
            contentDescription = "Client avatar",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 24.dp, bottom = 16.dp)
                .width(152.dp)
                .height(152.dp)
                .clip(RoundedCornerShape(100))
                .clickable {
                },
        )
        ProfileContentLine(
            Screens.Account.name,
            R.drawable.account_icon,
            onNavigateToFriends = {

            }
        )
        ProfileContentLine(
            Screens.Location.name,
            R.drawable.location_icon,
            onNavigateToFriends = {

            },
        )
        ProfileContentLine(
            Screens.Rating.name,
            R.drawable.rating_icon,
            onNavigateToFriends = {

            },
        )
        ProfileContentLine(
            Screens.Settings.name,
            R.drawable.settings_icon,
            onNavigateToFriends = {

            },
        )
        ProfileContentLine(
            Screens.Logout.name,
            R.drawable.logout_icon,
            onNavigateToFriends = {

            },
        )
    }
}

@Destination
@Composable
fun ChatsScreen(
    navigator: DestinationsNavigator,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 24.dp,
                start = 40.dp,
                end = 40.dp
            )
    ) {
        var searchText by remember {
            mutableStateOf(TextFieldValue(""))
        }
        TextField(
            label = {
                Text(
                    text = "Search in messages",
                    style = TextStyle(
                        color = colorResource(id = R.color.primary_dark),
                        fontSize = 12.sp
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            },
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = integerResource(id = R.integer.primary_text_size).sp,
            ),
            value = searchText,
            onValueChange = { value ->
                if (value.text.length <= 100) {
                    searchText = value
                }
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.loupe_icon),
                    contentDescription = "search_icon",
                    modifier = Modifier
                        .height(
                            integerResource(id = R.integer.ordinary_icon_size).dp
                        )
                        .width(
                            integerResource(id = R.integer.ordinary_icon_size).dp
                        ),
                )
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.clear_icon),
                    contentDescription = "clear_text",
                    modifier = Modifier
                        .height(
                            24.dp
                        )
                        .width(
                            24.dp
                        )
                        .clickable { searchText = TextFieldValue("") },
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(
                    BorderStroke(
                        2.dp,
                        colorResource(
                            id = R.color.primary_dark
                        )
                    ),
                    RoundedCornerShape(4.dp)
                ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(id = R.color.white)
            )
        )
        val listOfMessages = listOf(
            MessageToList(
                userAvatarPath = "path",
                messageDate = "12.22",
                messageCutText = "Hello my name is Piter",
                username = "Grigoriev Oleg",
                isRead = false,
                unreadMessages = 0,
                navigateToMessage = {  }
            ),
            MessageToList(
                userAvatarPath = "path",
                messageDate = "12.22",
                messageCutText = "Hello my name is Piter",
                username = "Grigoriev Oleg",
                isRead = true,
                unreadMessages = 10000,
                navigateToMessage = {  }
            ),
            MessageToList(
                userAvatarPath = "path",
                messageDate = "12.22",
                messageCutText = "Hello my name is Piter",
                username = "Grigoriev Oleg",
                isRead = false,
                unreadMessages = 15,
                navigateToMessage = {  }
            ),
        )
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(top = 24.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(listOfMessages.size) { index ->
                MessageItem(listOfMessages[index])
            }
        }
    }
}

@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
) {
    val recommendedRooms = mutableListOf<RecommendedRoom>()
    val recommendedRoommates = mutableListOf<RecommendedRoommate>()
    for (i in 0..5) {
        recommendedRooms.add(
            RecommendedRoom(
                i,
                "Room $i",
                "Location $i",
                "",
                false
            )
        )
        recommendedRoommates.add(
            RecommendedRoommate(
                i,
                "Andrey $i",
                0.2,
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
        SearchField(onNavigateToFriends = {  })
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

@Destination
@Composable
fun FavouriteScreen(
    navigator: DestinationsNavigator,
) {

    val listOfFavourites = listOf(
        RecommendedRoom(1, "Fav1", "Loc", "", true),
        RecommendedRoom(2, "Fav2", "Loc2", "", true),
        RecommendedRoom(3, "Fav3", "Loc3", "", true),
        RecommendedRoom(4, "Fav4", "Loc4", "", true)
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Text(
                text = "Favourite",
                style = TextStyle(
                    fontSize = integerResource(id = R.integer.label_text_size).sp,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        items(listOfFavourites.size) { index ->
            RoomCard(recommendedRoom = listOfFavourites[index], isMiniVersion = false)
        }
    }
}

@Destination
@Composable
fun PostScreen(
    navigator: DestinationsNavigator,
) {
    val navController = rememberNavController()
}
