<<<<<<<< HEAD:app/src/main/java/com/example/roomer/presentation/ui_components/screens.kt
package com.example.roomer.presentation.ui_components
========
package com.example.roomer.screens
>>>>>>>> origin/master:app/src/main/java/com/example/roomer/screens/navbar_screens.kt

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.roomer.R
import com.example.roomer.models.MessageToList
import com.example.roomer.models.RecommendedRoom
import com.example.roomer.models.RecommendedRoommate
import com.example.roomer.ui_components.*
import com.example.roomer.utils.NavbarItem
import com.example.roomer.utils.Screens

@Composable
fun ProfileScreen() {
    val navController = NavbarItem.Profile.navHostController ?: rememberNavController()
    Scaffold(bottomBar = { Navbar(navController, NavbarItem.Profile.name) }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 24.dp, bottom = it.calculateBottomPadding(), start = 40.dp, end = 40.dp)
        ) {
            Text(
                text = stringResource(R.string.profile_title),
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
                    .clip(RoundedCornerShape(100))
                    .clickable {

                    },
            )
            ProfileContentLine(
                Screens.Account.name,
                R.drawable.account_icon,
                onNavigateToFriends = {
                    navController.navigate(
                        Screens.Account.name
                    )
                })
            ProfileContentLine(
                Screens.Location.name, R.drawable.location_icon,
                onNavigateToFriends = {
                    navController.navigate(
                        Screens.Location.name
                    )
                },
            )
            ProfileContentLine(
                Screens.Rating.name, R.drawable.rating_icon,
                onNavigateToFriends = {
                    navController.navigate(
                        Screens.Rating.name
                    )
                },
            )
            ProfileContentLine(
                Screens.Settings.name, R.drawable.settings_icon,
                onNavigateToFriends = {
                    navController.navigate(
                        Screens.Settings.name
                    )
                },
            )
            ProfileContentLine(
                Screens.Logout.name, R.drawable.logout_icon,
                onNavigateToFriends = {
                    navController.navigate(
                        Screens.Logout.name
                    )
                },
            )
        }
    }
}

@Composable
fun ChatsScreen() {
    val navController = NavbarItem.Chats.navHostController ?: rememberNavController()
    Scaffold(bottomBar = { Navbar(navController, NavbarItem.Chats.name) }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp, bottom = it.calculateBottomPadding(), start = 40.dp, end = 40.dp)
        ) {
            var searchText by remember {
                mutableStateOf(TextFieldValue(""))
            }
            TextField(
                label = {
                    Text(
                        text = "Search in messages",
                        style = TextStyle(
                            color = colorResource(id = R.color.primary_dark),
                            fontSize = 12.sp
                        ),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = integerResource(id = R.integer.primary_text_size).sp,
                ),
                value = searchText, onValueChange = { value ->
                    if (value.text.length <= 100) {
                        searchText = value
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
                                24.dp
                            )
                            .width(
                                24.dp
                            )
                            .clickable { searchText = TextFieldValue("") },
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .border(
                        BorderStroke(
                            2.dp, colorResource(
                                id = R.color.primary_dark
                            )
                        ),
                        RoundedCornerShape(4.dp)
                    ),
                colors = TextFieldDefaults.textFieldColors(backgroundColor = colorResource(id = R.color.white))
            )
            val listOfMessages = listOf(
                MessageToList(userAvatarPath = "path",
                    messageDate = "12.22",
                    messageCutText = "Hello my name is Piter",
                    username = "Grigoriev Oleg",
                    isRead = false,
                    unreadMessages = 0,
                    navigateToMessage = { navController.navigate(Screens.Chat.name) }),
                MessageToList(userAvatarPath = "path",
                    messageDate = "12.22",
                    messageCutText = "Hello my name is Piter",
                    username = "Grigoriev Oleg",
                    isRead = true,
                    unreadMessages = 10000,
                    navigateToMessage = { navController.navigate(Screens.Chat.name) }),
                MessageToList(userAvatarPath = "path",
                    messageDate = "12.22",
                    messageCutText = "Hello my name is Piter",
                    username = "Grigoriev Oleg",
                    isRead = false,
                    unreadMessages = 15,
                    navigateToMessage = { navController.navigate(Screens.Chat.name) }),
            )
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(top = 24.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(listOfMessages.size) { index ->
                    MessageItem(listOfMessages[index])
                }
            }
        }
    }
}

@Composable
fun HomeScreen() {
    val navController = NavbarItem.Home.navHostController ?: rememberNavController()
    val recommendedRooms = mutableListOf<RecommendedRoom>()
    val recommendedRoommates = mutableListOf<RecommendedRoommate>()
    for (i in 0..5) {
        recommendedRooms.add(
            RecommendedRoom(
                i,
                "Room $i",
                "Location $i",
                "",
                false
            )
        )
        recommendedRoommates.add(
            RecommendedRoommate(
                i,
                "Andrey $i",
                0.2,
                "",
            )
        )
    }
    Scaffold(bottomBar = { Navbar(navController, NavbarItem.Home.name) }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 18.dp, bottom = it.calculateBottomPadding(), start = 40.dp, end = 40.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(modifier = Modifier.fillMaxHeight()) {
                    Text(
                        text = "Welcome back!",
                        style = TextStyle(
                            color = colorResource(id = R.color.text_secondary),
                            fontSize = 18.sp,
                        )
                    )
                    Text(
                        text = "Client name here",
                        style = TextStyle(
                            color = colorResource(id = R.color.text_secondary),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.ordinary_client),
                    contentDescription = "Client avatar",
                    modifier = Modifier
                        .height(56.dp)
                        .width(56.dp),
                    alignment = Alignment.Center,
                )
            }
            SearchField(onNavigateToFriends = { navController.navigate(Screens.SearchRoom.name) })
            Column(
                modifier = Modifier
                    .scrollable(
                        rememberScrollState(),
                        orientation = Orientation.Vertical,
                    )
                    .fillMaxSize()
                    .padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "Recently watched",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(148.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        items(recommendedRoommates.size - 2) { index ->
                            UserCard(recommendedRoommate = recommendedRoommates[index])
                        }
                        items(recommendedRooms.size - 2) { index ->
                            RoomCard(recommendedRoom = recommendedRooms[index], true)
                        }
                    }
                }
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "Recommended rooms",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(148.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        items(recommendedRooms.size) { index ->
                            RoomCard(recommendedRoom = recommendedRooms[index], true)
                        }
                    }
                }
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "Recommended roommates",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(148.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        items(recommendedRoommates.size) { index ->
                            UserCard(recommendedRoommate = recommendedRoommates[index])
                        }
                    }
                }
            }
        }
    }
}

@Composable
<<<<<<<< HEAD:app/src/main/java/com/example/roomer/presentation/ui_components/screens.kt
fun AccountScreen() {
    val navController = NavbarItem.Profile.navHostController ?: rememberNavController()
    val listOfEmployments = listOf<String>("Employed", "Unemployed", "Seasonable")
    Scaffold(bottomBar = { Navbar(navController, NavbarItem.Profile.name) }) {
        val padding = it
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp, bottom = 88.dp, start = 40.dp, end = 40.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back_btn),
                    modifier = Modifier
                        .height(40.dp)
                        .width(40.dp)
                        .clickable {
                            navController.navigate(NavbarItem.Profile.name)
                        },
                    contentDescription = "Back button"
                )
                Text(
                    text = "Account",
                    fontSize = integerResource(
                        id = R.integer.label_text_size
                    ).sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                ScreenTextField(label = "First Name", textHint = "Vasya")
                ScreenTextField(label = "Last Name", textHint = "Pupkin")
//                TODO change dateField arguments
//                DateField(label = "Date of birth")
                SelectSex()
                //TODO change sexField arguments
//                DropdownTextField(listOfItems = listOfEmployments, label = "Employment")
                ScreenTextField(
                    textHint = "Some text about you",
                    label = "About me",
                    textFieldHeight = 112
                )
                SelectAddressField(
                    label = "Select address at the map",
                    placeholder = "Here will be your address"
                )
                Row(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier
                            .height(40.dp)
                            .width(146.dp)
                            .border(
                                width = 1.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(100.dp)
                            )
                            .clickable {

                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.habits_icon),
                            contentDescription = "Habits icon",
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .width(18.dp)
                                .height(18.dp),
                        )
                        Text(
                            text = "Open habits",
                            style = TextStyle(
                                color = colorResource(id = R.color.primary_dark),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                            ),
                        )
                    }
                    Row(
                        modifier = Modifier
                            .height(40.dp)
                            .width(146.dp)
                            .border(
                                width = 1.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(100.dp)
                            )
                            .clickable {

                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.interests_icon),
                            contentDescription = "Habits icon",
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .width(18.dp)
                                .height(18.dp),
                        )
                        Text(
                            text = "Open interests",
                            style = TextStyle(
                                color = colorResource(id = R.color.primary_dark),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                            ),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MessageScreen() {
    val navController = NavbarItem.Chats.navHostController ?: rememberNavController()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp, bottom = 16.dp, start = 40.dp, end = 40.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth(),
        ) {
            Image(
                painter = painterResource(id = R.drawable.back_btn),
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp)
                    .clickable {
                        navController.navigate(NavbarItem.Chats.name)
                    },
                contentDescription = "Back button"
            )
            Image(
                modifier = Modifier
                    .width(56.dp)
                    .height(56.dp)
                    .padding(start = 16.dp),
                painter = painterResource(id = R.drawable.ordinary_client),
                contentDescription = "Client avatar",
                alignment = Alignment.Center,
            )
            Text(
                text = "Username here",
                modifier = Modifier.padding(start = 8.dp),
                style = TextStyle(
                    color = Color.Black,
                    fontSize = integerResource(
                        id = R.integer.primary_text_size
                    ).sp,
                    fontWeight = FontWeight.Bold,
                )
            )
        }
        Divider(
            color = colorResource(id = R.color.black),
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
        )
        var messages by remember {
            mutableStateOf(listOf<ChatMessage>())
        }
        val mutableListOfMessages = mutableListOf<ChatMessage>()
        FirebaseDatabase.getInstance("https://roomer-34a08-default-rtdb.europe-west1.firebasedatabase.app").reference.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (postSnapshot in snapshot.children) {
                        mutableListOfMessages.add(
                            ChatMessage(
                                postSnapshot.child("messageText").value as String,
                                postSnapshot.child("messageSenderUser").value as String,
                                postSnapshot.child("messageReceiverUser").value as String,
                                postSnapshot.child("messageTime").value as Long,
                            )
                        )
                    }
                    messages = mutableListOfMessages
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        var editMessageText by remember {
            mutableStateOf(TextFieldValue(""))
        }
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(messages.size) { index ->
                Message(
                    isUserMessage = false,
                    text = messages[index].messageText,
                    data = convertLongToTime(messages[index].messageTime)
                )
            }
        }
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.Bottom) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = editMessageText,
                placeholder = {
                    Text(
                        text = "Type your message", style = TextStyle(
                            color = colorResource(
                                id = R.color.text_secondary
                            ),
                            fontSize = integerResource(id = R.integer.primary_text_size).sp,
                        )
                    )
                },
                onValueChange = { editMessageText = it },
                trailingIcon = {
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.add_icon),
                            contentDescription = "Add icon",
                            modifier = Modifier
                                .width(32.dp)
                                .height(32.dp)
                        )
                        Box(
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .width(48.dp)
                                .height(32.dp)
                                .background(
                                    color = colorResource(id = R.color.secondary_color),
                                    RoundedCornerShape(100.dp)
                                )
                                .clickable {
                                    FirebaseDatabase
                                        .getInstance("https://roomer-34a08-default-rtdb.europe-west1.firebasedatabase.app")
                                        .reference
                                        .push()
                                        .setValue(
                                            ChatMessage(
                                                editMessageText.text,
                                                FirebaseAuth.getInstance().currentUser?.displayName
                                                    ?: "Error",
                                                "Second user",
                                            )
                                        )
                                    editMessageText = TextFieldValue("")
                                },
                            contentAlignment = Alignment.Center,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.send_icon),
                                contentDescription = "Enter message",
                                alignment = Alignment.Center,
                                modifier = Modifier
                                    .width(24.dp)
                                    .height(24.dp)

                            )
                        }
                    }
                },
                colors = TextFieldDefaults.textFieldColors(backgroundColor = colorResource(id = R.color.primary))
            )
        }
    }
}

@Composable
========
>>>>>>>> origin/master:app/src/main/java/com/example/roomer/screens/navbar_screens.kt
fun FavouriteScreen() {
    val navController = NavbarItem.Favourite.navHostController ?: rememberNavController()
    Scaffold(bottomBar = { Navbar(navController, NavbarItem.Favourite.name) }) {
        val listOfFavourites = listOf(
            RecommendedRoom(1, "Fav1", "Loc", "", true),
            RecommendedRoom(2, "Fav2", "Loc2", "", true),
            RecommendedRoom(3, "Fav3", "Loc3", "", true),
            RecommendedRoom(4, "Fav4", "Loc4", "", true)
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(bottom=it.calculateBottomPadding()),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Text(
                    text = "Favourite",
                    style = TextStyle(
                        fontSize = integerResource(id = R.integer.label_text_size).sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            items(listOfFavourites.size) { index ->
                RoomCard(recommendedRoom = listOfFavourites[index], isMiniVersion = false)
            }
        }
    }
}

@Composable
fun PostScreen() {
    val navController = NavbarItem.Post.navHostController ?: rememberNavController()
    Scaffold(bottomBar = { Navbar(navController, NavbarItem.Post.name) }) {
        Text("Hello from post", Modifier.padding(it))
    }
}
