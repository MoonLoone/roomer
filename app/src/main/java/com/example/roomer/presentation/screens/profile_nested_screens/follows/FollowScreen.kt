package com.example.roomer.presentation.screens.profile_nested_screens.follows

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
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
    FollowsList(
        state = state,
        follows = follows,
        navigateToUserDetails = { user -> navigator.navigate(UserDetailsScreenDestination(user)) },
        deleteFollow = { user -> followsViewModel.deleteFollow(user.userId) })
}

@Composable
private fun FollowsList(
    state: FollowsScreenState,
    follows: List<User>,
    navigateToUserDetails: (User) -> Unit,
    deleteFollow: (User) -> Unit
) {
    LazyColumn() {
        items(follows.size) { index ->
            val follow = follows[index]
            FollowCard(
                user = follow,
                onClick = { navigateToUserDetails.invoke(follow) },
                deleteFollow = { deleteFollow.invoke(follow) }
            )
        }
    }
}