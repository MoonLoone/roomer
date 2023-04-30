package com.example.roomer.presentation.screens.room_details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.roomer.R
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.presentation.screens.destinations.HomeScreenDestination
import com.example.roomer.presentation.screens.destinations.MessengerScreenDestination
import com.example.roomer.presentation.ui_components.BackBtn
import com.example.roomer.presentation.ui_components.GreenButtonOutline
import com.example.roomer.presentation.ui_components.NoRippleInteractionSource
import com.example.roomer.utils.Constants.Options.apartmentOptions
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun RoomDetailsScreen(
    navigator: DestinationsNavigator,
    room: Room
) {
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
            top = dimensionResource(id = R.dimen.screen_top_margin),
            bottom = dimensionResource(id = R.dimen.screen_nav_bottom_margin)
        ),
        floatingActionButton = {
            Button(
                enabled = true,
                onClick = {},
                /*TODO: onClick = navigator.navigate(ChatScreenDestination(...)),*/
                modifier = Modifier
                    .padding(
                        start = 100.dp,
                        bottom = dimensionResource(id = R.dimen.result_button_bottom_margin)
                    )
                    .background(
                        color = colorResource(id = R.color.primary_dark),
                        shape = RoundedCornerShape(32.dp)
                    )
                    .height(64.dp)
                    .width(64.dp)
                    .clip(RoundedCornerShape(16.dp)),
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = colorResource(id = R.color.primary_dark),
                    contentColor = colorResource(id = R.color.white)
                ),
                interactionSource = NoRippleInteractionSource(),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.chatin),
                    contentDescription = "Chat",
                    colorFilter = ColorFilter.tint(color = colorResource(id = R.color.white)),
                    modifier = Modifier
                        .height(32.dp)
                        .width(32.dp)
                )
            }
        }

    ) {
        Column(
            modifier = Modifier
                .padding(
                    bottom = it.calculateBottomPadding() + dimensionResource(R.dimen.column_bottom_margin)
                )
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.list_elements_margin)
            )
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                BackBtn(
                    onBackNavigation = {
                            navigator.navigate(HomeScreenDestination)
                        }
                )
                Text(
                    text = stringResource(R.string.details),
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(
                        fontSize = integerResource(
                            id = R.integer.label_text
                        ).sp, color = Color.Black
                    ),
                    textAlign = TextAlign.Center
                )
                Image(
                    painter = if (room.isLiked) {
                        painterResource(id = R.drawable.room_like_in_icon)
                    } else {
                        painterResource(
                            id = R.drawable.room_like_icon
                        )
                    },
                    contentDescription = stringResource(id = R.string.like_icon),
                    modifier = Modifier
                        .padding(top = 10.dp, end = 10.dp)
                        .width(dimensionResource(id = R.dimen.big_icon))
                        .height(dimensionResource(id = R.dimen.big_icon))
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner_full)))
                        .clickable {
                            room.isLiked = !room.isLiked
                        }
                )
            }

            /*TODO:carousel*/

            AutoSlidingCarousel(
                itemsCount = photos!!.size,
                itemContent = { index ->
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(photos[index].photo)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.height(200.dp)
                    )
                }
            )

            Text(
                text = room.title,
                modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                style = TextStyle(
                    color = colorResource(
                        id = R.color.black
                    ),
                    //fontSize = nameTextSize,
                    fontWeight = FontWeight.Bold
                )
            )
            Row(
                modifier = Modifier.padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.location_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .width(dimensionResource(id = R.dimen.tine_icon))
                        .height(dimensionResource(id = R.dimen.tine_icon)),
                    colorFilter = ColorFilter.tint(color = colorResource(id = R.color.black))
                )
                Text(
                    text = room.location,
                    style = TextStyle(
                        color = colorResource(id = R.color.black),
                        //fontSize = locationTextSize
                    )
                )
            }

            Row(
                modifier = Modifier.padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Apartment type: ",
                    style = TextStyle(
                        color = colorResource(id = R.color.black),
                        //fontSize = locationTextSize
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    text = stringResource(id = apartmentOptions[room.housingType]!!),
                    style = TextStyle(
                        color = colorResource(id = R.color.black),
                        //fontSize = locationTextSize
                    )
                )
            }

            Row(
                modifier = Modifier.padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Price for month: ",
                    style = TextStyle(
                        color = colorResource(id = R.color.black),
                        //fontSize = locationTextSize
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    text = room.monthPrice.toString(),
                    style = TextStyle(
                        color = colorResource(id = R.color.black),
                        //fontSize = locationTextSize
                    )
                )
            }
            Divider(color = colorResource(id = R.color.black), thickness = 1.dp)
            Text(
                text = "Host",
                style = TextStyle(
                    color = colorResource(id = R.color.black),
                    fontWeight = FontWeight.Bold
                    //fontSize = locationTextSize
                )
            )
            Row(
                modifier = Modifier.padding(start = 10.dp)
                    /*TODO: onClick -> RoommateDetails*/,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(avatar)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(id = R.drawable.ordinary_client),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp, end = 16.dp)
                        .width(40.dp)
                        .height(40.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .clickable {
                        },
                    contentScale = ContentScale.FillBounds
                )
                Text(
                    text = (room.host?.firstName + " " + room.host?.lastName),
                    style = TextStyle(
                        color = colorResource(id = R.color.primary_dark),
                        //fontSize = locationTextSize
                    )
                )
                Image(
                    painter = painterResource(id = R.drawable.rating_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp, start = 40.dp)
                        .width(20.dp)
                        .height(20.dp),
                    colorFilter = ColorFilter.tint(color = colorResource(id = R.color.black))

                )
                Text(
                    text = (room.host?.rating.toString()),
                    style = TextStyle(
                        color = colorResource(id = R.color.black),
                        //fontSize = locationTextSize
                    )
                )
            }

            Divider(color = colorResource(id = R.color.black), thickness = 1.dp)

            Text(
                text = "Description",
                style = TextStyle(
                    color = colorResource(id = R.color.black),
                    fontWeight = FontWeight.Bold
                    //fontSize = locationTextSize
                )
            )

            Text(
                text = room.description,
                style = TextStyle(
                    color = colorResource(id = R.color.text_secondary),
                    //fontSize = locationTextSize
                )
            )

            Divider(color = colorResource(id = R.color.black), thickness = 1.dp)

        }
    }
}