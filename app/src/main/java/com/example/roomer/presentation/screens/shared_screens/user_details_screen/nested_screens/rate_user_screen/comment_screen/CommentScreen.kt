package com.example.roomer.presentation.screens.shared_screens.user_details_screen.nested_screens.rate_user_screen.comment_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.roomer.R
import com.example.roomer.domain.model.entities.User
import com.example.roomer.presentation.ui_components.BasicHeaderBar
import com.example.roomer.presentation.ui_components.CommentCard
import com.example.roomer.presentation.ui_components.SimpleAlertDialog
import com.example.roomer.utils.NavbarManagement
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

/**
 * The Comment Screen.
 * Allows users to view comments to other users.
 *
 * @author Andrey Karanik
 */

@Destination
@Composable
fun CommentScreen(
    user: User,
    navigator: DestinationsNavigator,
    viewModel: CommentScreenViewModel = hiltViewModel()
) {
    NavbarManagement.hideNavbar()
    val state by viewModel.state.collectAsState()
    val textStyleSecondary = TextStyle(
        color = colorResource(id = R.color.text_secondary),
        fontWeight = FontWeight.Medium,
        fontSize = integerResource(id = R.integer.medium_text).sp
    )
    val scoreTextStyle = TextStyle(
        color = colorResource(id = R.color.black),
        fontWeight = FontWeight.Medium,
        fontSize = 48.sp
    )

    StateDialog(viewModel, state)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = dimensionResource(id = R.dimen.screen_start_margin),
                end = dimensionResource(id = R.dimen.screen_end_margin),
                top = dimensionResource(id = R.dimen.screen_top_margin)
            )
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = colorResource(id = R.color.primary_dark)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(
                16.dp
            )
        ) {
            BasicHeaderBar(
                title = stringResource(R.string.comment_screen_title)
            ) {
                navigator.popBackStack()
            }
            if (!state.isLoading) {
                if (viewModel.reviews.value.isNotEmpty()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = viewModel.getStarRatingAsString(),
                            style = scoreTextStyle
                        )
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                for (i in 0 until viewModel.getStarRatingAsCount()) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.star_icon),
                                        contentDescription = null,
                                        tint = Color.Unspecified
                                    )
                                }
                            }
                            Text(
                                modifier = Modifier
                                    .padding(start = 4.dp),
                                text = pluralStringResource(
                                    R.plurals.comments,
                                    viewModel.reviews.value.size,
                                    viewModel.reviews.value.size
                                ),
                                style = textStyleSecondary
                            )
                        }
                    }
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(
                            dimensionResource(id = R.dimen.list_elements_margin)
                        )
                    ) {
                        items(viewModel.reviews.value.size) { index ->
                            CommentCard(viewModel.reviews.value[index])
                        }
                    }
                } else {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = stringResource(R.string.no_comments_text),
                        style = textStyleSecondary
                    )
                }
            }
        }
    }
}

@Composable
fun StateDialog(viewModel: CommentScreenViewModel, state: CommentScreenState) {
    if (state.requestProblem) {
        SimpleAlertDialog(
            title = stringResource(R.string.error_dialog_text),
            text = state.error
        ) {
            viewModel.clearState()
        }
    }

    if (state.internetProblem) {
        SimpleAlertDialog(
            title = stringResource(R.string.error_dialog_text),
            text = stringResource(R.string.no_internet_connection_text)
        ) {
            viewModel.clearState()
        }
    }
}
