package com.example.roomer.presentation.screens.profile_nested_screens.follows

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.roomer.domain.model.entities.Follow
import com.example.roomer.domain.model.entities.User
import com.example.roomer.presentation.screens.destinations.UserDetailsScreenDestination
import com.example.roomer.presentation.ui_components.FollowCard
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination
@Composable
fun FollowsScreen(
    navigator: DestinationsNavigator,
    followsViewModel: FollowScreenViewModel = hiltViewModel()
) {
    val state = followsViewModel.state.collectAsState().value
    val follows = followsViewModel.follows.value
    Column() {
        Text(text = "Follows")
        FollowsList(
            state = state,
            follows = follows,
            navigateToUserDetails = { user -> navigator.navigate(UserDetailsScreenDestination(user)) },
            deleteFollow = { user -> followsViewModel.deleteFollow(user.userId) })
    }
}

@Composable
private fun FollowsList(
    state: FollowsScreenState,
    follows: List<Follow>,
    navigateToUserDetails: (User) -> Unit,
    deleteFollow: (User) -> Unit
) {
    LazyColumn() {
        items(follows.size) { index ->
            val followUser = follows[index].user
            FollowCard(
                user = followUser,
                onClick = { navigateToUserDetails.invoke(followUser) },
                deleteFollow = { deleteFollow.invoke(followUser) }
            )
        }
    }
}