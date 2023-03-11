package com.example.roomer.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.roomer.R
import com.example.roomer.domain.model.ChatMessage
import com.example.roomer.domain.model.RecommendedRoom
import com.example.roomer.presentation.screens.destinations.ChatsScreenDestination
import com.example.roomer.presentation.screens.destinations.HomeScreenDestination
import com.example.roomer.presentation.screens.destinations.SearchRoomResultsDestination
import com.example.roomer.presentation.screens.destinations.SearchRoomScreenDestination
import com.example.roomer.presentation.screens.destinations.SearchRoommateScreenDestination
import com.example.roomer.presentation.ui_components.BackBtn
import com.example.roomer.presentation.ui_components.FilterSelect
import com.example.roomer.presentation.ui_components.GreenButtonOutline
import com.example.roomer.presentation.ui_components.InterestField
import com.example.roomer.presentation.ui_components.Message
import com.example.roomer.presentation.ui_components.RoomCard
import com.example.roomer.presentation.ui_components.ScreenTextField
import com.example.roomer.presentation.ui_components.UserCardResult
import com.example.roomer.utils.LoadingStates
import com.example.roomer.utils.convertLongToTime
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun MessageScreen(
    navigator: DestinationsNavigator,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp, bottom = 16.dp, start = 40.dp, end = 40.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            BackBtn(onBackNavigation = { navigator.navigate(ChatsScreenDestination) })
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
        FirebaseDatabase.getInstance(
            "https://roomer-34a08-default-rtdb.europe-west1.firebasedatabase.app"
        ).reference.addValueEventListener(
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
            }
        )
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
                        text = "Type your message",
                        style = TextStyle(
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
                                        .getInstance()
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
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = colorResource(id = R.color.primary)
                )
            )
        }
    }
}

@Destination
@Composable
fun SearchRoomScreen(
    navigator: DestinationsNavigator,
) {
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
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.padding(start = 40.dp, end = 40.dp, top = 16.dp),
        floatingActionButton = {
            GreenButtonOutline(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(100.dp))
                    .height(40.dp),
                text = "Show results",
                onClick = {
                    if ((
                        Integer.getInteger(fromPrice.text)
                            ?: 0
                        ) > (Integer.getInteger(toPrice.text) ?: 0)
                    ) {
                        Toast.makeText(context, "To price less than from price", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        navigator.navigate(SearchRoomResultsDestination)
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                BackBtn(
                    onBackNavigation = {
                        navigator.navigate(HomeScreenDestination)
                    }
                )
                Text(
                    text = "Search filter",
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(
                        fontSize = integerResource(
                            id = R.integer.label_text_size
                        ).sp,
                        color = Color.Black
                    ),
                    textAlign = TextAlign.Center
                )
            }
            FilterSelect(
                selectItemName = "Room",
                onNavigateToFriends = { navigator.navigate(SearchRoommateScreenDestination) }
            )
            Text(
                "Choose room parameters",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.Black
                ),
            )
            Text(
                "Month price",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Black
                ),
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        "From",
                        style = TextStyle(
                            fontSize = integerResource(id = R.integer.primary_text_size).sp,
                            color = colorResource(
                                id = R.color.text_secondary
                            )
                        )
                    )
                    TextField(
                        value = fromPrice,
                        onValueChange = { changedText ->
                            fromPrice = changedText
                        },
                        modifier = Modifier
                            .width(120.dp)
                            .height(56.dp),
                        placeholder = { Text("Start price") },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = colorResource(
                                id = R.color.secondary_color
                            )
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                }
                Column {
                    Text(
                        "To",
                        style = TextStyle(
                            fontSize = integerResource(id = R.integer.primary_text_size).sp,
                            color = colorResource(
                                id = R.color.text_secondary
                            )
                        )
                    )
                    TextField(
                        value = toPrice,
                        onValueChange = { changedText ->
                            toPrice = changedText
                        },
                        modifier = Modifier
                            .width(120.dp)
                            .height(56.dp),
                        placeholder = { Text("End price") },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = colorResource(
                                id = R.color.secondary_color
                            )
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                }
            }
            ScreenTextField(textHint = "Put some city, street", label = "Location", text = location)
//            ButtonsRow(
//                label = "Bedrooms",
//                values = listOf("Any", "1", "2", ">3"),
//                value = bedrooms
//            )TODO Change implementation to String
//            ButtonsRow(
//                label = "Bathrooms",
//                values = listOf("Any", "1", "2", ">3"),
//                selectedValue = bathrooms
//            )TODO Change implementation to String
//            ButtonsRow(
//                label = "Apartment Type",
//                values = listOf("Any", "House", "Flat", "Dorm"),
//                selectedValue = apartmentType
//            )TODO Change implementation to String
        }
    }
}

@Destination
@Composable
fun SearchRoomResults(
    navigator: DestinationsNavigator,
) {
    val from = ""
    val to = ""
    val location = ""
    val bedrooms = ""
    val bathrooms = ""
    var apartmentType = ""
    apartmentType = when (apartmentType) {
        "Flat" -> "F"
        "Duplex" -> "DU"
        "House" -> "H"
        else -> "DO"
    }
    val viewModel: SearchRoomResultsViewModel = viewModel()
    val rooms by viewModel.rooms.collectAsState()
    viewModel.loadRooms(from, to, bedrooms, bathrooms, apartmentType)
    val loadingState = viewModel.loadingState.collectAsState()
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
                    BackBtn(onBackNavigation = { navigator.navigate(HomeScreenDestination) })
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
                ) {
                    item {
                        if (rooms.isEmpty()) {
                            Text(
                                text = "Sorry, nothing here",
                                style = TextStyle(
                                    fontSize = integerResource(id = R.integer.label_text_size).sp,
                                )
                            )
                        }
                    }
                    items(rooms.size) { index ->
                        RoomCard(
                            recommendedRoom = RecommendedRoom(
                                id = 0,
                                name = rooms[index].title,
                                location = rooms[index].location,
                                roomImagePath = rooms[index].photo,
                                isLiked = false,
                            ),
                            isMiniVersion = false
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
                    viewModel.loadRooms(from, to, bedrooms, bathrooms, apartmentType)
                }
            }
        }
    }
}

@Destination
@Composable
fun SearchRoommateScreen(
    navigator: DestinationsNavigator,
) {
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
    val context = LocalContext.current
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
                    if ((Integer.getInteger(fromAge.text) ?: 0) > (
                        Integer.getInteger(toAge.text)
                            ?: 0
                        )
                    ) {
                        Toast.makeText(context, "To age less than from age", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        navigator.navigate(SearchRoommateScreenDestination)
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = it.calculateBottomPadding() + 64.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                BackBtn(onBackNavigation = { navigator.navigate(HomeScreenDestination) })
                Text(
                    text = "Search filter",
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(
                        fontSize = integerResource(
                            id = R.integer.label_text_size
                        ).sp,
                        color = Color.Black
                    ),
                    textAlign = TextAlign.Center
                )
            }
            FilterSelect(
                selectItemName = "Roommate",
                onNavigateToFriends = { navigator.navigate(SearchRoomScreenDestination) }
            )
            Text(
                "Choose roommate parameters",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.Black
                ),
            )
            Text(
                "Age",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Black
                ),
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        "From",
                        style = TextStyle(
                            fontSize = integerResource(id = R.integer.primary_text_size).sp,
                            color = colorResource(
                                id = R.color.text_secondary
                            )
                        )
                    )
                    TextField(
                        value = fromAge,
                        onValueChange = { changedText ->
                            fromAge = changedText
                        },
                        modifier = Modifier
                            .width(120.dp)
                            .height(56.dp),
                        placeholder = { Text("Start age") },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = colorResource(
                                id = R.color.secondary_color
                            )
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                }
                Column {
                    Text(
                        "To",
                        style = TextStyle(
                            fontSize = integerResource(id = R.integer.primary_text_size).sp,
                            color = colorResource(
                                id = R.color.text_secondary
                            )
                        )
                    )
                    TextField(
                        value = toAge,
                        onValueChange = { changedText ->
                            toAge = changedText
                        },
                        modifier = Modifier
                            .width(120.dp)
                            .height(56.dp),
                        placeholder = { Text("End age") },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = colorResource(
                                id = R.color.secondary_color
                            )
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                }
            }
//            DropdownTextField(
//                listOfItems = Choices.sleepTime,
//                label = "Sleep time",
//                textSelected = sleepTime,
//            )TODO Change implementation to String
//            DropdownTextField(
//                listOfItems = Choices.personality,
//                label = "Personality",
//                textSelected = personality,
//            )TODO Change implementation to String
//            DropdownTextField(
//                listOfItems = Choices.smockingAttitude,
//                label = "Smocking attitude",
//                textSelected = smokingAttitude,
//            )TODO Change implementation to String
//            DropdownTextField(
//                listOfItems = Choices.alcoholAttitude,
//                label = "Alcohol attitude",
//                textSelected = alcoholAttitude,
//            )TODO Change implementation to String
//            DropdownTextField(
//                listOfItems = Choices.location,
//                label = "Location",
//                textSelected = location,
//            )TODO Change implementation to String
//            DropdownTextField(
//                listOfItems = Choices.employment,
//                label = "Employment",
//                textSelected = employment,
//            )TODO Change implementation to String
//            DropdownTextField(
//                listOfItems = Choices.cleanHabits,
//                label = "Clean habits",
//                textSelected = cleanHabits,
//            )TODO Change implementation to String
            InterestField(paddingValues = it, label = "Interests")
        }
    }
}

@Destination
@Composable
fun SearchRoommateResults(
    navigator: DestinationsNavigator,
) {
    // TODO("Make search results")
    var sex = ""
    var employment = ""
    var alcoholAttitude = ""
    var smokingAttitude = ""
    var sleepTime = ""
    var personalityType = ""
    var cleanHabits = ""
    cleanHabits = when (cleanHabits) {
        "Neat" -> "N"
        "It Depends" -> "D"
        else -> "C"
    }
    val viewModel: SearchRoommateResultViewModel = viewModel()
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
                    BackBtn(onBackNavigation = { navigator.navigate(HomeScreenDestination) })
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
                ) {
                    item {
                        if (roommates.isEmpty()) {
                            Text(
                                text = "Sorry, nothing here",
                                style = TextStyle(
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
