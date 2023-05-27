package com.example.roomer.presentation.screens.shared_screens.room_details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.roomer.presentation.ui_components.FavouriteLikeButton
import com.example.roomer.presentation.ui_components.NoRippleInteractionSource
import com.example.roomer.utils.Constants.Options.apartmentOptions
import com.example.roomer.utils.NavbarManagement
import com.example.roomer.utils.UtilsFunctions
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun RoomDetailsScreen(
    navigator: DestinationsNavigator,
    room: Room,
    viewModel: RoomDetailsScreenViewModel = hiltViewModel()
) {
    NavbarManagement.hideNavbar()
    val state = viewModel.state.collectAsState().value
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = dimensionResource(id = R.dimen.screen_start_margin),
                end = dimensionResource(id = R.dimen.screen_end_margin)
            ),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.list_elements_margin)
            )
        ) {
            Header(onBackClick = { navigator.popBackStack() })

            if (photos?.isNotEmpty() == true) {
                Box {
                    AutoSlidingCarousel(
                        itemsCount = photos.size,
                        itemContent = { index ->
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(photos[index].photo)
                                    .build(),
                                contentDescription = stringResource(id = R.string.slider),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.height(
                                    dimensionResource(id = R.dimen.slider_height)
                                )
                            )
                        }
                    )
                    FavouriteLikeButton(
                        isLiked = state.isFavourite,
                        dislikeHousing = { viewModel.deleteFromFavourite(room) },
                        likeHousing = { viewModel.addToFavourite(room) },
                        modifier = Modifier.align(Alignment.TopEnd))
                }
            }
            Text(
                text = UtilsFunctions.trimString(room.title, 100),
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.row_vert_margin),
                    top = dimensionResource(id = R.dimen.row_vert_margin)
                ),
                style = TextStyle(
                    color = colorResource(
                        id = R.color.black
                    ),
                    fontWeight = FontWeight.Bold,
                    fontSize = integerResource(id = R.integer.big_text).sp
                )
            )

            Location(location = room.location)
            ApartmentType(type = room.housingType)
            Price(price = room.monthPrice.toString())
            if (room.host != null) {
                Host(
                    onClick = {
                        navigator.navigate(UserDetailsScreenDestination(room.host))
                    },
                    avatar = avatar,
                    name = room.host.firstName + " " + room.host.lastName,
                    rating = room.host.rating.toString()
                )
            }

            Description(description = room.description)

            Spacer(Modifier.padding(bottom = dimensionResource(R.dimen.column_bottom_margin)))
        }
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (room.host != null) {
                MessageFab {
                    navigator.navigate(ChatScreenDestination(room.host))
                }
            }
        }
    }
}

@Composable
private fun MessageFab(onClick: () -> Unit) {
    Button(
        onClick = onClick,
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
        interactionSource = NoRippleInteractionSource()
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

@Composable
private fun Header(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BackBtn {
            onBackClick()
        }
        Text(
            text = stringResource(R.string.details_header),
            fontSize = integerResource(id = R.integer.label_text).sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun Location(location: String) {
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
            text = location,
            style = TextStyle(
                color = colorResource(id = R.color.black)
            )
        )
    }
}

@Composable
private fun ApartmentType(type: String) {
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
            text = stringResource(id = apartmentOptions[type]!!),
            style = TextStyle(
                color = colorResource(id = R.color.black)
            )
        )
    }
}

@Composable
private fun Price(price: String) {
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
            text = price,
            style = TextStyle(
                color = colorResource(id = R.color.black)
            )
        )
    }
    Divider(
        color = colorResource(id = R.color.black),
        thickness = dimensionResource(id = R.dimen.divider)
    )
}

@Composable
private fun Host(
    onClick: () -> Unit,
    avatar: String?,
    name: String,
    rating: String
) {
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
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
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
            text = (name),
            style = TextStyle(
                color = colorResource(id = R.color.primary_dark)
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
            text = (rating),
            style = TextStyle(
                color = colorResource(id = R.color.black)
            )
        )
    }

    Divider(
        color = colorResource(id = R.color.black),
        thickness = dimensionResource(id = R.dimen.divider)
    )
}

@Composable
private fun Description(
    description: String
) {
    Text(
        text = stringResource(R.string.description),
        style = TextStyle(
            color = colorResource(id = R.color.black),
            fontWeight = FontWeight.Bold,
            fontSize = integerResource(id = R.integer.big_text).sp
        )
    )

    Text(
        text = description,
        style = TextStyle(
            color = colorResource(id = R.color.text_secondary)
        )
    )

    Divider(
        color = colorResource(id = R.color.black),
        thickness = dimensionResource(id = R.dimen.divider)
    )
}
