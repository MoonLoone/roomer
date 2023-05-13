package com.example.roomer.presentation.screens.navbar_screens.post_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.roomer.R
import com.example.roomer.presentation.screens.destinations.AddHousingScreenDestination
import com.example.roomer.presentation.ui_components.BasicConfirmDialog
import com.example.roomer.presentation.ui_components.GreenButtonOutlineIconed
import com.example.roomer.presentation.ui_components.GreenButtonPrimaryIconed
import com.example.roomer.presentation.ui_components.PostCard
import com.example.roomer.presentation.ui_components.SimpleAlertDialog
import com.example.roomer.utils.NavbarManagement
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

/**
 * The Post Screen.
 * Allows users to view and post their housing advertisements.
 *
 * @author Andrey Karanik
 */

@Destination
@Composable
fun PostScreen(
    navigator: DestinationsNavigator,
    viewModel: PostScreenViewModel = hiltViewModel()
) {
    NavbarManagement.showNavbar()
    val state by viewModel.state.collectAsState()

    if (state.isError) {
        SimpleAlertDialog(
            title = stringResource(R.string.error_dialog_text),
            text = state.errorMessage
        ) {
            viewModel.clearState()
        }
    }
    if (state.isInternetProblem) {
        SimpleAlertDialog(
            title = stringResource(R.string.error_dialog_text),
            text = stringResource(R.string.no_internet_connection_text)
        ) {
            viewModel.clearState()
        }
    }
    if (viewModel.removeConfirmation) {
        BasicConfirmDialog(
            text = stringResource(R.string.remove_housing_confirm_dialog_text),
            confirmButtonText = stringResource(R.string.yes),
            dismissButtonText = stringResource(R.string.no),
            confirmOnClick = {
                viewModel.removeAdvertisement()
            },
            dismissOnClick = {
                viewModel.hideRemoveConfirmDialog()
            }
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = dimensionResource(id = R.dimen.screen_top_margin),
                start = dimensionResource(id = R.dimen.screen_start_margin),
                end = dimensionResource(id = R.dimen.screen_end_margin)
            )
    ) {
        Text(
            text = stringResource(R.string.post_screen_title),
            style = TextStyle(
                textAlign = TextAlign.Start,
                fontSize = integerResource(id = R.integer.label_text).sp,
                fontWeight = FontWeight.Bold
            )
        )
        Box(modifier = Modifier.fillMaxSize()) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = colorResource(id = R.color.primary_dark)
                )
            } else {
                if (viewModel.advertisements.value.isNotEmpty()) {
                    Box {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(
                                    top = 24.dp
                                ),
                            verticalArrangement = Arrangement.spacedBy(
                                dimensionResource(id = R.dimen.list_elements_margin)
                            )
                        ) {
                            items(viewModel.advertisements.value.size) { index ->
                                PostCard(
                                    room = viewModel.advertisements.value[index],
                                    onEditClick = {
                                        navigator.navigate(
                                            AddHousingScreenDestination(
                                                viewModel.advertisements.value[index]
                                            )
                                        )
                                    },
                                    onRemoveClick = {
                                        viewModel.showRemoveConfirmDialog(
                                            viewModel.advertisements.value[index].id
                                        )
                                    }
                                )
                            }
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                )
                            }
                        }
                        GreenButtonOutlineIconed(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            text = stringResource(R.string.add_post_button_label),
                            trailingIconPainterId = R.drawable.postin,
                            trailingIconDescriptionId = R.string.icon_description,
                            enabled = true,
                            onClick = {
                                navigator.navigate(AddHousingScreenDestination(null))
                            }
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.no_posts_label),
                            style = TextStyle(
                                textAlign = TextAlign.Center,
                                fontSize = integerResource(id = R.integer.label_text).sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        GreenButtonPrimaryIconed(
                            text = stringResource(R.string.add_post_button_label),
                            trailingIcon = ImageVector.vectorResource(id = R.drawable.postin),
                            enabled = true,
                            onClick = {
                                navigator.navigate(AddHousingScreenDestination(null))
                            }
                        )
                    }
                }
            }
        }
    }
}
