package com.example.roomer.presentation.screens.room_details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.roomer.R
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.presentation.screens.destinations.ChatScreenDestination
import com.example.roomer.presentation.screens.destinations.UserDetailsScreenDestination
import com.example.roomer.presentation.ui_components.BackBtn
import com.example.roomer.presentation.ui_components.NoRippleInteractionSource
import com.example.roomer.utils.Constants.Options.apartmentOptions
import com.example.roomer.utils.NavbarManagement
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun RoomDetailsScreen(
    navigator: DestinationsNavigator,
    room: Room,
    viewModel: RoomDetailsScreenViewModel = hiltViewModel()
) {
    NavbarManagement.hideNavbar()
    val isLiked by remember {
        mutableStateOf(room.isLiked)
    }
    val photos = if (room.fileContent?.isNotEmpty() == true) {
        room.fileContent
    } else {
        null
    }
    val avatar = if (room.host?.avatar?.isNotEmpty() == true) {
        room.host.avatar
    } else {
        null
    }

    Scaffold(
        modifier = Modifier.padding(
            start = dimensionResource(id = R.dimen.screen_start_margin),
            end = dimensionResource(id = R.dimen.screen_end_margin),
            top = dimensionResource(id = R.dimen.screen_top_margin)
        ),
        floatingActionButton = {
            Button(
                enabled = true,
                onClick = {
                    if (room.host != null) {
                        navigator.navigate(ChatScreenDestination(room.host))
                    }
                },
                modifier = Modifier
                    .padding(
                        start = dimensionResource(id = R.dimen.fab_start_margin),
                        bottom = dimensionResource(id = R.dimen.result_button_bottom_margin)
                    )
                    .background(
                        color = colorResource(id = R.color.primary_dark),
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.fab_size) / 2)
                    )
                    .height(dimensionResource(id = R.dimen.fab_size))
                    .width(dimensionResource(id = R.dimen.fab_size))
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.fab_size) / 4)),
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = colorResource(id = R.color.primary_dark),
                    contentColor = colorResource(id = R.color.white)
                ),
                interactionSource = NoRippleInteractionSource(),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.big_envelope_icon),
                    contentDescription = stringResource(R.string.chat),
                    colorFilter = ColorFilter.tint(color = colorResource(id = R.color.white)),
                    modifier = Modifier
                        .height(dimensionResource(id = R.dimen.fab_size) / 2)
                        .width(dimensionResource(id = R.dimen.fab_size) / 2)
                )
            }
        }

    ) {
        Column(
            modifier = Modifier
                .padding(
                    bottom = it.calculateBottomPadding()
                )
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.list_elements_margin)
            )
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.top_spaced))) {
                BackBtn(
                    onBackNavigation = {
                        navigator.popBackStack()
                    }
                )
                Text(
                    text = stringResource(R.string.details),
                    style = TextStyle(
                        fontSize = integerResource(
                            id = R.integer.label_text
                        ).sp, color = Color.Black
                    ),
                    textAlign = TextAlign.Center
                )
                Icon(
                    painter = if (room.isLiked) {
                        painterResource(id = R.drawable.room_like_in_icon)
                    } else {
                        painterResource(id = R.drawable.room_like_icon)
                    },
                    contentDescription = stringResource(id = R.string.like_icon),
                    tint = colorResource(id = R.color.black),
                    modifier = Modifier
                        .padding(
                            top = dimensionResource(id = R.dimen.row_vert_margin),
                            end = dimensionResource(id = R.dimen.row_vert_margin)
                        )
                        .width(dimensionResource(id = R.dimen.big_icon))
                        .height(dimensionResource(id = R.dimen.big_icon))
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner_full)))
                        .clickable {
                            CoroutineScope(Dispatchers.IO).launch {
                                if (isLiked) {
                                    viewModel.housingLike.dislikeHousing(room)
                                } else {
                                    viewModel.housingLike.likeHousing(room)
                                }
                            }
                        }
                )
            }

            if (photos?.isNotEmpty() == true) {
                AutoSlidingCarousel(
                    itemsCount = photos.size,
                    itemContent = { index ->
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(photos[index].photo)
                                .build(),
                            contentDescription = stringResource(id = R.string.slider),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.height(dimensionResource(id = R.dimen.slider_height))
                        )
                    }
                )
            }


            Text(
                text = room.title,
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.row_vert_margin),
                    top = dimensionResource(id = R.dimen.row_vert_margin)
                ),
                style = TextStyle(
                    color = colorResource(
                        id = R.color.black
                    ),
                    fontWeight = FontWeight.Bold
                )
            )
            Row(
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.row_vert_margin)
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.location_icon),
                    contentDescription = stringResource(id = R.string.location_label),
                    modifier = Modifier
                        .width(dimensionResource(id = R.dimen.tine_icon))
                        .height(dimensionResource(id = R.dimen.tine_icon)),
                    colorFilter = ColorFilter.tint(color = colorResource(id = R.color.black))
                )
                Text(
                    text = room.location,
                    style = TextStyle(
                        color = colorResource(id = R.color.black),
                    )
                )
            }

            Row(
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.row_vert_margin)
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stringResource(id = R.string.apartment_type_prefix),
                    style = TextStyle(
                        color = colorResource(id = R.color.black),
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    text = stringResource(id = apartmentOptions[room.housingType]!!),
                    style = TextStyle(
                        color = colorResource(id = R.color.black),
                    )
                )
            }

            Row(
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.row_vert_margin)),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stringResource(R.string.price_for_month_prefix),
                    style = TextStyle(
                        color = colorResource(id = R.color.black),
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    text = room.monthPrice.toString(),
                    style = TextStyle(
                        color = colorResource(id = R.color.black),
                    )
                )
            }
            Divider(
                color = colorResource(id = R.color.black), 
                thickness = dimensionResource(id = R.dimen.divider)
            )
            if (room.host != null) {
                Text(
                    text = stringResource(R.string.host),
                    style = TextStyle(
                        color = colorResource(id = R.color.black),
                        fontWeight = FontWeight.Bold
                    )
                )

                Row(
                    modifier = Modifier
                        .padding(start = dimensionResource(id = R.dimen.row_vert_margin))
                        .clickable {
                            navigator.navigate(UserDetailsScreenDestination(room.host))
                        },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(avatar)
                    
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(id = R.drawable.ordinary_client),
                        contentDescription = stringResource(id = R.string.avatar),
                        modifier = Modifier
                            .padding(
                                top = dimensionResource(id = R.dimen.row_vert_margin),
                                bottom = dimensionResource(id = R.dimen.row_vert_margin),
                                end = dimensionResource(id = R.dimen.avatar_end_margin)
                            )
                            .width(dimensionResource(id = R.dimen.avatar_size))
                            .height(dimensionResource(id = R.dimen.avatar_size))
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.avatar_size) / 2)),
                        contentScale = ContentScale.FillBounds
                    )
                    Text(
                        text = (room.host.firstName + " " + room.host.lastName),
                        style = TextStyle(
                            color = colorResource(id = R.color.primary_dark),
                        )
                    )
                    Image(
                        painter = painterResource(id = R.drawable.rating_icon),
                        contentDescription = stringResource(id = R.string.rating_label),
                        modifier = Modifier
                            .padding(
                                top = dimensionResource(id = R.dimen.row_vert_margin),
                                bottom = dimensionResource(id = R.dimen.row_vert_margin),
                                start = dimensionResource(id = R.dimen.rate_start_margin)
                            )
                            .width(dimensionResource(id = R.dimen.icon_size))
                            .height(dimensionResource(id = R.dimen.icon_size)),
                        colorFilter = ColorFilter.tint(color = colorResource(id = R.color.black))

                    )
                    Text(
                        text = (room.host.rating.toString()),
                        style = TextStyle(
                            color = colorResource(id = R.color.black),
                        )
                    )
                }

                Divider(
                    color = colorResource(id = R.color.black),
                    thickness = dimensionResource(id = R.dimen.divider)
                )
            }
            Text(
                text = stringResource(R.string.description),
                style = TextStyle(
                    color = colorResource(id = R.color.black),
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = room.description,
                style = TextStyle(
                    color = colorResource(id = R.color.text_secondary),
                )
            )

            Divider(
                color = colorResource(id = R.color.black),
                thickness = dimensionResource(id = R.dimen.divider)
            )
            Spacer(Modifier.padding(bottom = dimensionResource(R.dimen.column_bottom_margin)))
        }
    }
}