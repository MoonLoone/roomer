package com.example.roomer.ui_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.roomer.R
import com.example.roomer.models.MessageToList
import com.example.roomer.utils.NavbarItem

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
