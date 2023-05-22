package com.example.roomer.presentation.screens.shared_screens.user_details_screen.rate_user_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.example.roomer.domain.model.entities.User
import com.example.roomer.presentation.screens.destinations.UserDetailsScreenDestination
import com.example.roomer.presentation.screens.shared_screens.user_details_screen.comment_screen.CommentScreenState
import com.example.roomer.presentation.screens.shared_screens.user_details_screen.comment_screen.CommentScreenViewModel
import com.example.roomer.presentation.ui_components.BasicConfirmDialog
import com.example.roomer.presentation.ui_components.BasicHeaderBar
import com.example.roomer.presentation.ui_components.GreenButtonPrimary
import com.example.roomer.presentation.ui_components.NoRippleInteractionSource
import com.example.roomer.presentation.ui_components.SimpleAlertDialog
import com.example.roomer.presentation.ui_components.UsualTextField
import com.example.roomer.utils.Constants
import com.example.roomer.utils.NavbarManagement
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.time.LocalDate
import java.time.Period

/**
 * The Rate User Screen.
 * Allows users to rate other users.
 *
 * @author Andrey Karanik
 */

@Destination
@Composable
fun RateUserScreen(
    user: User,
    navigator: DestinationsNavigator,
    viewModel: RateUserScreenViewModel = hiltViewModel()
) {
    NavbarManagement.hideNavbar()
    val state by viewModel.state.collectAsState()
    val columnScroll = rememberScrollState()
    val textStyleHeadline = TextStyle(
        color = colorResource(id = R.color.black),
        fontWeight = FontWeight.Medium,
        fontSize = integerResource(id = R.integer.big_text).sp
    )
    val textStyleSecondary = TextStyle(
        color = colorResource(id = R.color.text_secondary),
        fontWeight = FontWeight.Normal,
        fontSize = integerResource(id = R.integer.medium_text).sp
    )

    if (state.success) {
        navigator.navigate(UserDetailsScreenDestination(user))
    }
    StateDialog(viewModel, state)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = dimensionResource(id = R.dimen.screen_start_margin),
                end = dimensionResource(id = R.dimen.screen_end_margin),
                top = dimensionResource(id = R.dimen.screen_top_margin)
            ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        BasicHeaderBar(
            title = stringResource(R.string.rate_user_title)
        ) {
            navigator.popBackStack()
        }
        Box(modifier = Modifier.fillMaxSize()) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = colorResource(id = R.color.primary_dark)
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(columnScroll),
                    verticalArrangement = Arrangement.spacedBy(
                        dimensionResource(id = R.dimen.rate_user_column_vertical_arrangement)
                    )
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            dimensionResource(id = R.dimen.column_elements_small_margin)
                        )
                    ) {
                        UserAvatar(avatarUrl = user.avatar)
                        UserHeadline(
                            firstName = user.firstName,
                            lastName = user.lastName,
                            birthDate = user.birthDate ?: "",
                            sex = user.sex,
                            textStyleHeadline
                        )
                    }
                    StarRating(
                        value = viewModel.rating,
                        onValueChange = {
                            viewModel.rating = it
                        }
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(
                            dimensionResource(id = R.dimen.column_elements_small_margin)
                        )
                    ) {
                        UsualTextField(
                            title = stringResource(R.string.leave_comment_label),
                            placeholder = stringResource(R.string.leave_comment_placeholder),
                            singleLine = false,
                            value = viewModel.comment,
                            onValueChange = { newValue -> viewModel.comment = newValue }
                        )
                        Text(
                            text = stringResource(R.string.comment_maximum_text),
                            style = textStyleSecondary
                        )
                    }

                    AnonymousButton(
                        isChecked = viewModel.isAnonymous
                    ) {
                        viewModel.isAnonymous = it
                    }
                    GreenButtonPrimary(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        text = stringResource(R.string.send_review_button_label),
                        enabled = true,
                        onClick = {
                            viewModel.showConfirmDialog()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun UserAvatar(avatarUrl: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(avatarUrl)
            .crossfade(true)
            .build(),
        placeholder = painterResource(id = R.drawable.usual_client),
        contentDescription = stringResource(id = R.string.user_avatar_content_description),
        modifier = Modifier
            .size(dimensionResource(R.dimen.user_avatar_size))
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.user_avatar_corner_radius))),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun UserHeadline(
    firstName: String,
    lastName: String,
    birthDate: String,
    sex: String,
    textStyle: TextStyle
) {
    val userDate = birthDate.split(birthDate[4]).map { it.toInt() }
    val userAge = Period.between(
        LocalDate.of(userDate[0], userDate[1], userDate[2]),
        LocalDate.now()
    ).years

    val userHeadlineString = stringResource(
        R.string.details_headline_user,
        firstName.replaceFirstChar(Char::uppercaseChar),
        lastName.replaceFirstChar(Char::uppercaseChar),
        userAge,
        stringResource(id = Constants.Options.sexOptions.getOrDefault(sex, 0))
    )
    Text(
        text = userHeadlineString,
        style = textStyle
    )
}

@Composable
private fun StarRating(
    value: Int,
    onValueChange: (Int) -> Unit,
    enabled: Boolean = true
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.column_elements_small_margin)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (i in 0 until value) {
                IconButton(
                    onClick = { onValueChange(i + 1) },
                    enabled = enabled,
                    interactionSource = NoRippleInteractionSource()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.filled_star_icon),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
            }
            for (i in value until 5) {
                IconButton(
                    onClick = { onValueChange(i + 1) },
                    enabled = enabled,
                    interactionSource = NoRippleInteractionSource()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.outlined_star_icon),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }
}

@Composable
private fun AnonymousButton(
    isChecked: Boolean,
    onClick: (Boolean) -> Unit
) {
    IconToggleButton(modifier = Modifier
        .fillMaxWidth()
        .background(
            colorResource(R.color.secondary_color),
        ),
        checked = isChecked,
        onCheckedChange = onClick,
        interactionSource = NoRippleInteractionSource()
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        16.dp
                    ),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.tabler_spy_icon),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
                Text(
                    text = stringResource(R.string.rate_anonymously_text),
                    modifier = Modifier.align(Alignment.CenterVertically),
                    style = TextStyle(
                        color = colorResource(id = R.color.text_secondary),
                        fontWeight = FontWeight.Normal,
                        fontSize = integerResource(id = R.integer.medium_text).sp
                    )
                )
            }
            if (isChecked) {
                Icon(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    painter = painterResource(R.drawable.checkbox_on_icon),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        }
    }
}

@Composable
fun StateDialog(viewModel: RateUserScreenViewModel, state: RateUserScreenState) {
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

    if (state.ratingNotSpecified) {
        SimpleAlertDialog(
            title = stringResource(R.string.error_dialog_text),
            text = stringResource(R.string.rating_not_specified_alert_dialog_text)
        ) {
            viewModel.clearState()
        }
    }

    if (state.commentIsEmpty) {
        SimpleAlertDialog(
            title = stringResource(R.string.error_dialog_text),
            text = stringResource(R.string.comment_is_empty_alert_dialog_text)
        ) {
            viewModel.clearState()
        }
    }

    if (viewModel.confirmation) {
        BasicConfirmDialog(
            text = stringResource(R.string.send_review_confirm_dialog_text),
            confirmOnClick = {
                viewModel.sendReview()
            },
            dismissOnClick = {
                viewModel.hideConfirmDialog()
            }
        )
    }
}