package com.example.roomer.presentation.ui_components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.roomer.R
import com.example.roomer.models.MessageToList
import com.example.roomer.models.RecommendedRoom
import com.example.roomer.models.RecommendedRoommate
import com.example.roomer.utils.NavbarItem
import androidx.compose.material3.AlertDialog
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.roomer.domain.model.interests.InterestModel

@Composable
fun ProfileContentLine(text: String, iconId: Int, onNavigateToFriends: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable(onClick = { onNavigateToFriends.invoke() }),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = text,
            modifier = Modifier
                .height(24.dp)
                .width(24.dp)
                .align(Alignment.CenterVertically),
            contentScale = ContentScale.Crop,
        )
        Text(
            fontSize = integerResource(id = R.integer.primary_text_size).sp,
            text = text,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
    Divider(color = Color.Black, modifier = Modifier.padding(top = 4.dp, bottom = 16.dp))
}

@Composable
fun Navbar(navController: NavHostController, selectedNavbarItemName: String) {
    var selectedItem by remember {
        mutableStateOf(selectedNavbarItemName)
    }
    BottomNavigation(
        backgroundColor = colorResource(id = R.color.secondary_color),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        NavbarItem.values().map { it.name }.forEach { screen ->
            BottomNavigationItem(
                selected = (selectedItem == screen),
                modifier = Modifier
                    .width(80.dp)
                    .padding(
                        start = 4.dp,
                        end = 4.dp
                    ),
                onClick = {
                    selectedItem = screen
                    navController.navigate(screen)
                },
                icon = {
                    val item = NavbarItem.valueOf(screen)
                    if (selectedItem == screen) {
                        Column {
                            Box(
                                modifier = Modifier
                                    .width(64.dp)
                                    .height(32.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(colorResource(id = R.color.primary)),
                            ) {
                                Image(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .width(24.dp)
                                        .height(24.dp),
                                    painter = painterResource(id = item.iconSelected),
                                    contentDescription = item.description
                                )
                            }
                            Text(
                                text = screen,
                                fontSize = integerResource(id = R.integer.secondary_text_size).sp,
                                color = Color.Black,
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(top = 4.dp)
                            )
                        }
                    } else {
                        Column {
                            Box(
                                modifier = Modifier
                                    .width(32.dp)
                                    .height(32.dp)
                                    .align(Alignment.CenterHorizontally)
                            ) {
                                Image(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .width(24.dp)
                                        .height(24.dp),
                                    painter = painterResource(id = item.iconUnSelected),
                                    contentDescription = item.description
                                )
                            }
                            Text(
                                text = screen,
                                fontSize = integerResource(id = R.integer.secondary_text_size).sp,
                                color = colorResource(id = R.color.text_secondary),
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(top = 4.dp),
                            )
                        }
                    }
                })
        }
    }
}

@Composable
fun MessageItem(
    message: MessageToList,
) {
    Row(
        modifier = Modifier
            .clickable { message.navigateToMessage.invoke() }
            .fillMaxWidth()
            .height(64.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ordinary_client),
            contentDescription = "message_client_avatar",
            modifier = Modifier
                .width(56.dp)
                .height(56.dp)
                .padding(start = 8.dp, end = 16.dp, bottom = 8.dp, top = 8.dp),
            alignment = Alignment.Center
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = message.username,
                    fontSize = integerResource(id = R.integer.primary_text_size).sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Image(
                        painter = painterResource(id = if (message.isRead) R.drawable.checked_messages_icon else R.drawable.unchecked_messages_icon),
                        contentDescription = if (message.isRead) "Messages checked" else "Messages unchecked",
                        alignment = Alignment.Center,
                        modifier = Modifier
                            .width(18.dp)
                            .height(18.dp),
                    )
                    Text(
                        text = message.messageDate, style = TextStyle(
                            color = colorResource(id = R.color.text_secondary),
                            fontSize = 12.sp,
                            textAlign = TextAlign.End
                        )
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
            ) {
                Text(
                    text = message.messageCutText, style = TextStyle(
                        color = colorResource(id = R.color.text_secondary),
                        fontSize = 14.sp,
                    )
                )
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    if (message.unreadMessages > 0) {
                        Text(
                            text =
                            when (message.unreadMessages) {
                                in 1..999 -> message.unreadMessages.toString()
                                else -> "999+"
                            },
                            modifier = Modifier
                                .width(48.dp)
                                .height(20.dp)
                                .background(
                                    color = colorResource(
                                        id = R.color.primary
                                    ),
                                    shape = RoundedCornerShape(20.dp)
                                ),
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                            )
                        )
                    }
                }
            }
        }
    }
    Divider(
        color = Color.Black,
        modifier = Modifier.padding(top = 2.dp),
    )
}

@Composable
fun Message(isUserMessage: Boolean, text: String, data: String) {
    if (!isUserMessage) {
        Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .width(214.dp)
                    .border(
                        1.dp,
                        Color.Black,
                        RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp, bottomEnd = 16.dp)
                    )
                    .height(IntrinsicSize.Max)
                    .background(
                        colorResource(id = R.color.secondary_color),
                        RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp, bottomEnd = 16.dp)
                    )
            ) {
                Text(text = text, textAlign = TextAlign.Start, modifier = Modifier.padding(16.dp))
                Text(text = data, textAlign = TextAlign.End, modifier = Modifier.padding(16.dp))
            }
        }
    } else {
        Column(horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .padding(start = 40.dp, top = 16.dp)
                    .border(
                        1.dp,
                        Color.Black,
                        RoundedCornerShape(bottomStart = 16.dp, topStart = 16.dp, bottomEnd = 16.dp)
                    )
                    .width(214.dp)
                    .height(IntrinsicSize.Max)
                    .background(
                        colorResource(id = R.color.primary),
                        RoundedCornerShape(bottomStart = 16.dp, topStart = 16.dp, bottomEnd = 16.dp)
                    ),
                horizontalAlignment = Alignment.End
            ) {
                Text(text = text)
                Image(
                    painter = painterResource(id = R.drawable.checked_messages_icon),
                    contentDescription = "Checked message"
                )
                Text(text = data)
            }
        }
    }
}

@Composable
fun UserCard(recommendedRoommate: RecommendedRoommate) {
    Column(
        modifier = Modifier
            .height(148.dp)
            .width(100.dp)
            .background(
                color = colorResource(id = R.color.primary),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.ordinary_client),
            contentDescription = recommendedRoommate.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(92.dp),
            contentScale = ContentScale.Fit,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 6.dp, start = 10.dp, end = 10.dp, bottom = 7.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = recommendedRoommate.name,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Row() {
                Icon(
                    painter = painterResource(id = R.drawable.rating_icon),
                    contentDescription = "Rating icon",
                    modifier = Modifier
                        .width(integerResource(id = R.integer.ordinary_icon_size).dp)
                        .height(integerResource(id = R.integer.ordinary_icon_size).dp)
                )
                Text(
                    text = recommendedRoommate.rating.toString(),
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

@Composable
fun ApartmentCard(recommendedRoom: RecommendedRoom) {
    Column(
        modifier = Modifier
            .width(240.dp)
            .height(148.dp)
            .background(
                color = colorResource(id = R.color.primary_dark),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        var isLiked by remember {
            mutableStateOf(recommendedRoom.isLiked)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(92.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.ordinary_client),
                contentDescription = "Room image",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentScale = ContentScale.Fit,
            )
            Image(
                painter = if (isLiked) painterResource(id = R.drawable.room_like_in_icon) else painterResource(
                    id = R.drawable.room_like_icon
                ), contentDescription = "Like icon",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 10.dp, end = 10.dp)
                    .width(32.dp)
                    .height(32.dp)
                    .clickable {
                        isLiked = !isLiked
                    }
            )
        }
        Text(
            text = recommendedRoom.name,
            modifier = Modifier.padding(start = 10.dp, top = 4.dp),
            style = TextStyle(
                color = colorResource(
                    id = R.color.secondary_color
                ),
                fontSize = integerResource(id = R.integer.primary_text_size).sp,
                fontWeight = FontWeight.Bold,
            )
        )
        Row(
            modifier = Modifier.padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.location_icon),
                contentDescription = "Location icon",
                modifier = Modifier
                    .width(14.dp)
                    .height(14.dp),
                colorFilter = ColorFilter.tint(color = colorResource(id = R.color.secondary_color))
            )
            Text(
                text = recommendedRoom.location, style = TextStyle(
                    color = colorResource(id = R.color.secondary_color),
                    fontSize = 12.sp,
                )
            )
        }
    }
}

@Composable
fun SearchField() {
    var searcherText by remember {
        mutableStateOf(TextFieldValue(""))
    }
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .height(56.dp)
            .border(
                width = 2.dp,
                shape = RoundedCornerShape(4.dp),
                color = colorResource(id = R.color.primary_dark),
            ),
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = integerResource(id = R.integer.primary_text_size).sp,
        ),
        value = searcherText,
        onValueChange = {
            if (it.text.length > 100) {
                searcherText = it
            }
        },
        label = {
            Text(
                text = "Search for roommate or housing",
                modifier = Modifier.padding(bottom = 24.dp),
                style = TextStyle(
                    color = colorResource(id = R.color.primary_dark),
                    fontSize = 12.sp,
                ),
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.loupe_icon),
                contentDescription = "Search loupe",
                modifier = Modifier
                    .height(24.dp)
                    .width(24.dp),
            )
        },
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.search_filter_icon),
                contentDescription = "Searcher filters",
                modifier = Modifier
                    .height(24.dp)
                    .width(24.dp),
            )
        },
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White)
    )
}
@Composable
fun GreenButtonPrimary(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
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
        )
    }
}
@Composable
fun GreenButtonPrimaryIconed(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
    trailingIcon: ImageVector,
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


    ) {
        Icon(trailingIcon, "None", tint = colorResource(id = R.color.secondary_color))
        androidx.compose.material.Text(
            text = text,
        )
    }
}

@Composable
fun GreenButtonOutlineIconed(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    trailingIcon: ImageVector,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = CircleShape,
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.White,
            contentColor = colorResource(id = R.color.primary_dark)
        ),
        border = BorderStroke(1.dp, color = colorResource(id = R.color.text_secondary)),
        enabled = enabled
    ) {
        Icon(trailingIcon, "None", tint = colorResource(id = R.color.primary_dark))
        androidx.compose.material.Text(
            text = text,
        )
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
        border = BorderStroke(1.dp, color = colorResource(id = R.color.text_secondary))
    ) {
        androidx.compose.material.Text(
            text = text,
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
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        androidx.compose.material.Text(
            text = label,
            fontSize = integerResource(id = R.integer.primary_text_size).sp,
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

@Preview
@Composable
fun ProfilePicture() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(152.dp)
                .height(152.dp)
                .clickable {

                },
            painter = painterResource(id = R.drawable.usual_client),
            contentDescription = "Your avatar")
        Icon(
            Icons.Filled.PhotoCamera,
            contentDescription = "Upload photo",
            modifier = Modifier
                .height(24.dp)
                .width(24.dp)
                .offset(50.dp, (-25).dp)
                .background(Color.White, shape = CircleShape)
        )
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
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        androidx.compose.material.Text(
            text = label,
            fontSize = integerResource(id = R.integer.primary_text_size).sp,
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
                    if (value in selectedItems)
                        GreenButtonPrimary(text = value.interest) {
                            selectedItems.minus(value).let(onSelectedChange)
                        }
                    else
                        GreenButtonOutline(text = value.interest) {
                            if (selectedItems.size < chooseLimit)
                                selectedItems.plus(value).let(onSelectedChange)
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
    buttonText: String = "Got you!",
    confirmDismissOnClick: () -> Unit,
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