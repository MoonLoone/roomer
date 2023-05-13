package com.example.roomer.presentation.screens.profile_nested_screens.follows

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
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
import com.example.roomer.domain.model.entities.Follow
import com.example.roomer.domain.model.entities.User
import com.example.roomer.presentation.screens.destinations.UserDetailsScreenDestination
import com.example.roomer.presentation.ui_components.FollowCard
import com.example.roomer.utils.NavbarManagement
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun FollowsScreen(
    navigator: DestinationsNavigator,
    followsViewModel: FollowScreenViewModel = hiltViewModel()
) {
    NavbarManagement.showNavbar()
    val state = followsViewModel.state.collectAsState().value
    val follows = followsViewModel.follows.value
    Column(
        modifier = Modifier.padding(
            start = dimensionResource(id = R.dimen.screen_start_margin),
            end = dimensionResource(
                id = R.dimen.screen_end_margin
            ),
            top = dimensionResource(id = R.dimen.screen_top_margin),
            bottom = dimensionResource(id = R.dimen.bottom_padding)
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.follows_title),
            style = TextStyle(
                color = colorResource(
                    id = R.color.black
                ),
                fontSize = integerResource(id = R.integer.label_text).sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        FollowsList(
            state = state,
            follows = follows,
            navigateToUserDetails = { user -> navigator.navigate(UserDetailsScreenDestination(user)) },
            deleteFollow = { follow -> followsViewModel.deleteFollow(follow) }
        )
    }
}

@Composable
private fun FollowsList(
    state: FollowsScreenState,
    follows: List<Follow>,
    navigateToUserDetails: (User) -> Unit,
    deleteFollow: (Follow) -> Unit
) {
    if (state.isLoading) CircularProgressIndicator()
    if (state.success) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.column_elements_small_margin)
            )
        ) {
            items(follows.size) { index ->
                val followUser = follows[index].user
                FollowCard(
                    user = followUser,
                    onClick = { navigateToUserDetails.invoke(followUser) },
                    deleteFollow = { deleteFollow.invoke(follows[index]) }
                )
            }
        }
    }
    if (state.emptyFollowsList) {
        Text(text = "Empty follow list")
        Text(text = "Do you want to find new follow?")
    }
}
