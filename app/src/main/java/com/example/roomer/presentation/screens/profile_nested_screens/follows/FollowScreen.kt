package com.example.roomer.presentation.screens.profile_nested_screens.follows

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.roomer.presentation.screens.destinations.SearchRoommateScreenDestination
import com.example.roomer.presentation.screens.destinations.UserDetailsScreenDestination
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
    if (state.isLoading) Loading()
    Column(
        modifier = Modifier.padding(
            start = dimensionResource(id = R.dimen.screen_start_margin),
            end = dimensionResource(
                id = R.dimen.screen_end_margin
            ),
            top = dimensionResource(id = R.dimen.screen_top_margin)
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderLine()
        if (state.success) {
            FollowsList(
                follows = follows,
                navigateToUserDetails = { user ->
                    navigator.navigate(UserDetailsScreenDestination(user))
                },
                deleteFollow = { follow -> followsViewModel.unfollow(follow) }
            )
        }
        if (state.emptyFollowsList) {
            EmptyFollowsText(navigateToUsersFilters = {
                navigator.navigate(
                    SearchRoommateScreenDestination
                )
            })
        }
    }
}

@Composable
private fun FollowsList(
    follows: List<Follow>,
    navigateToUserDetails: (User) -> Unit,
    deleteFollow: (Follow) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.list_elements_margin)
        )
    ) {
        items(follows.size) { index ->
            val followUser = follows[index].user
            FollowCard(
                user = followUser,
                onClick = { navigateToUserDetails.invoke(followUser) },
                deleteFollow = {
                    deleteFollow.invoke(follows[index])
                }
            )
        }
    }
}

@Composable
private fun EmptyFollowsText(navigateToUsersFilters: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.empty_follow_list),
            style = TextStyle(
                color = colorResource(id = R.color.black),
                fontSize = integerResource(id = R.integer.primary_text).sp,
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = stringResource(R.string.find_new_follow),
            style = TextStyle(
                color = colorResource(id = R.color.primary),
                fontSize = integerResource(id = R.integer.primary_text).sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.clickable { navigateToUsersFilters() }
        )
    }
}

@Composable
private fun HeaderLine() {
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
}

@Composable
private fun Loading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = Modifier.size(
                dimensionResource(id = R.dimen.circular_loading_size)
            )
        )
    }
}
