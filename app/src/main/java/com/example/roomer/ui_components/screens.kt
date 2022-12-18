package com.example.roomer.ui_components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.roomer.R
import com.example.roomer.utils.NavbarItem
import com.example.roomer.utils.Screens

@Composable
fun ProfileScreen() {
    val navController =
        if (NavbarItem.Profile.navHostController != null) NavbarItem.Profile.navHostController else rememberNavController()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp, bottom = 16.dp, start = 40.dp, end = 40.dp)
    ) {
        Text(
            text = "Profile",
            textAlign = TextAlign.Start,
            fontSize = integerResource(id = R.integer.label_text_size).sp,
        )
        Image(
            painter = painterResource(id = R.drawable.ordinary_client),
            contentDescription = "Client avatar",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 24.dp, bottom = 16.dp)
                .width(152.dp)
                .height(152.dp)
                .clickable {

                },
        )
        ProfileContentLine(Screens.Account.name, R.drawable.account_icon, onNavigateToFriends = {
            navController?.navigate(
                Screens.Account.name
            )
        })
        ProfileContentLine(
            Screens.Location.name, R.drawable.location_icon,
            onNavigateToFriends = {
                navController?.navigate(
                    Screens.Location.name
                )
            },
        )
        ProfileContentLine(
            Screens.Rating.name, R.drawable.rating_icon,
            onNavigateToFriends = {
                navController?.navigate(
                    Screens.Rating.name
                )
            },
        )
        ProfileContentLine(
            Screens.Settings.name, R.drawable.settings_icon,
            onNavigateToFriends = {
                navController?.navigate(
                    Screens.Settings.name
                )
            },
        )
        ProfileContentLine(
            Screens.Logout.name, R.drawable.logout_icon,
            onNavigateToFriends = {
                navController?.navigate(
                    Screens.Logout.name
                )
            },
        )
    }
}

@Composable
fun ChatScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp, bottom = 16.dp, start = 40.dp, end = 40.dp)
    ) {
        var searchText by remember {
            mutableStateOf(TextFieldValue(""))
        }
        TextField(
            label = { Text(text = "Search message") },
            value = searchText, onValueChange = {
                if (it.text.length <= 100) {
                    searchText = it
                }
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.loupe_icon),
                    contentDescription = "search_icon",
                    modifier = Modifier
                        .height(
                            integerResource(id = R.integer.ordinary_icon_size).dp
                        )
                        .width(
                            integerResource(id = R.integer.ordinary_icon_size).dp
                        ),
                )
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.clear_icon),
                    contentDescription = "clear_text",
                    modifier = Modifier
                        .height(
                            integerResource(id = R.integer.ordinary_icon_size).dp
                        )
                        .width(
                            integerResource(id = R.integer.ordinary_icon_size).dp
                        ),
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color.White)
                .border(
                    BorderStroke(
                        2.dp, colorResource(
                            id = R.color.primary_dark
                        )
                    ),
                    RoundedCornerShape(4.dp)
                )
        )
        Column(
            Modifier
                .fillMaxSize()
                .padding(top = 24.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MessageItem(
                userAvatarPath = "path",
                messageDate = "12.22",
                messageCutText = "Hello my name is Piter",
                username = "Grigoriev Oleg",
                isRead = false,
                unreadMessages = 0
            )
            MessageItem(
                userAvatarPath = "path",
                messageDate = "12.22",
                messageCutText = "Hello my name is Piter",
                username = "Grigoriev Oleg",
                isRead = true,
                unreadMessages = 10000
            )
            MessageItem(
                userAvatarPath = "path",
                messageDate = "12.22",
                messageCutText = "Hello my name is Piter",
                username = "Grigoriev Oleg",
                isRead = false,
                unreadMessages = 15
            )
        }
    }
}

@Composable
fun HomeScreen() {
    Text("Hello from home")
}

@Composable
fun AccountScreen() {
    Text(text = "Hello from account")
}