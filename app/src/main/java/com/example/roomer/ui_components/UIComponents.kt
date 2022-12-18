package com.example.roomer.ui_components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.TextField
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.roomer.R
import com.example.roomer.utils.NavbarItem
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.format.DateTimeFormatter

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
fun Navbar(navController: NavHostController) {
    var selectedItem by remember {
        mutableStateOf(NavbarItem.Home.name)
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
    userAvatarPath: String,
    messageDate: String,
    messageCutText: String,
    username: String,
    isRead: Boolean,
    unreadMessages: Int,
    navigateToMessage: () -> Unit,
) {
    Row(modifier = Modifier.clickable { navigateToMessage.invoke() }) {
        Image(
            painter = painterResource(id = R.drawable.ordinary_client),
            contentDescription = "message_client_avatar",
            modifier = Modifier
                .width(64.dp)
                .height(64.dp)
                .padding(start = 8.dp, end = 16.dp),
        )
        Column() {
            Row() {
                Text(
                    text = username,
                    fontSize = integerResource(id = R.integer.primary_text_size).sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
                Image(
                    painter = painterResource(id = if (isRead) R.drawable.checked_messages_icon else R.drawable.unchecked_messages_icon),
                    contentDescription = if (isRead) "Messages checked" else "Messages unchecked",
                    alignment = Alignment.TopEnd
                )
                Text(text = messageDate)
            }
            Row {
                Text(text = messageCutText, modifier = Modifier.padding(top = 8.dp, end = 12.dp))
                Text(
                    text =
                    when (unreadMessages) {
                        0 -> ""
                        in 1..999 -> unreadMessages.toString()
                        else -> ">1000"
                    }
                )

            }
        }
    }
}

@Composable
fun DateField(
    label: String = "Date field", paddingValues: PaddingValues = PaddingValues(top = 16.dp)
) {
    val dialogState = rememberMaterialDialogState()
    var textState by remember {
        mutableStateOf(TextFieldValue("11.12.2002"))
    }
    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    ) {
        datepicker { date ->
            val formattedDate = date.format(
                DateTimeFormatter.ofPattern("dd.MM.yyyy")
            )
            textState = TextFieldValue(formattedDate)
        }
    }
    Column(
        modifier = Modifier.padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            fontSize = integerResource(id = R.integer.primary_text_size).sp,
            color = Color.Black
        )
        Text(
            fontSize = integerResource(id = R.integer.primary_text_size).sp,
            color = colorResource(id = R.color.text_secondary),
            style = TextStyle(
                color = Color.Black,
                fontSize = integerResource(id = R.integer.primary_text_size).sp,
                textAlign = TextAlign.Start,
            ),
            text = textState.text,
            modifier = Modifier
                .clickable {
                    dialogState.show()
                }
                .fillMaxWidth()
                .height(56.dp)
                .background(colorResource(id = R.color.secondary_color))
        )
    }
}

@Composable
fun ScreenTextField(
    textHint: String, label: String = "", paddingValues: PaddingValues = PaddingValues(top = 16.dp),
) {
    var text by remember {
        mutableStateOf(TextFieldValue(""))
    }
    Column(
        modifier = Modifier.padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            fontSize = integerResource(id = R.integer.primary_text_size).sp,
            color = Color.Black
        )
        TextField(
            value = text,
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = integerResource(id = R.integer.primary_text_size).sp,
                textAlign = TextAlign.Start,
            ),
            placeholder = {
                Text(
                    text = textHint,
                    fontSize = integerResource(id = R.integer.primary_text_size).sp,
                    color = colorResource(id = R.color.text_secondary)
                )
            },
            onValueChange = { text = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(colorResource(id = R.color.secondary_color))
        )
    }
}

@Composable
fun SelectSex(paddingValues: PaddingValues = PaddingValues(top = 16.dp)) {
    Column(
        modifier = Modifier.padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        var isMaleSelected by remember {
            mutableStateOf(true)
        }
        Text(
            text = "Sex",
            fontSize = integerResource(id = R.integer.primary_text_size).sp,
            color = Color.Black
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier
                    .height(40.dp)
                    .width(98.dp)
                    .border(
                        1.dp,
                        if (isMaleSelected) colorResource(id = R.color.primary_dark) else Color.Black,
                        RoundedCornerShape(100.dp),
                    )
                    .background(
                        if (isMaleSelected) colorResource(id = R.color.primary_dark) else Color.White,
                        RoundedCornerShape(100.dp)
                    )
                    .clickable {
                        isMaleSelected = true
                    },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = if (isMaleSelected) R.drawable.sex_male_in_icon else R.drawable.sex_male_un_icon),
                    contentDescription = "Male icon",
                    modifier = Modifier
                        .width(18.dp)
                        .height(18.dp),
                )
                Text(
                    text = "Male",
                    modifier = Modifier.padding(start = 4.dp),
                    fontSize = 14.sp,
                    color = if (isMaleSelected) colorResource(
                        id = R.color.secondary_color
                    ) else colorResource(id = R.color.primary_dark)
                )
            }
            Row(
                modifier = Modifier
                    .height(40.dp)
                    .width(98.dp)
                    .border(
                        1.dp,
                        if (!isMaleSelected) colorResource(id = R.color.primary_dark) else Color.Black,
                        RoundedCornerShape(100.dp)
                    )
                    .background(
                        if (!isMaleSelected) colorResource(id = R.color.primary_dark) else Color.White,
                        RoundedCornerShape(100.dp)
                    )
                    .clickable {
                        isMaleSelected = false
                    },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = if (!isMaleSelected) R.drawable.sex_female_in_icon else R.drawable.sex_female_un_icon),
                    contentDescription = "Female icon",
                    modifier = Modifier
                        .width(18.dp)
                        .height(18.dp),
                )
                Text(
                    text = "Female",
                    modifier = Modifier.padding(start = 4.dp),
                    fontSize = 14.sp,
                    color = if (!isMaleSelected) colorResource(
                        id = R.color.secondary_color
                    ) else colorResource(id = R.color.primary_dark)
                )
            }
        }
    }
}

@Composable
fun Message(isUserMessage: Boolean, text: String, data: String) {
    if (isUserMessage) {
        Column(
            modifier = Modifier
                .padding(start = 40.dp)
                .width(214.dp)
                .height(IntrinsicSize.Max)
                .background(
                    colorResource(id = R.color.secondary_color),
                    RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp, bottomEnd = 16.dp)
                )
        ) {
            Text(text = text)
            Text(text = data)
        }
    } else {
        Column() {

            Text(text = text)
            Text(text = data)
        }
    }
}
