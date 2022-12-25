package com.example.roomer.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.roomer.R
import com.example.roomer.models.ChatMessage
import com.example.roomer.models.RecommendedRoom
import com.example.roomer.models.SearchUserResult
import com.example.roomer.ui_components.*
import com.example.roomer.utils.NavbarItem
import com.example.roomer.utils.Screens
import com.example.roomer.utils.convertLongToTime
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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
            BackBtn(onBackNavigation = { navController.navigate(NavbarItem.Chats.name) })
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
fun SearchRoomScreen() {
    val navController = NavbarItem.Home.navHostController ?: rememberNavController()
    Scaffold(
        modifier = Modifier.padding(start = 40.dp, end = 40.dp, top = 16.dp),
        floatingActionButton = {
            GreenButtonOutline(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp)
                    .height(40.dp),
                text = "Show results",
                onClick = { navController.navigate(Screens.SearchRoomResults.name) })
        }) {
        val padding = it
        var fromPrice by remember {
            mutableStateOf(TextFieldValue(""))
        }
        var toPrice by remember {
            mutableStateOf(TextFieldValue(""))
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                BackBtn(onBackNavigation = { navController.navigate(NavbarItem.Home.name) })
                Text(
                    text = "Search filter", modifier = Modifier.fillMaxWidth(), style = TextStyle(
                        fontSize = integerResource(
                            id = R.integer.label_text_size
                        ).sp, color = Color.Black
                    ), textAlign = TextAlign.Center
                )
            }
            FilterSelect(
                selectItemName = "Room",
                onNavigateToFriends = { navController.navigate(Screens.SearchRoommate.name) })
            Text(
                "Choose room parameters",
                style = TextStyle(
                    fontSize = 20.sp, color = Color.Black
                ),
            )
            Text(
                "Month price",
                style = TextStyle(
                    fontSize = 16.sp, color = Color.Black
                ),
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column() {
                    Text(
                        "From", style = TextStyle(
                            fontSize = integerResource(id = R.integer.primary_text_size).sp,
                            color = colorResource(
                                id = R.color.text_secondary
                            )
                        )
                    )
                    TextField(
                        value = fromPrice, onValueChange = { fromPrice = it },
                        modifier = Modifier
                            .width(120.dp)
                            .height(56.dp),
                        placeholder = { Text("Start price") },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = colorResource(
                                id = R.color.primary
                            )
                        )
                    )
                }
                Column() {
                    Text(
                        "To", style = TextStyle(
                            fontSize = integerResource(id = R.integer.primary_text_size).sp,
                            color = colorResource(
                                id = R.color.text_secondary
                            )
                        )
                    )
                    TextField(
                        value = toPrice,
                        onValueChange = { toPrice = it },
                        modifier = Modifier
                            .width(120.dp)
                            .height(56.dp),
                        placeholder = { Text("End price") },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = colorResource(
                                id = R.color.primary
                            )
                        )
                    )
                }
            }
            ScreenTextField(textHint = "Put some city, street", label = "Location")
            ButtonsRow(label = "Bedrooms", values = listOf("Any", "1", "2", "3+"))
            ButtonsRow(label = "Bathrooms", values = listOf("Any", "1", "2", "3+"))
            ButtonsRow(
                label = "Apartment Type",
                values = listOf("Any", "House", "Flat", "Dorm")
            )
        }
    }
}

@Composable
fun SearchRoomResults() {
    val navController = NavbarItem.Home.navHostController ?: rememberNavController()
    val listOfFavourites = listOf<RecommendedRoom>(
        RecommendedRoom(1, "Fav1", "Loc", "", true),
        RecommendedRoom(2, "Fav2", "Loc2", "", true),
        RecommendedRoom(3, "Fav3", "Loc3", "", true),
        RecommendedRoom(4, "Fav4", "Loc4", "", true)
    )
    Scaffold(
        bottomBar = { Navbar(navController, NavbarItem.Home.name) },
    ) {
        val padding = it
        Column(
            modifier = Modifier.padding(
                start = 40.dp,
                end = 40.dp,
                top = 16.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BackBtn(onBackNavigation = { navController.navigate(NavbarItem.Home.name) })
                Text(
                    text = "Housing Results",
                    fontSize = integerResource(
                        id = R.integer.label_text_size
                    ).sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(bottom = it.calculateBottomPadding())
            ) {
                items(listOfFavourites.size) { index ->
                    RoomCard(recommendedRoom = listOfFavourites[index], isMiniVersion = false)
                }
            }
        }
    }
}

@Composable
fun SearchRoommateScreen() {
    val navController = NavbarItem.Home.navHostController ?: rememberNavController()
    Scaffold(
        modifier = Modifier.padding(start = 40.dp, end = 40.dp, top = 16.dp, bottom = 16.dp),
        floatingActionButton = {
            GreenButtonOutline(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .fillMaxWidth()
                    .height(40.dp),
                text = "Show results",
                onClick = { navController.navigate(Screens.SearchRoommateResults.name) })
        }) {
        val listOfSleepTime = listOf<String>("Night", "Day", "Occasionally")
        val listOfPersonality = listOf<String>("Extraverted", "Introverted", "Mixed")
        val listOfSmocking = listOf<String>("Positive", "Negative", "Indifferent")
        val listOfAlchogol = listOf("Positive", "Negative", "Indifferent")
        val listOfLocation = listOf("In my town", "Not in my town")
        val listOfEmployment = listOf("Not Employed", "Employed", "Searching For Work")
        val listOfCleanHabits = listOf("Neat", "It Depends", "Chaos")
        var fromAge by remember {
            mutableStateOf(TextFieldValue(""))
        }
        var toAge by remember {
            mutableStateOf(TextFieldValue(""))
        }
        val padding = it
        Column(
            modifier = Modifier
                .padding(bottom = 64.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                BackBtn(onBackNavigation = { navController.navigate(NavbarItem.Home.name) })
                Text(
                    text = "Search filter", modifier = Modifier.fillMaxWidth(), style = TextStyle(
                        fontSize = integerResource(
                            id = R.integer.label_text_size
                        ).sp, color = Color.Black
                    ), textAlign = TextAlign.Center
                )
            }
            FilterSelect(
                selectItemName = "Roommate",
                onNavigateToFriends = { navController.navigate(Screens.SearchRoom.name) })
            Text(
                "Choose roommate parameters",
                style = TextStyle(
                    fontSize = 20.sp, color = Color.Black
                ),
            )
            Text(
                "Age",
                style = TextStyle(
                    fontSize = 16.sp, color = Color.Black
                ),
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column() {
                    Text(
                        "From", style = TextStyle(
                            fontSize = integerResource(id = R.integer.primary_text_size).sp,
                            color = colorResource(
                                id = R.color.text_secondary
                            )
                        )
                    )
                    TextField(
                        value = fromAge, onValueChange = { fromAge = it },
                        modifier = Modifier
                            .width(120.dp)
                            .height(56.dp),
                        placeholder = { Text("Start price") },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = colorResource(
                                id = R.color.primary
                            )
                        )
                    )
                }
                Column() {
                    Text(
                        "To", style = TextStyle(
                            fontSize = integerResource(id = R.integer.primary_text_size).sp,
                            color = colorResource(
                                id = R.color.text_secondary
                            )
                        )
                    )
                    TextField(
                        value = toAge,
                        onValueChange = { toAge = it },
                        modifier = Modifier
                            .width(120.dp)
                            .height(56.dp),
                        placeholder = { Text("End price") },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = colorResource(
                                id = R.color.primary
                            )
                        )
                    )
                }
            }
            DropdownTextField(listOfItems = listOfSleepTime, label = "Sleep time")
            DropdownTextField(listOfItems = listOfPersonality, label = "Personality")
            DropdownTextField(listOfItems = listOfSmocking, label = "Smocking attitude")
            DropdownTextField(listOfItems = listOfAlchogol, label = "Alcohol attitude")
            DropdownTextField(listOfItems = listOfLocation, label = "Location")
            DropdownTextField(listOfItems = listOfEmployment, label = "Employment")
            DropdownTextField(listOfItems = listOfCleanHabits, label = "Clean habits")
            InterestField(paddingValues = it, label = "Interests")
        }
    }
}

@Composable
fun SearchRoommateResults() {
    val navController = NavbarItem.Home.navHostController ?: rememberNavController()
    val resultsList = listOf<SearchUserResult>(
        SearchUserResult(
            "Mark Zuckenberg",
            "London, Avenu 12",
            "Occasionly",
            "7",
        ),
        SearchUserResult(
            "Mark Zuckenberg",
            "London, Avenu 12",
            "Occasionly",
            "7",
        ),
        SearchUserResult(
            "Mark Zuckenberg",
            "London, Avenu 12",
            "Occasionly",
            "7",
        ),
    )
    Scaffold(
        bottomBar = { Navbar(navController, NavbarItem.Home.name) },
    ) {
        Column(
            modifier = Modifier.padding(start = 40.dp, end = 40.dp, top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BackBtn(onBackNavigation = { navController.navigate(NavbarItem.Home.name) })
                Text(
                    text = "Roommate Results",
                    fontSize = integerResource(
                        id = R.integer.label_text_size
                    ).sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(bottom = it.calculateBottomPadding())
            ) {
                items(resultsList.size) { index ->
                    UserCardResult(searchUser = resultsList[index])
                }
            }
        }
    }
}
