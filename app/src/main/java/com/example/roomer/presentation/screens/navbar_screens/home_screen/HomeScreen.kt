package com.example.roomer.presentation.screens.navbar_screens.home_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.roomer.data.shared.housing_like.HousingLikeInterface
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.User
import com.example.roomer.presentation.screens.destinations.RoomDetailsScreenDestination
import com.example.roomer.presentation.screens.destinations.SearchRoomScreenDestination
import com.example.roomer.presentation.screens.destinations.SplashScreenDestination
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
    val state = homeScreenViewModel.state.collectAsState().value
    val currentUser = homeScreenViewModel.currentUser.value
    val history = homeScreenViewModel.history.collectAsState().value
    val recommendedRooms = homeScreenViewModel.recommendedRooms.collectAsState().value
    val recommendedMates = homeScreenViewModel.recommendedMates.collectAsState().value
    if (state.isLoading) LoadingView()
    if (state.unauthorized) {
        UnauthorizedView(navigateToSplash = {
            navigator.navigate(
                SplashScreenDestination()
            )
        })
    } else if (state.success) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    top = dimensionResource(id = R.dimen.screen_top_margin)
                )
        ) {
            HeaderLine(
                user = currentUser,
                navigateToUser = { navigator.navigate(UserDetailsScreenDestination(currentUser)) }
            )
            SearchField(
                navigateToFilters = { navigator.navigate(SearchRoomScreenDestination) },
                paddingValues = PaddingValues(
                    start = dimensionResource(
                        id = R.dimen.screen_start_margin
                    ),
                    end = dimensionResource(id = R.dimen.screen_end_margin),
                    top = 16.dp
                )
            )
            RecentlyWatched(
                emptyRecently = state.emptyHistory,
                history = history,
                housingLikeInterface = homeScreenViewModel.housingLike,
                navigateToUser = { user -> navigator.navigate(UserDetailsScreenDestination(user)) },
                navigateToRoom = { room -> navigator.navigate(RoomDetailsScreenDestination(room)) }
            )
            RecommendedRoommates(
                emptyRoommates = state.emptyRecommendedMates,
                recommendedMates = recommendedMates,
                navigateToUser = { user -> navigator.navigate(UserDetailsScreenDestination(user)) }
            )
            RecommendedRooms(
                emptyRooms = state.emptyRecommendedRooms,
                recommendedRooms = recommendedRooms,
                housingLikeInterface = homeScreenViewModel.housingLike,
                navigateToRoom = { room -> navigator.navigate(RoomDetailsScreenDestination(room)) }
            )
            Spacer(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.screen_bottom_margin) + 24.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun HeaderLine(user: User, navigateToUser: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = dimensionResource(id = R.dimen.screen_start_margin),
                end = dimensionResource(
                    id = R.dimen.screen_end_margin
                ),
                top = 16.dp
            )
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
                .clip(CircleShape)
                .clickable {
                    navigateToUser()
                },
            alignment = Alignment.Center
        )
    }
}

@Composable
private fun RecentlyWatched(
    emptyRecently: Boolean,
    history: List<HistoryItem>,
    housingLikeInterface: HousingLikeInterface,
    navigateToUser: (User) -> Unit,
    navigateToRoom: (Room) -> Unit
) {
    if (!emptyRecently) {
        val reverseHistory = history.reversed()
        Text(
            text = stringResource(id = R.string.recently_watched_label),
            modifier = Modifier.padding(
                start = dimensionResource(
                    id = R.dimen.screen_start_margin
                ),
                end = dimensionResource(id = R.dimen.screen_end_margin),
                top = 16.dp
            ),
            style = TextStyle(
                color = colorResource(id = R.color.black),
                fontSize = integerResource(id = R.integer.lists_title).sp,
                fontWeight = FontWeight.Bold
            )
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(148.dp)
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.list_elements_margin)
            ),
            contentPadding = PaddingValues(
                start = dimensionResource(
                    id = R.dimen.screen_start_margin
                ),
                end = dimensionResource(id = R.dimen.screen_end_margin)
            )
        ) {
            items(reverseHistory.size) { index ->
                val item = reverseHistory[index]
                item.room?.toRoom()?.let { room ->
                    RoomCard(
                        recommendedRoom = room,
                        isMiniVersion = true,
                        likeHousing = housingLikeInterface,
                        onClick = { navigateToRoom(room) }
                    )
                }
                item.user?.let { user ->
                    UserCard(recommendedRoommate = user) {
                        navigateToUser(user)
                    }
                }
            }
        }
    }
}

@Composable
private fun RecommendedRoommates(
    recommendedMates: List<User>,
    navigateToUser: (User) -> Unit,
    emptyRoommates: Boolean
) {
    if (!emptyRoommates) {
        Text(
            text = stringResource(id = R.string.recommended_roommates_label),
            modifier = Modifier.padding(
                start = dimensionResource(
                    id = R.dimen.screen_start_margin
                ),
                end = dimensionResource(id = R.dimen.screen_end_margin),
                top = 16.dp
            ),
            style = TextStyle(
                color = colorResource(id = R.color.black),
                fontSize = integerResource(id = R.integer.lists_title).sp,
                fontWeight = FontWeight.Bold
            )
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(148.dp)
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.list_elements_margin)
            ),
            contentPadding = PaddingValues(
                start = dimensionResource(
                    id = R.dimen.screen_start_margin
                ),
                end = dimensionResource(id = R.dimen.screen_end_margin)
            )
        ) {
            items(recommendedMates.size) { index ->
                UserCard(recommendedRoommate = recommendedMates[index]) {
                    navigateToUser(recommendedMates[index])
                }
            }
        }
    }
}

@Composable
private fun RecommendedRooms(
    recommendedRooms: List<Room>,
    housingLikeInterface: HousingLikeInterface,
    emptyRooms: Boolean,
    navigateToRoom: (Room) -> Unit
) {
    if (!emptyRooms) {
        Text(
            text = stringResource(id = R.string.recommended_rooms_label),
            modifier = Modifier.padding(
                start = dimensionResource(
                    id = R.dimen.screen_start_margin
                ),
                end = dimensionResource(id = R.dimen.screen_end_margin),
                top = 16.dp
            ),
            style = TextStyle(
                color = colorResource(id = R.color.black),
                fontSize = integerResource(id = R.integer.lists_title).sp,
                fontWeight = FontWeight.Bold
            )
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.list_elements_margin)
            ),
            contentPadding = PaddingValues(
                start = dimensionResource(
                    id = R.dimen.screen_start_margin
                ),
                end = dimensionResource(id = R.dimen.screen_end_margin)
            )
        ) {
            items(recommendedRooms.size) { index ->
                RoomCard(
                    recommendedRoom = recommendedRooms[index],
                    isMiniVersion = true,
                    likeHousing = housingLikeInterface,
                    onClick = { navigateToRoom(recommendedRooms[index]) }
                )
            }
        }
    }
}

@Composable
private fun UnauthorizedView(navigateToSplash: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.unathorized_message),
            style = TextStyle(
                color = colorResource(id = R.color.black),
                fontSize = integerResource(id = R.integer.label_text).sp
            )
        )
        Text(
            text = stringResource(id = R.string.try_again),
            style = TextStyle(
                color = colorResource(id = R.color.black),
                fontSize = integerResource(id = R.integer.label_text).sp
            ),
            modifier = Modifier.clickable {
                navigateToSplash()
            }
        )
    }
}

@Composable
private fun LoadingView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = Modifier.size(dimensionResource(id = R.dimen.ordinary_image)),
            color = colorResource(id = R.color.primary)
        )
    }
}
