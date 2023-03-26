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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.roomer.R
import com.example.roomer.domain.model.ChatMessage
import com.example.roomer.domain.model.RecommendedRoom
import com.example.roomer.presentation.screens.destinations.ChatsScreenDestination
import com.example.roomer.presentation.screens.destinations.HomeScreenDestination
import com.example.roomer.presentation.screens.destinations.SearchRoomResultsDestination
import com.example.roomer.presentation.screens.destinations.SearchRoomScreenDestination
import com.example.roomer.presentation.screens.destinations.SearchRoommateScreenDestination
import com.example.roomer.presentation.ui_components.BackBtn
import com.example.roomer.presentation.ui_components.ButtonsRow
import com.example.roomer.presentation.ui_components.ButtonsRowMapped
import com.example.roomer.presentation.ui_components.DropdownTextFieldMapped
import com.example.roomer.presentation.ui_components.FilterSelect
import com.example.roomer.presentation.ui_components.GreenButtonOutline
import com.example.roomer.presentation.ui_components.InterestField
import com.example.roomer.presentation.ui_components.Message
import com.example.roomer.presentation.ui_components.RoomCard
import com.example.roomer.presentation.ui_components.UserCardResult
import com.example.roomer.presentation.ui_components.UsualTextField
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
                contentDescription = stringResource(R.string.user_avatar_description),
                alignment = Alignment.Center,
            )
            Text(
                text = stringResource(R.string.username_here),
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
                        text = stringResource(R.string.type_your_message),
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
                            contentDescription = stringResource(R.string.add_icon),
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
                                contentDescription = stringResource(R.string.enter_message_content_description),
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
        mutableStateOf("0")
    }
    var toPrice by remember {
        mutableStateOf("0")
    }
    val location = remember {
        mutableStateOf("Astrakhan city")
    }
    val bedrooms = remember {
        mutableStateOf("1")
    }
    val bathrooms = remember {
        mutableStateOf("1")
    }
    val apartmentType = remember {
        mutableStateOf("F")
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
                text = stringResource(R.string.show_results),
                onClick = {
                    if (fromPrice > toPrice) {
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
                    text = stringResource(R.string.search_filter),
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
                stringResource(R.string.choose_room_parameters),
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.Black
                ),
            )
            Text(
                stringResource(R.string.month_price),
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
                        stringResource(R.string.from_price_title),
                        style = TextStyle(
                            fontSize = integerResource(id = R.integer.primary_text_size).sp,
                            color = colorResource(
                                id = R.color.text_secondary
                            )
                        )
                    )
                    TextField(
                        value = fromPrice,
                        onValueChange = { changedPrice ->
                            fromPrice = changedPrice
                        },
                        modifier = Modifier
                            .width(120.dp)
                            .height(56.dp),
                        placeholder = { Text(stringResource(R.string.start_price_placeholder)) },
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
                        stringResource(R.string.to_price_title),
                        style = TextStyle(
                            fontSize = integerResource(id = R.integer.primary_text_size).sp,
                            color = colorResource(id = R.color.text_secondary)
                        )
                    )
                    TextField(
                        value = toPrice,
                        onValueChange = { changedPrice ->
                            toPrice = changedPrice
                        },
                        modifier = Modifier
                            .width(120.dp)
                            .height(56.dp),
                        placeholder = { Text(stringResource(R.string.end_price_placeholder)) },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = colorResource(
                                id = R.color.secondary_color
                            )
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                }
            }
            UsualTextField(
                title = stringResource(R.string.location_title),
                placeholder = stringResource(R.string.put_some_city_placeholder),
                value = location.value,
                onValueChange = { newValue -> location.value = newValue }
            )
            ButtonsRow(
                label = stringResource(R.string.bedrooms_title),
                values = listOf("Any", "1", "2", ">3"),
                value = bedrooms.value,
                onValueChange = { bedrooms.value = it }
            )
            ButtonsRow(
                label = stringResource(R.string.bathrooms_title),
                values = listOf("Any", "1", "2", ">3"),
                value = bathrooms.value,
                onValueChange = { bathrooms.value = it }
            )
            ButtonsRowMapped(
                label = stringResource(R.string.apartment_type_title),
                values = mapOf(
                    Pair("F", "Flat"),
                    Pair("DU", "Duplex"),
                    Pair("H", "House"),
                    Pair("DO", "Dorm")
                ),
                value = apartmentType.value,
                onValueChange = { apartmentType.value = it }
            )
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
    val viewModel: SearchRoomResultsViewModel = hiltViewModel()
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
                        text = stringResource(R.string.housing_results),
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
                                text = stringResource(R.string.sorry_nothing_here),
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
                    text = stringResource(R.string.something_went_wrong),
                    style = TextStyle(
                        fontSize = integerResource(
                            id = R.integer.primary_text_size
                        ).sp,
                        color = Color.Black,
                    )
                )
                GreenButtonOutline(text = stringResource(R.string.retry)) {
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
        mutableStateOf("0")
    }
    var toAge by remember {
        mutableStateOf("0")
    }
    val sleepTime = remember {
        mutableStateOf("N")
    }
    val personality = remember {
        mutableStateOf("E")
    }
    val smokingAttitude = remember {
        mutableStateOf("I")
    }
    val alcoholAttitude = remember {
        mutableStateOf("I")
    }
    val location = remember {
        mutableStateOf("")
    }
    val employment = remember {
        mutableStateOf("E")
    }
    val cleanHabits = remember {
        mutableStateOf("N")
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
                text = stringResource(R.string.show_results),
                onClick = {
                    if (fromAge > toAge) {
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
                    text = stringResource(R.string.search_filter),
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
                stringResource(R.string.choose_roommate_parameters),
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.Black
                ),
            )
            Text(
                stringResource(R.string.age_title),
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
                        stringResource(R.string.from_age_title),
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
                        placeholder = { Text(stringResource(R.string.start_age_placeholder)) },
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
                        stringResource(R.string.to_age_title),
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
                        placeholder = { Text(stringResource(R.string.end_age_placeholder)) },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = colorResource(
                                id = R.color.secondary_color
                            )
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                }
            }
            DropdownTextFieldMapped(
                mapOfItems = mapOf(
                    Pair("N", "Night"),
                    Pair("D", "Day"),
                    Pair("O", "Occasionally")
                ),
                label = stringResource(R.string.sleep_time_title),
                value = sleepTime.value,
                onValueChange = { sleepTime.value = it }
            )
            DropdownTextFieldMapped(
                label = stringResource(R.string.personality_type_title),
                mapOfItems = mapOf(
                    Pair("E", "Extraverted"),
                    Pair("I", "Introverted"),
                    Pair("M", "Mixed")
                ),
                value = personality.value,
                onValueChange = { personality.value = it }
            )
            DropdownTextFieldMapped(
                label = stringResource(R.string.attitude_to_smoking_title),
                mapOfItems = mapOf(
                    Pair("P", "Positive"),
                    Pair("N", "Negative"),
                    Pair("I", "Indifferent")
                ),
                value = smokingAttitude.value,
                onValueChange = { smokingAttitude.value = it }
            )
            DropdownTextFieldMapped(
                label = stringResource(R.string.attitude_to_alcohol_title),
                mapOfItems = mapOf(
                    Pair("P", "Positive"),
                    Pair("N", "Negative"),
                    Pair("I", "Indifferent")
                ),
                value = alcoholAttitude.value,
                onValueChange = { alcoholAttitude.value = it }
            )
            DropdownTextFieldMapped(
                mapOfItems = mapOf(
                    Pair("N", "Night"),
                    Pair("D", "Day"),
                    Pair("O", "Occasionally")
                ),
                label = stringResource(R.string.sleep_time_title),
                value = sleepTime.value,
                onValueChange = { sleepTime.value = it }
            )
            DropdownTextFieldMapped(
                mapOfItems = mapOf(
                    Pair("N", "Night"),
                    Pair("D", "Day"),
                    Pair("O", "Occasionally")
                ),
                label = stringResource(R.string.sleep_time_title),
                value = sleepTime.value,
                onValueChange = { sleepTime.value = it }
            )
            DropdownTextFieldMapped(
                mapOfItems = mapOf(
                    Pair("N", "Night"),
                    Pair("D", "Day"),
                    Pair("O", "Occasionally")
                ),
                label = stringResource(R.string.sleep_time_title),
                value = sleepTime.value,
                onValueChange = { sleepTime.value = it }
            )
            DropdownTextFieldMapped(
                mapOfItems = mapOf(
                    Pair("N", "Night"),
                    Pair("D", "Day"),
                    Pair("O", "Occasionally")
                ),
                label = stringResource(R.string.sleep_time_title),
                value = sleepTime.value,
                onValueChange = { sleepTime.value = it }
            )
            DropdownTextFieldMapped(
                mapOfItems = mapOf(
                    Pair("NE", "Not Employed"),
                    Pair("E", "Employed"),
                    Pair("S", "Searching For Work")
                ),
                label = stringResource(R.string.what_you_currently_do_title),
                value = employment.value,
                onValueChange = { employment.value = it }
            )
            DropdownTextFieldMapped(
                mapOfItems = mapOf(
                    Pair("N", "Neat"),
                    Pair("D", "It Depends"),
                    Pair("C", "Chaos")
                ),
                label = stringResource(R.string.clean_habits_title),
                value = cleanHabits.value,
                onValueChange = { cleanHabits.value = it }
            )
            InterestField(paddingValues = it, label = stringResource(R.string.interests))
        }
    }
}

@Destination
@Composable
fun SearchRoommateResults(
    navigator: DestinationsNavigator,
) {
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
    val viewModel: SearchRoommateResultViewModel = hiltViewModel()
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
                        text = stringResource(R.string.roommate_results),
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
                                text = stringResource(R.string.sorry_nothing_here),
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
                    text = stringResource(R.string.something_went_wrong),
                    style = TextStyle(
                        fontSize = integerResource(
                            id = R.integer.primary_text_size
                        ).sp,
                        color = Color.Black,
                    )
                )
                GreenButtonOutline(text = stringResource(R.string.retry)) {
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
