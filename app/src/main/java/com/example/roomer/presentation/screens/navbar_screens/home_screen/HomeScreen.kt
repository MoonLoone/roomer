package com.example.roomer.presentation.screens.navbar_screens.home_screen

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.roomer.R
import com.example.roomer.data.room.entities.HistoryItem
import com.example.roomer.data.room.entities.toRoom
import com.example.roomer.data.room.entities.toUser
import com.example.roomer.data.shared.HousingLikeInterface
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.User
import com.example.roomer.presentation.screens.destinations.SearchRoomScreenDestination
import com.example.roomer.presentation.screens.destinations.UserDetailsScreenDestination
import com.example.roomer.presentation.ui_components.RoomCard
import com.example.roomer.presentation.ui_components.SearchField
import com.example.roomer.presentation.ui_components.UserCard
import com.example.roomer.utils.Constants
import com.example.roomer.utils.NavbarManagement
import com.example.roomer.utils.UtilsFunctions
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
) {
    NavbarManagement.showNavbar()
    val currentUser = homeScreenViewModel.currentUser.value
    val history = homeScreenViewModel.history.collectAsState().value
    val recommendedRooms = homeScreenViewModel.recommendedRooms.collectAsState().value
    val recommendedMates = homeScreenViewModel.recommendedMates.collectAsState().value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                top = dimensionResource(id = R.dimen.screen_top_margin),
                start = dimensionResource(id = R.dimen.screen_start_margin),
                end = dimensionResource(id = R.dimen.screen_end_margin)
            )
    ) {
        HeaderLine(
            user = currentUser,
            navigateToUser = { navigator.navigate(UserDetailsScreenDestination(currentUser)) })
        SearchField(onNavigateToFriends = { navigator.navigate(SearchRoomScreenDestination) })
        RecentlyWatched(history = history,
            housingLikeInterface = homeScreenViewModel.housingLike,
            navigateToUser = { user -> navigator.navigate(UserDetailsScreenDestination(user)) },
            navigateToRoom = { room -> })
        RecommendedRoommates(
            recommendedMates = recommendedMates,
            navigateToUser = { user -> navigator.navigate(UserDetailsScreenDestination(user)) })
        RecommendedRooms(
            recommendedRooms = recommendedRooms,
            housingLikeInterface = homeScreenViewModel.housingLike
        )
    }
}

@Composable
fun HeaderLine(user: User, navigateToUser: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.fillMaxHeight()) {
            Text(
                text = stringResource(R.string.home_screen_title),
                style = TextStyle(
                    color = colorResource(id = R.color.text_secondary),
                    fontSize = integerResource(id = R.integer.primary_text).sp
                )
            )
            Text(
                text = UtilsFunctions.trimString(
                    user.firstName + " " + user.lastName,
                    Constants.Home.HOME_USERNAME_MAX_LENGTH
                ),
                style = TextStyle(
                    color = colorResource(id = R.color.text_secondary),
                    fontSize = integerResource(id = R.integer.label_text).sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(user.avatar)
                .crossfade(true)
                .build(),
            placeholder = painterResource(id = R.drawable.ordinary_client),
            contentDescription = stringResource(R.string.user_avatar_content_description),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(dimensionResource(id = R.dimen.ordinary_image))
                .height(dimensionResource(id = R.dimen.ordinary_image))
                .padding(start = 16.dp)
                .clip(CircleShape)
                .clickable {
                    navigateToUser()
                },
            alignment = Alignment.Center
        )
    }
}

@Composable
fun RecentlyWatched(
    history: List<HistoryItem>, housingLikeInterface: HousingLikeInterface,
    navigateToUser: (User) -> Unit, navigateToRoom: (Room) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(148.dp),
        horizontalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.list_elements_margin)
        ),
        reverseLayout = true
    ) {
        items(history.size) { index ->
            val item = history[index]
            item.room?.toRoom()?.let { room ->
                RoomCard(
                    recommendedRoom = room,
                    isMiniVersion = false,
                    likeHousing = housingLikeInterface
                )
            }
            item.user?.toUser()?.let { user ->
                UserCard(recommendedRoommate = user) {
                    navigateToUser(user)
                }
            }
        }
    }
}

@Composable
fun RecommendedRoommates(recommendedMates: List<User>, navigateToUser: (User) -> Unit) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(148.dp),
        horizontalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.list_elements_margin)
        )
    ) {
        items(recommendedMates.size) { index ->
            UserCard(recommendedRoommate = recommendedMates[index]) {
                navigateToUser(recommendedMates[index])
            }
        }
    }
}

@Composable
fun RecommendedRooms(recommendedRooms: List<Room>, housingLikeInterface: HousingLikeInterface) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(148.dp),
        horizontalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.list_elements_margin)
        )
    ) {
        items(recommendedRooms.size) { index ->
            RoomCard(
                recommendedRoom = recommendedRooms[index],
                isMiniVersion = true,
                likeHousing = housingLikeInterface
            )
        }
    }
}
