package com.example.roomer.presentation.ui_components

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.roomer.R
import com.example.roomer.domain.model.comment.UserReview
import com.example.roomer.domain.model.entities.Message
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.User
import com.example.roomer.domain.model.login_sign_up.InterestModel
import com.example.roomer.presentation.screens.entrance.signup.habits_screen.HabitTileModel
import com.example.roomer.utils.Constants
import com.example.roomer.utils.UtilsFunctions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun ProfileContentLine(text: String, iconId: Int, onNavigateToFriends: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.profile_line_height))
            .clickable(onClick = { onNavigateToFriends.invoke() }),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = text,
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.ordinary_icon))
                .width(dimensionResource(id = R.dimen.ordinary_icon))
                .align(Alignment.CenterVertically),
            contentScale = ContentScale.Crop
        )
        Text(
            fontSize = integerResource(id = R.integer.primary_text).sp,
            text = text,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
    Divider(
        color = Color.Black,
        modifier = Modifier.padding(
            top = dimensionResource(id = R.dimen.divider_top_padding),
            bottom = dimensionResource(
                id = R.dimen.screen_bottom_margin
            )
        )
    )
}

@Composable
fun ChatItem(
    message: Message,
    unreadMessages: Int = 0,
    navigateTo: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable { navigateTo.invoke() }
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.chat_row_height)),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(message.recipient.avatar)
                .crossfade(true)
                .build(),
            placeholder = painterResource(id = R.drawable.ordinary_client),
            contentDescription = stringResource(R.string.user_avatar_content_description),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(dimensionResource(id = R.dimen.small_avatar_image))
                .height(dimensionResource(id = R.dimen.small_avatar_image))
                .clip(CircleShape),
            alignment = Center
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = UtilsFunctions.trimString(
                        message.recipient.firstName + " " + message.recipient.lastName,
                        Constants.Chat.CHAT_USERNAME_MAX_LENGTH
                    ),
                    fontSize = integerResource(id = R.integer.primary_text).sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = message.dateTime,
                    modifier = Modifier.padding(start = 8.dp),
                    style = TextStyle(
                        color = colorResource(id = R.color.text_secondary),
                        fontSize = integerResource(id = R.integer.primary_text).sp,
                        textAlign = TextAlign.End
                    )
                )
            }
            Text(
                text = UtilsFunctions.trimString(
                    message.text,
                    Constants.Chat.MESSENGER_TEXT_MAX_SIZE
                ),
                style = TextStyle(
                    color = colorResource(id = R.color.text_secondary),
                    fontSize = integerResource(id = R.integer.primary_text).sp
                ),
                maxLines = 1
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                if (unreadMessages > 0) {
                    Text(
                        text =
                        when (unreadMessages) {
                            in 1..99 -> unreadMessages.toString()
                            else -> "99+"
                        },
                        modifier = Modifier
                            .width(48.dp)
                            .height(20.dp)
                            .background(
                                color = colorResource(
                                    id = R.color.primary
                                ),
                                shape = RoundedCornerShape(
                                    dimensionResource(id = R.dimen.rounded_corner_ordinary)
                                )
                            ),
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = integerResource(id = R.integer.primary_text).sp,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        }
    }
    Divider(
        color = Color.Black,
        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.divider_top_padding))
    )
}

@Composable
fun Message(isUserMessage: Boolean, text: String, date: String) {
    if (isUserMessage) {
        DonorMessage(text, date)
    } else {
        RecipientMessage(text, date)
    }
}

@Composable
fun RecipientMessage(text: String, date: String) {
    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .border(
                    dimensionResource(id = R.dimen.ordinary_border),
                    Color.Black,
                    RoundedCornerShape(
                        topEnd = dimensionResource(id = R.dimen.rounded_corner_ordinary),
                        topStart = dimensionResource(id = R.dimen.rounded_corner_ordinary),
                        bottomEnd = dimensionResource(id = R.dimen.rounded_corner_ordinary)
                    )
                )
                .widthIn(
                    min = dimensionResource(id = R.dimen.message_min_width),
                    max = dimensionResource(id = R.dimen.message_max_width)
                )
                .height(IntrinsicSize.Max)
                .background(
                    colorResource(id = R.color.secondary_color),
                    RoundedCornerShape(
                        topEnd = dimensionResource(id = R.dimen.rounded_corner_ordinary),
                        topStart = dimensionResource(id = R.dimen.rounded_corner_ordinary),
                        bottomEnd = dimensionResource(id = R.dimen.rounded_corner_ordinary)
                    )
                )
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = text,
                modifier = Modifier.weight(6f),
                style = TextStyle(
                    color = colorResource(id = R.color.black),
                    fontSize = integerResource(
                        id = R.integer.primary_text
                    ).sp
                )
            )
            Column(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    textAlign = TextAlign.End,
                    text = date,
                    style = TextStyle(
                        color = colorResource(id = R.color.black),
                        fontSize = integerResource(
                            id = R.integer.secondary_text
                        ).sp
                    )
                )
                Image(
                    painter = painterResource(id = R.drawable.checked_messages_icon),
                    contentDescription = stringResource(id = R.string.message_checked_description),
                    alignment = BottomEnd
                )
            }
        }
    }
}

@Composable
fun DonorMessage(text: String, date: String) {
    Column(horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .padding(start = 40.dp, top = 16.dp)
                .border(
                    dimensionResource(id = R.dimen.ordinary_border),
                    Color.Black,
                    RoundedCornerShape(
                        bottomStart = dimensionResource(id = R.dimen.rounded_corner_ordinary),
                        topStart = dimensionResource(id = R.dimen.rounded_corner_ordinary),
                        bottomEnd = dimensionResource(id = R.dimen.rounded_corner_ordinary)
                    )
                )
                .widthIn(
                    min = dimensionResource(id = R.dimen.message_min_width),
                    max = dimensionResource(id = R.dimen.message_max_width)
                )
                .height(IntrinsicSize.Max)
                .background(
                    colorResource(id = R.color.primary),
                    RoundedCornerShape(
                        bottomStart = dimensionResource(id = R.dimen.rounded_corner_ordinary),
                        topStart = dimensionResource(id = R.dimen.rounded_corner_ordinary),
                        bottomEnd = dimensionResource(id = R.dimen.rounded_corner_ordinary)
                    )
                )
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = text,
                modifier = Modifier.weight(6f),
                style = TextStyle(
                    color = colorResource(id = R.color.black),
                    fontSize = integerResource(
                        id = R.integer.primary_text
                    ).sp
                )
            )
            Column(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    textAlign = TextAlign.End,
                    text = date,
                    style = TextStyle(
                        color = colorResource(id = R.color.black),
                        fontSize = integerResource(
                            id = R.integer.secondary_text
                        ).sp
                    )
                )
                Image(
                    painter = painterResource(id = R.drawable.checked_messages_icon),
                    contentDescription = stringResource(id = R.string.message_checked_description),
                    alignment = BottomEnd
                )
            }
        }
    }
}

@Composable
fun UserCard(recommendedRoommate: User, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .height(148.dp)
            .width(100.dp)
            .background(
                color = colorResource(id = R.color.primary),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(recommendedRoommate.avatar)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ordinary_user),
            contentDescription = recommendedRoommate.firstName + recommendedRoommate.lastName,
            modifier = Modifier
                .fillMaxWidth()
                .height(92.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = dimensionResource(id = R.dimen.card_small_rounded_corner),
                        topEnd = dimensionResource(id = R.dimen.card_small_rounded_corner)
                    )
                ),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 6.dp, start = 10.dp, end = 10.dp, bottom = 7.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = UtilsFunctions.trimString(
                    recommendedRoommate.firstName + " " + recommendedRoommate.lastName,
                    Constants.USER_CARD_MAX_NAME
                ),
                style = TextStyle(
                    color = Color.Black,
                    fontSize = integerResource(id = R.integer.secondary_text).sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.rating_icon),
                    contentDescription = stringResource(R.string.rate_icon),
                    modifier = Modifier
                        .width(dimensionResource(id = R.dimen.small_icon))
                        .height(dimensionResource(id = R.dimen.small_icon))
                )
                Text(
                    text = recommendedRoommate.rating.toString(),
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = integerResource(id = R.integer.secondary_text).sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

@Composable
fun RoomCard(
    recommendedRoom: Room,
    isMiniVersion: Boolean,
    onClick: () -> Unit
) {
    val cardWidth = if (isMiniVersion) 240.dp else 332.dp
    val cardHeight = if (isMiniVersion) 172.dp else 256.dp
    val imageHeight = if (isMiniVersion) 112.dp else 172.dp
    val nameTextSize = if (isMiniVersion) 16.sp else 20.sp
    val locationTextSize = if (isMiniVersion) 12.sp else 14.sp
    val title = recommendedRoom.title.substring(0, recommendedRoom.title.length.coerceAtMost(16))
    val location = recommendedRoom.location.substring(
        0,
        recommendedRoom.location.length.coerceAtMost(32)
    )
    Column(
        modifier = Modifier
            .width(cardWidth)
            .height(cardHeight)
            .background(
                color = colorResource(id = R.color.primary_dark),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() }
    ) {
        val photo = if (recommendedRoom.fileContent?.isNotEmpty() == true) {
            recommendedRoom.fileContent.first().photo
        } else {
            null
        }
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(photo)
                .crossfade(true)
                .build(),
            placeholder = painterResource(id = R.drawable.ordinary_room),
            contentDescription = stringResource(id = R.string.room_image_description),
            modifier = Modifier
                .width(cardWidth)
                .height(imageHeight)
                .clip(
                    RoundedCornerShape(
                        topStart = dimensionResource(id = R.dimen.card_small_rounded_corner),
                        topEnd = dimensionResource(id = R.dimen.card_small_rounded_corner)
                    )
                ),
            contentScale = ContentScale.Crop
        )
        Text(
            text = UtilsFunctions.trimString(title, Constants.ROOM_CARD_MAX_NAME),
            modifier = Modifier.padding(start = 16.dp, top = if (isMiniVersion) 12.dp else 16.dp),
            style = TextStyle(
                color = colorResource(
                    id = R.color.secondary_color
                ),
                fontSize = nameTextSize,
                fontWeight = FontWeight.Bold
            )
        )
        Row(
            modifier = Modifier.padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.location_icon),
                contentDescription = stringResource(id = R.string.location_icon),
                modifier = Modifier
                    .width(dimensionResource(id = R.dimen.tine_icon))
                    .height(dimensionResource(id = R.dimen.tine_icon)),
                colorFilter = ColorFilter.tint(color = colorResource(id = R.color.secondary_color))
            )
            Text(
                text = UtilsFunctions.trimString(location, Constants.ROOM_CARD_MAX_LOCATION),
                style = TextStyle(
                    color = colorResource(id = R.color.secondary_color),
                    fontSize = locationTextSize
                )
            )
        }
    }
}

@Composable
fun PostCard(room: Room, onEditClick: () -> Unit, onRemoveClick: () -> Unit) {
    val cardWidth = 332.dp
    val cardHeight = 222.dp
    val imageHeight = 140.dp
    val nameTextSize = 20.sp
    val locationTextSize = 14.sp
    val title = room.title.substring(0, room.title.length.coerceAtMost(16))
    val location = room.location.substring(
        0,
        room.location.length.coerceAtMost(32)
    )
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .width(cardWidth)
            .height(cardHeight)
            .background(
                color = colorResource(id = R.color.primary_dark),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.TopEnd)
        ) {
            DropdownMenu(
                modifier = Modifier.align(Alignment.TopEnd),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            color = colorResource(R.color.primary_dark),
                            text = stringResource(R.string.edit_label)
                        )
                    },
                    onClick = {
                        onEditClick()
                        expanded = false
                    }
                )
                Divider()
                DropdownMenuItem(
                    text = {
                        Text(
                            color = colorResource(R.color.red),
                            text = stringResource(R.string.remove_label)
                        )
                    },
                    onClick = {
                        onRemoveClick()
                        expanded = false
                    }
                )
            }
        }
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight)
            ) {
                if (room.fileContent?.isNotEmpty() == true) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(
                                room.fileContent?.first()?.photo ?: ""
                            )
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(id = R.drawable.ordinary_room),
                        contentDescription = stringResource(id = R.string.room_image_description),
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(
                                RoundedCornerShape(
                                    topStart = 16.dp,
                                    topEnd = 16.dp
                                )
                            ),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Text(
                text = title,
                modifier = Modifier.padding(
                    start = 10.dp,
                    top = 10.dp
                ),
                style = TextStyle(
                    color = colorResource(
                        id = R.color.secondary_color
                    ),
                    fontSize = nameTextSize,
                    fontWeight = FontWeight.Bold
                )
            )
            Row(
                modifier = Modifier.padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.location_icon),
                    contentDescription = stringResource(id = R.string.location_icon),
                    modifier = Modifier
                        .width(dimensionResource(id = R.dimen.tine_icon))
                        .height(dimensionResource(id = R.dimen.tine_icon)),
                    colorFilter = ColorFilter.tint(
                        color = colorResource(
                            id = R.color.secondary_color
                        )
                    )
                )
                Text(
                    text = location,
                    style = TextStyle(
                        color = colorResource(id = R.color.secondary_color),
                        fontSize = locationTextSize
                    )
                )
            }
        }
        Image(
            painter = painterResource(id = R.drawable.settings_secondary_icon),
            contentDescription = stringResource(id = R.string.like_icon),
            modifier = Modifier
                .padding(bottom = 24.dp, end = 24.dp)
                .align(BottomEnd)
                .width(dimensionResource(id = R.dimen.big_icon))
                .height(dimensionResource(id = R.dimen.big_icon))
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner_full)))
                .clickable {
                    expanded = true
                }
        )
    }
}

@Composable
fun SearchField(
    navigateToFilters: () -> Unit,
    paddingValues: PaddingValues = PaddingValues(top = 16.dp)
) {
    var searcherText by remember {
        mutableStateOf(TextFieldValue(""))
    }
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
            .height(56.dp)
            .border(
                width = 2.dp,
                shape = RoundedCornerShape(4.dp),
                color = colorResource(id = R.color.primary_dark)
            )
            .clickable {
                navigateToFilters()
            },
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = integerResource(id = R.integer.primary_text).sp
        ),
        enabled = false,
        value = searcherText,
        onValueChange = {
            if (it.text.length > 100) {
                searcherText = it
            }
        },
        label = {
            Text(
                text = stringResource(id = R.string.search_placeholder),
                modifier = Modifier.padding(bottom = 24.dp),
                style = TextStyle(
                    color = colorResource(id = R.color.primary_dark),
                    fontSize = integerResource(id = R.integer.primary_text).sp
                )
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.loupe_icon),
                contentDescription = stringResource(id = R.string.search_icon_description),
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.ordinary_icon))
                    .width(dimensionResource(id = R.dimen.ordinary_icon))
            )
        },
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.search_filter_icon),
                contentDescription = stringResource(id = R.string.search_placeholder),
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.ordinary_icon))
                    .width(dimensionResource(id = R.dimen.ordinary_icon))
                    .clickable {
                        navigateToFilters.invoke()
                    }
            )
        },
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White)
    )
}

@Composable
fun BackBtn(onBackNavigation: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.back_btn),
        modifier = Modifier
            .height(40.dp)
            .width(40.dp)
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner_full)))
            .clickable {
                onBackNavigation.invoke()
            },
        contentDescription = stringResource(id = R.string.back_button_label)
    )
}

@Composable
fun GreenButtonPrimary(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        enabled = enabled,
        onClick = onClick,
        modifier = modifier,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(id = R.color.primary_dark),
            contentColor = colorResource(id = R.color.secondary_color)
        )
    ) {
        androidx.compose.material.Text(
            text = text,
            fontSize = integerResource(id = R.integer.button_outline_font_size).sp
        )
    }
}

@Composable
fun GreenButtonPrimaryIconed(
    text: String,
    modifier: Modifier = Modifier,
    trailingIconPainterId: Int,
    trailingIconDescriptionId: Int,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        enabled = enabled,
        onClick = onClick,
        modifier = modifier,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(id = R.color.primary_dark),
            contentColor = colorResource(id = R.color.secondary_color)
        ),
        interactionSource = NoRippleInteractionSource()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(dimensionResource(id = R.dimen.small_icon)),
                painter = painterResource(id = trailingIconPainterId),
                contentDescription = stringResource(trailingIconDescriptionId),
                tint = colorResource(id = R.color.secondary_color)
            )
            androidx.compose.material.Text(
                text = text,
                fontSize = integerResource(id = R.integer.button_outline_font_size).sp
            )
        }
    }
}

@Composable
fun RedButtonPrimaryIconed(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
    trailingIcon: ImageVector
) {
    Button(
        enabled = enabled,
        onClick = onClick,
        modifier = modifier,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(id = R.color.red),
            contentColor = colorResource(id = R.color.secondary_color)
        ),
        interactionSource = NoRippleInteractionSource()
    ) {
        Icon(
            trailingIcon,
            stringResource(R.string.none_content_description),
            tint = colorResource(id = R.color.secondary_color)
        )
        androidx.compose.material.Text(
            text = text,
            Modifier.padding(start = 4.dp)
        )
    }
}

@Composable
fun GreenButtonOutlineIconed(
    text: String,
    modifier: Modifier = Modifier,
    trailingIconPainterId: Int,
    trailingIconDescriptionId: Int,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = CircleShape,
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.White,
            contentColor = colorResource(id = R.color.primary_dark)
        ),
        border = BorderStroke(
            dimensionResource(id = R.dimen.ordinary_border),
            color = colorResource(id = R.color.text_secondary)
        ),
        enabled = enabled,
        interactionSource = NoRippleInteractionSource()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painterResource(id = trailingIconPainterId),
                stringResource(id = trailingIconDescriptionId),
                tint = colorResource(id = R.color.primary_dark),
                modifier = Modifier.size(dimensionResource(id = R.dimen.small_icon))
            )
            androidx.compose.material.Text(
                text = text,
                fontSize = integerResource(id = R.integer.button_outline_font_size).sp
            )
        }
    }
}

@Composable
fun GreenButtonOutline(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        enabled = enabled,
        onClick = onClick,
        modifier = modifier,
        shape = CircleShape,
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.White,
            contentColor = colorResource(id = R.color.primary_dark)
        ),
        border = BorderStroke(
            dimensionResource(id = R.dimen.ordinary_border),
            color = colorResource(id = R.color.text_secondary)
        ),
        interactionSource = NoRippleInteractionSource()
    ) {
        androidx.compose.material.Text(
            text = text,
            fontSize = integerResource(id = R.integer.button_outline_font_size).sp
        )
    }
}

@Composable
fun ButtonsRow(
    label: String,
    values: List<String>,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.column_elements_small_margin)
        )
    ) {
        androidx.compose.material.Text(
            text = label,
            fontSize = integerResource(id = R.integer.primary_text).sp,
            color = Color.Black,
            textAlign = TextAlign.End,
            fontWeight = FontWeight.Medium
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (item in values) {
                if (item == value) {
                    GreenButtonPrimary(text = item, enabled = enabled) {}
                } else {
                    GreenButtonOutline(text = item, enabled = enabled) {
                        onValueChange(item)
                    }
                }
            }
        }
    }
}

@Composable
fun ButtonsRowMapped(
    label: String,
    values: Map<String, String>,
    value: String, // There gonna be keys
    onValueChange: (String) -> Unit,
    enabled: Boolean = true
) {
    val bundledMap =
        UtilsFunctions.bundleMapItemsToFitInOneLine(values, Constants.SPACE_IN_BUTTONS_ROW)
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.column_elements_small_margin)
        )
    ) {
        androidx.compose.material.Text(
            text = label,
            fontSize = integerResource(id = R.integer.primary_text).sp,
            color = Color.Black,
            textAlign = TextAlign.End,
            fontWeight = FontWeight.Medium
        )
        bundledMap.forEach {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    dimensionResource(id = R.dimen.list_elements_margin)
                )
            ) {
                for (item in it) {
                    if (item.key == value) {
                        GreenButtonPrimary(text = item.value, enabled = enabled) {}
                    } else {
                        GreenButtonOutline(text = item.value, enabled = enabled) {
                            onValueChange(item.key)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfilePicture(
    enabled: Boolean = true,
    bitmapValue: Bitmap?,
    onBitmapValueChange: (Bitmap?) -> Unit
) {
    val imageUri = rememberSaveable {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri.value = uri
        imageUri.value?.let {
            if (Build.VERSION.SDK_INT < 28) {
                onBitmapValueChange(
                    MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                )
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                onBitmapValueChange(ImageDecoder.decodeBitmap(source))
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val imageModifier = Modifier
            .align(Alignment.CenterHorizontally)
            .width(152.dp)
            .height(152.dp)
            .clip(CircleShape)
            .border(
                dimensionResource(id = R.dimen.ordinary_border),
                colorResource(id = R.color.primary_dark),
                CircleShape
            )
            .clickable {
                if (enabled) {
                    launcher.launch("image/*")
                    imageUri.value?.let {
                        if (Build.VERSION.SDK_INT < 28) {
                            onBitmapValueChange(
                                MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                            )
                        } else {
                            val source = ImageDecoder.createSource(context.contentResolver, it)
                            onBitmapValueChange(ImageDecoder.decodeBitmap(source))
                        }
                    }
                }
            }
        bitmapValue?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = stringResource(R.string.email_placeholder),
                modifier = imageModifier,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
        } ?: Image(
            modifier = imageModifier,
            painter = painterResource(id = R.drawable.usual_client),
            contentDescription = stringResource(R.string.email_placeholder),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
        Icon(
            Icons.Filled.PhotoCamera,
            contentDescription = stringResource(R.string.upload_photo_content_description),
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.ordinary_icon))
                .width(dimensionResource(id = R.dimen.ordinary_icon))
                .offset(50.dp, (-25).dp)
                .background(Color.White, shape = CircleShape)
        )
    }
}

@Composable
fun HousingPhotosComponent(
    enabled: Boolean = true,
    bitmapListValue: MutableList<Bitmap>,
    onBitmapListValueChange: (MutableList<Bitmap>?) -> Unit
) {
    val imageUri = rememberSaveable {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri.value = uri
        imageUri.value?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmapListValue.add(MediaStore.Images.Media.getBitmap(context.contentResolver, it))
                onBitmapListValueChange(bitmapListValue)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                bitmapListValue.add(ImageDecoder.decodeBitmap(source))
                onBitmapListValueChange(bitmapListValue)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.housing_photos),
                style = TextStyle(
                    color = Color.Black,
                    fontSize = integerResource(R.integer.housing_component_font_size).sp,
                    fontWeight = FontWeight.Bold
                )
            )
            if (bitmapListValue.isEmpty()) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(dimensionResource(R.dimen.housing_component_default_padding_2)),
                    text = stringResource(R.string.no_images_label),
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = integerResource(R.integer.housing_component_font_size).sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            } else {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(R.dimen.housing_component_lazy_row))
                        .padding(
                            top = dimensionResource(
                                R.dimen.housing_component_default_padding
                            )
                        ),
                    horizontalArrangement = Arrangement.spacedBy(
                        dimensionResource(R.dimen.housing_component_default_padding)
                    )
                ) {
                    items(bitmapListValue.size) { index ->
                        Image(
                            bitmap = bitmapListValue[index].asImageBitmap(),
                            contentDescription = stringResource(
                                id = R.string.room_image_description
                            ),
                            modifier = Modifier
                                .size(dimensionResource(R.dimen.housing_component_big_image))
                                .clip(
                                    RoundedCornerShape(
                                        dimensionResource(
                                            R.dimen.housing_component_rounded_corner_shape
                                        )
                                    )
                                ),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.housing_component_horizontal_arrangement)
            )
        ) {
            GreenButtonPrimaryIconed(
                modifier = Modifier.padding(
                    top = dimensionResource(R.dimen.housing_component_default_padding)
                ),
                text = stringResource(R.string.add_photos_button_label),
                enabled = true,
                trailingIconPainterId = R.drawable.add_photos_icon,
                trailingIconDescriptionId = R.string.add_photos_icon_description
            ) {
                if (enabled) {
                    launcher.launch("image/*")
                }
            }
            RedButtonPrimaryIconed(
                modifier = Modifier.padding(
                    top = dimensionResource(R.dimen.housing_component_default_padding)
                ),
                text = stringResource(R.string.remove_all_photos_button_label),
                trailingIcon = ImageVector.vectorResource(id = R.drawable.remove_icon),
                enabled = true,
                onClick = {
                    bitmapListValue.clear()
                }
            )
        }
    }
}

@Composable
fun FilterSelect(selectItemName: String, onNavigateToFriends: () -> Unit) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .height(40.dp)
                .width(88.dp)
                .border(
                    dimensionResource(id = R.dimen.ordinary_border),
                    color = colorResource(id = R.color.text_secondary),
                    RoundedCornerShape(
                        topStart = dimensionResource(id = R.dimen.rounded_corner_full),
                        bottomStart = dimensionResource(id = R.dimen.rounded_corner_full)
                    )
                )
                .background(
                    color = if (selectItemName == stringResource(id = R.string.room)) {
                        colorResource(
                            id = R.color.primary_dark
                        )
                    } else {
                        Color.White
                    },
                    RoundedCornerShape(
                        topStart = dimensionResource(id = R.dimen.rounded_corner_full),
                        bottomStart = dimensionResource(id = R.dimen.rounded_corner_full)
                    )
                )
                .clip(
                    RoundedCornerShape(
                        topStart = dimensionResource(id = R.dimen.rounded_corner_full),
                        bottomStart = dimensionResource(id = R.dimen.rounded_corner_full)
                    )
                )
                .clickable {
                    if (selectItemName == context.getString(R.string.roommate)) {
                        onNavigateToFriends.invoke()
                    }
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (selectItemName == stringResource(id = R.string.room)) {
                Image(
                    painter = painterResource(id = R.drawable.unchecked_messages_icon),
                    contentDescription = stringResource(id = R.string.room),
                    modifier = Modifier
                        .height(dimensionResource(id = R.dimen.small_icon))
                        .width(dimensionResource(id = R.dimen.small_icon)),
                    colorFilter = ColorFilter.tint(
                        colorResource(id = R.color.primary)
                    )
                )
            }
            Text(
                text = stringResource(id = R.string.room),
                style = TextStyle(
                    fontSize = integerResource(id = R.integer.primary_text).sp,
                    color = if (selectItemName == stringResource(id = R.string.room)) {
                        colorResource(
                            id = R.color.primary
                        )
                    } else {
                        colorResource(
                            id = R.color.text_secondary
                        )
                    }
                )
            )
        }
        Row(
            modifier = Modifier
                .height(40.dp)
                .width(100.dp)
                .border(
                    dimensionResource(id = R.dimen.ordinary_border),
                    color = colorResource(id = R.color.text_secondary),
                    RoundedCornerShape(
                        topEnd = dimensionResource(id = R.dimen.rounded_corner_full),
                        bottomEnd = dimensionResource(id = R.dimen.rounded_corner_full)
                    )
                )
                .background(
                    color = if (selectItemName == stringResource(id = R.string.roommate)) {
                        colorResource(
                            id = R.color.primary_dark
                        )
                    } else {
                        Color.White
                    },
                    RoundedCornerShape(
                        topEnd = dimensionResource(id = R.dimen.rounded_corner_full),
                        bottomEnd = dimensionResource(id = R.dimen.rounded_corner_full)
                    )
                )
                .clip(
                    RoundedCornerShape(
                        topEnd = dimensionResource(id = R.dimen.rounded_corner_full),
                        bottomEnd = dimensionResource(id = R.dimen.rounded_corner_full)
                    )
                )
                .clickable {
                    if (selectItemName == context.getString(R.string.room)) {
                        onNavigateToFriends.invoke()
                    }
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val color = if (selectItemName == stringResource(id = R.string.roommate)) {
                colorResource(
                    id = R.color.primary
                )
            } else {
                colorResource(
                    id = R.color.text_secondary
                )
            }
            Text(
                text = stringResource(id = R.string.roommate),
                style = TextStyle(
                    fontSize = integerResource(id = R.integer.primary_text).sp,
                    color = color
                )
            )
            if (selectItemName == stringResource(id = R.string.roommate)) {
                Image(
                    painter = painterResource(id = R.drawable.unchecked_messages_icon),
                    contentDescription = stringResource(id = R.string.roommate),
                    modifier = Modifier
                        .height(dimensionResource(id = R.dimen.small_icon))
                        .width(dimensionResource(id = R.dimen.small_icon)),
                    colorFilter = ColorFilter.tint(
                        colorResource(id = R.color.primary)
                    )
                )
            }
        }
    }
}

@Composable
fun UserCardResult(searchUser: User, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(148.dp)
            .background(
                color = colorResource(id = R.color.primary),
                shape = RoundedCornerShape(20.dp)
            )
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(searchUser.avatar)
                .crossfade(true)
                .build(),
            placeholder = painterResource(id = R.drawable.ordinary_user),
            contentDescription = searchUser.firstName,
            modifier = Modifier
                .fillMaxHeight()
                .width(104.dp),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp, top = 8.dp, end = 12.dp, bottom = 8.dp),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.column_elements_small_margin)
            )
        ) {
            Text(
                text = UtilsFunctions.trimString(
                    searchUser.firstName + " " + searchUser.lastName,
                    Constants.Follows.SMALL_USER_NAME
                ),
                style = TextStyle(
                    fontSize = integerResource(id = R.integer.label_text).sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.location_icon),
                    contentDescription = stringResource(id = R.string.location_icon),
                    modifier = Modifier
                        .width(dimensionResource(id = R.dimen.small_icon))
                        .height(dimensionResource(id = R.dimen.small_icon))
                        .align(Alignment.CenterVertically)
                )
                Text(
                    text = searchUser.city ?: "",
                    style = TextStyle(
                        fontSize = integerResource(id = R.integer.primary_text).sp,
                        color = Color.Black
                    )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.status),
                    style = TextStyle(
                        fontSize = integerResource(id = R.integer.primary_text).sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )
                Text(
                    stringResource(R.string.occasionally),
                    style = TextStyle(
                        fontSize = integerResource(id = R.integer.primary_text).sp,
                        color = Color.Black
                    ),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            ) {
                Text(
                    text = stringResource(R.string.rating_and_colon),
                    style = TextStyle(
                        fontSize = integerResource(id = R.integer.primary_text).sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )
                Text(
                    text = searchUser.rating.toString(),
                    style = TextStyle(
                        fontSize = integerResource(id = R.integer.primary_text).sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ),
                    modifier = Modifier.padding(start = 8.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.rating_icon),
                    contentDescription = stringResource(R.string.rating_star_content_description),
                    modifier = Modifier
                        .height(dimensionResource(id = R.dimen.small_icon))
                        .width(dimensionResource(id = R.dimen.small_icon))
                )
            }
        }
    }
}

@Composable
fun InterestsButtons(
    label: String,
    values: List<InterestModel>,
    selectedItems: List<InterestModel>,
    onSelectedChange: (List<InterestModel>) -> Unit,
    chooseLimit: Int = 10
) {
    val chunkedValues = values.chunked(3)
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.list_elements_margin)
        ),
        horizontalAlignment = Alignment.Start
    ) {
        androidx.compose.material.Text(
            text = label,
            fontSize = integerResource(id = R.integer.primary_text).sp,
            color = Color.Black,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Medium
        )
        for (row in chunkedValues) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (value in row) {
                    if (value in selectedItems) {
                        GreenButtonPrimary(text = value.interest) {
                            selectedItems.minus(value).let(onSelectedChange)
                        }
                    } else {
                        GreenButtonOutline(text = value.interest) {
                            if (selectedItems.size < chooseLimit) {
                                selectedItems.plus(value).let(onSelectedChange)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SimpleAlertDialog(
    title: String,
    text: String,
    buttonText: String = stringResource(R.string.got_you),
    confirmDismissOnClick: () -> Unit
) {
    AlertDialog(
        containerColor = Color.White,
        onDismissRequest = confirmDismissOnClick,
        title = {
            Text(text = title)
        },
        titleContentColor = Color.Red,
        text = {
            Text(text = text)
        },
        confirmButton = {
            GreenButtonOutline(
                text = buttonText,
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = confirmDismissOnClick
            )
        }
    )
}

/**
 * The basic confirm dialog.
 * Asks user to approve requested operation (Confirm / Dismiss).
 *
 * @param text the text of the confirm dialog to be displayed.
 * @param confirmButtonText the text of the confirm button. Default is [R.string.confirm_button_label].
 * @param dismissButtonText the text of the dismiss button. Default is [R.string.dismiss_button_label].
 * @param confirmOnClick called when the user tries to confirm the operation.
 * @param dismissOnClick called when the user tries to dismiss the operation. Default is only to close the dialog.
 *
 * @author Andrey Karanik
 */
@Composable
fun BasicConfirmDialog(
    text: String,
    confirmButtonText: String = stringResource(R.string.confirm_button_label),
    dismissButtonText: String = stringResource(R.string.dismiss_button_label),
    confirmOnClick: () -> Unit,
    dismissOnClick: () -> Unit = {}
) {
    AlertDialog(
        containerColor = Color.White,
        titleContentColor = Color.Black,
        onDismissRequest = dismissOnClick,
        text = {
            Text(text = text)
        },
        dismissButton = {
            GreenButtonOutline(
                text = dismissButtonText,
                onClick = dismissOnClick
            )
        },
        confirmButton = {
            GreenButtonPrimary(
                text = confirmButtonText,
                onClick = confirmOnClick
            )
        }
    )
}

@Composable
fun HabitsTable(habitsList: List<HabitTileModel>) {
    val chunkSize = if (habitsList.size % 2 == 0) habitsList.size / 2 else habitsList.size / 2 + 1
    val habitsChunked = habitsList.chunked(chunkSize)
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        habitsChunked.forEach {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    dimensionResource(id = R.dimen.column_medium_margin)
                )
            ) {
                it.forEach { HabitTile(habit = it) }
            }
        }
    }
}

@Composable
fun HabitTile(habit: HabitTileModel) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.column_elements_small_margin)
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.ordinary_icon)),
            painter = painterResource(habit.painterId),
            contentDescription = stringResource(id = habit.iconDescriptionId),
            tint = colorResource(id = R.color.black)
        )
        Column {
            Text(
                text = stringResource(habit.habitKey),
                color = colorResource(id = R.color.primary_dark),
                fontSize = integerResource(
                    id = R.integer.medium_text
                ).sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = stringResource(habit.habitValue),
                color = colorResource(id = R.color.black),
                fontSize = integerResource(
                    id = R.integer.medium_text
                ).sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Composable
fun ExpandableText(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    fontStyle: FontStyle? = null,
    text: String,
    collapsedMaxLine: Int = Constants.EXP_TEXT_MINIMUM_TEXT_LINE,
    showMoreText: String = "... Show More",
    showMoreStyle: SpanStyle = SpanStyle(fontWeight = FontWeight.W500),
    showLessText: String = " Show Less",
    showLessStyle: SpanStyle = showMoreStyle,
    textAlign: TextAlign? = null
) {
    var isExpanded by remember { mutableStateOf(false) }
    var clickable by remember { mutableStateOf(false) }
    var lastCharIndex by remember { mutableStateOf(0) }
    Box(
        modifier = Modifier
            .clickable(clickable) {
                isExpanded = !isExpanded
            }
            .then(modifier)
    ) {
        Text(
            modifier = textModifier
                .fillMaxWidth()
                .animateContentSize(),
            text = buildAnnotatedString {
                if (clickable) {
                    if (isExpanded) {
                        append(text)
                        withStyle(style = showLessStyle) { append(showLessText) }
                    } else {
                        val adjustText = text.substring(startIndex = 0, endIndex = lastCharIndex)
                            .dropLast(showMoreText.length)
                            .dropLastWhile { Character.isWhitespace(it) || it == '.' }
                        append(adjustText)
                        withStyle(style = showMoreStyle) { append(showMoreText) }
                    }
                } else {
                    append(text)
                }
            },
            maxLines = if (isExpanded) Int.MAX_VALUE else collapsedMaxLine,
            fontStyle = fontStyle,
            onTextLayout = { textLayoutResult ->
                if (!isExpanded && textLayoutResult.hasVisualOverflow) {
                    clickable = true
                    lastCharIndex = textLayoutResult.getLineEnd(collapsedMaxLine - 1)
                }
            },
            style = style,
            textAlign = textAlign
        )
    }
}

@Composable
fun BasicHeaderBar(title: String, onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BackBtn {
            onBackClick()
        }
        Text(
            text = title,
            modifier = Modifier.fillMaxWidth(),
            fontSize = integerResource(id = R.integer.label_text).sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun CommentCard(userReview: UserReview) {
    val cardWidth = 332.dp
    val nameTextSize = 16.sp
    val commentTextSize = 14.sp
    Box(
        modifier = Modifier
            .width(cardWidth)
            .background(
                color = colorResource(id = R.color.secondary_color),
                shape = RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_ordinary))
            )
            .border(
                dimensionResource(R.dimen.ordinary_border),
                Color.Black,
                RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_ordinary))
            )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (userReview.isAnonymous) {
                    Icon(
                        painter = painterResource(id = R.drawable.tabler_spy_icon),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                } else {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(userReview.author.avatar)
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(R.drawable.ordinary_user),
                        contentDescription =
                        UtilsFunctions.trimString(
                            userReview.author.firstName + " " + userReview.author.lastName,
                            Constants.MAX_CHARS_IN_COMMENT_NAME
                        ),
                        modifier = Modifier
                            .size(dimensionResource(R.dimen.big_icon))
                            .clip(
                                CircleShape
                            ),
                        contentScale = ContentScale.Crop
                    )
                }
                Text(
                    modifier = Modifier.align(CenterVertically),
                    text = if (userReview.isAnonymous) {
                        stringResource(R.string.anonymous)
                    } else {
                        UtilsFunctions.trimString(
                            userReview.author.firstName + " " + userReview.author.lastName,
                            Constants.MAX_CHARS_IN_COMMENT_NAME
                        )
                    },
                    style = TextStyle(
                        color = colorResource(
                            id = R.color.black
                        ),
                        fontSize = nameTextSize,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
            Row {
                for (i in 0 until userReview.score) {
                    Icon(
                        painter = painterResource(id = R.drawable.small_filled_star_icon),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
                for (i in 0 until 5 - userReview.score) {
                    Icon(
                        painter = painterResource(id = R.drawable.small_outlined_star_icon),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
            }
            Text(
                text = userReview.comment,
                style = TextStyle(
                    color = colorResource(
                        id = R.color.black
                    ),
                    fontSize = commentTextSize,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

@Composable
fun FavouriteLikeButton(
    isLiked: Boolean,
    dislikeHousing: () -> Unit,
    likeHousing: () -> Unit,
    modifier: Modifier = Modifier
) {
    val imagePainter = if (isLiked) painterResource(id = R.drawable.room_like_in_icon)
    else painterResource(id = R.drawable.room_like_icon)
    val onClickReaction = if (isLiked) dislikeHousing else likeHousing
    Image(
        painter = imagePainter,
        contentDescription = stringResource(id = R.string.like_icon),
        modifier = modifier
            .padding(top = 10.dp, end = 10.dp)
            .width(dimensionResource(id = R.dimen.big_icon))
            .height(dimensionResource(id = R.dimen.big_icon))
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner_full)))
            .clickable {
                onClickReaction()
            },
        alignment = Alignment.TopEnd,
    )
}

class NoRippleInteractionSource : MutableInteractionSource {

    override val interactions: Flow<Interaction> = emptyFlow()

    override suspend fun emit(interaction: Interaction) {}

    override fun tryEmit(interaction: Interaction) = true
}
