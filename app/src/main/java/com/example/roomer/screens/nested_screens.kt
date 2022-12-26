package com.example.roomer.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
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
import com.example.roomer.ui_components.*
import com.example.roomer.utils.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ramcosta.composedestinations.annotation.Destination

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

@Destination
@Composable
fun SearchRoomScreen() {
    val navController = NavbarItem.Home.navHostController ?: rememberNavController()

    var fromPrice by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var toPrice by remember {
        mutableStateOf(TextFieldValue(""))
    }
    val location = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val bedrooms = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val bathrooms = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val apartmentType = remember {
        mutableStateOf(TextFieldValue(""))
    }
    Scaffold(
        modifier = Modifier.padding(start = 40.dp, end = 40.dp, top = 16.dp),
        floatingActionButton = {
            GreenButtonOutline(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp)
                    .height(40.dp),
                text = "Show results",
                onClick = {
                    navController.navigate(
                        Screens.SearchRoomResults.name + "?from=${fromPrice.text}&to=${toPrice.text}" +
                                "&location=${location.value.text}&bedrooms=${bedrooms.value.text}" +
                                "&bathrooms=${bathrooms.value.text}&apartment_type=${apartmentType.value.text}"
                    )
                })
        }) {
        val padding = it
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                BackBtn(onBackNavigation = {
                    navController.navigate(NavbarItem.Home.name)
                })
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
            ScreenTextField(textHint = "Put some city, street", label = "Location", text = location)
            ButtonsRow(
                label = "Bedrooms",
                values = listOf("Any", "1", "2", ">3"),
                selectedValue = bedrooms
            )
            ButtonsRow(
                label = "Bathrooms",
                values = listOf("Any", "1", "2", ">3"),
                selectedValue = bathrooms
            )
            ButtonsRow(
                label = "Apartment Type",
                values = listOf("Any", "House", "Flat", "Dorm"),
                selectedValue = apartmentType
            )
        }
    }
}

@Destination
@Composable
fun SearchRoomResults() {
    val navController = NavbarItem.Home.navHostController ?: rememberNavController()
    val from = navController.currentBackStackEntry?.arguments?.getString("from") ?: ""
    val to = navController.currentBackStackEntry?.arguments?.getString("to") ?: ""
    val location = navController.currentBackStackEntry?.arguments?.getString("location") ?: ""
    val bedrooms = navController.currentBackStackEntry?.arguments?.getString("bedrooms") ?: ""
    val bathrooms = navController.currentBackStackEntry?.arguments?.getString("bathrooms") ?: ""
    var apartmenType =
        navController.currentBackStackEntry?.arguments?.getString("apartment_type") ?: ""
    apartmenType = when (apartmenType) {
        "Flat" -> "F"
        "Duplex" -> "DU"
        "House" -> "H"
        else -> "DO"
    }
    val viewModel = SearchRoomResultsViewModel()
    val rooms by viewModel.rooms.collectAsState()
    viewModel.loadRooms(from, to, bedrooms, bathrooms, apartmenType)
    val loadingState = viewModel.loadingState.collectAsState()
    Scaffold(
        bottomBar = { Navbar(navController, NavbarItem.Home.name) },
    ) {
        val padding = it
        when (loadingState.value) {
            LoadingStates.Success ->
                Column(
                    modifier = Modifier.padding(
                        start = 40.dp,
                        end = 40.dp,
                        top = 16.dp,
                    ),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
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
                        items(rooms.size) { index ->
                            RoomCard(
                                recommendedRoom = RecommendedRoom(
                                    id = 0,
                                    name = rooms[index].description,
                                    location = rooms[index].location,
                                    roomImagePath = rooms[index].photo,
                                    isLiked = false,
                                ), isMiniVersion = false
                            )
                        }
                    }
                }
            LoadingStates.Loading -> CircularProgressIndicator(modifier = Modifier.fillMaxSize())
            LoadingStates.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Sorry, something went wrong. You should to retry",
                        style = TextStyle(
                            fontSize = integerResource(
                                id = R.integer.primary_text_size
                            ).sp,
                            color = Color.Black,
                        )
                    )
                    GreenButtonOutline(text = "Retry") {
                        viewModel.loadRooms(from, to, bedrooms, bathrooms, apartmenType)
                    }
                }
            }
        }
    }
}

@Destination
@Composable
fun SearchRoommateScreen() {
    val navController = NavbarItem.Home.navHostController ?: rememberNavController()
    var fromAge by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var toAge by remember {
        mutableStateOf(TextFieldValue(""))
    }
    val sleepTime = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val personality = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val smokingAttitude = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val alcoholAttitude = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val location = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val employment = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val cleanHabits = remember {
        mutableStateOf(TextFieldValue(""))
    }
    Scaffold(
        modifier = Modifier.padding(start = 40.dp, end = 40.dp, top = 16.dp, bottom = 16.dp),
        floatingActionButton = {
            GreenButtonOutline(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .fillMaxWidth()
                    .height(40.dp),
                text = "Show results",
                onClick = {
                    navController.navigate(
                        Screens.SearchRoommateResults.name +
                                "?sex=M&employment=${employment.value.text}&alcohol_attitude=${alcoholAttitude.value.text}" +
                                "&smoking_attitude=${smokingAttitude.value.text}&sleep_time=${sleepTime.value.text}" +
                                "&personality_type=${personality.value.text}&clean_habits=${cleanHabits.value.text}"
                    )
                })
        }) {
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
            DropdownTextField(
                listOfItems = Choices.sleepTime,
                label = "Sleep time",
                textSelected = sleepTime,
            )
            DropdownTextField(
                listOfItems = Choices.personality,
                label = "Personality",
                textSelected = personality,
            )
            DropdownTextField(
                listOfItems = Choices.smockingAttitude,
                label = "Smocking attitude",
                textSelected = smokingAttitude,
            )
            DropdownTextField(
                listOfItems = Choices.alcoholAttitude,
                label = "Alcohol attitude",
                textSelected = alcoholAttitude,
            )
            DropdownTextField(
                listOfItems = Choices.location,
                label = "Location",
                textSelected = location,
            )
            DropdownTextField(
                listOfItems = Choices.employment,
                label = "Employment",
                textSelected = employment,
            )
            DropdownTextField(
                listOfItems = Choices.cleanHabits,
                label = "Clean habits",
                textSelected = cleanHabits,
            )
            InterestField(paddingValues = it, label = "Interests")
        }
    }
}

@Destination
@Composable
fun SearchRoommateResults() {
    val navController = NavbarItem.Home.navHostController ?: rememberNavController()
    var sex = navController.currentBackStackEntry?.arguments?.getString("sex") ?: ""
    var employment = navController.currentBackStackEntry?.arguments?.getString("employment") ?: ""
    var alcoholAttitude =
        navController.currentBackStackEntry?.arguments?.getString("alcohol_attitude") ?: ""
    var smokingAttitude =
        navController.currentBackStackEntry?.arguments?.getString("smoking_attitude") ?: ""
    var sleepTime = navController.currentBackStackEntry?.arguments?.getString("sleep_time") ?: ""
    var personalityType =
        navController.currentBackStackEntry?.arguments?.getString("personality_type") ?: ""
    var cleanHabits =
        navController.currentBackStackEntry?.arguments?.getString("clean_habits") ?: ""
    employment = employment[0].titlecase()
    sex = sex[0].titlecase()
    alcoholAttitude = alcoholAttitude[0].titlecase()
    smokingAttitude = smokingAttitude[0].titlecase()
    sleepTime = sleepTime[0].titlecase()
    personalityType = personalityType[0].titlecase()
    cleanHabits = when (cleanHabits) {
        "Neat" -> "N"
        "It Depends" -> "D"
        else -> "C"
    }
    val viewModel = SearchRoommateResultViewModel()
    viewModel.loadRoommates(
        sex,
        employment,
        alcoholAttitude,
        smokingAttitude,
        sleepTime,
        personalityType,
        cleanHabits
    )
    val roommates by viewModel.roommates.collectAsState()
    val loadingState = viewModel.loadingState.collectAsState()
    Scaffold(
        bottomBar = { Navbar(navController, NavbarItem.Home.name) },
    ) {
        when (loadingState.value) {
            LoadingStates.Success ->
                Column(
                    modifier = Modifier.padding(start = 40.dp, end = 40.dp, top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
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
                        item {
                            if (roommates.isEmpty()) {
                                Text(
                                    text = "Sorry, nothing here", style = TextStyle(
                                        fontSize = integerResource(
                                            id = R.integer.label_text_size
                                        ).sp,
                                    )
                                )
                            }
                        }
                        items(roommates.size) { index ->
                            UserCardResult(roommates[index])
                        }
                    }
                }
            LoadingStates.Loading -> CircularProgressIndicator(modifier = Modifier.fillMaxSize())
            LoadingStates.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Sorry, something went wrong. You should to retry",
                        style = TextStyle(
                            fontSize = integerResource(
                                id = R.integer.primary_text_size
                            ).sp,
                            color = Color.Black,
                        )
                    )
                    GreenButtonOutline(text = "Retry") {
                        viewModel.loadRoommates(
                            sex,
                            employment,
                            alcoholAttitude,
                            smokingAttitude,
                            sleepTime,
                            personalityType,
                            cleanHabits
                        )
                    }
                }
            }
        }
    }
}
