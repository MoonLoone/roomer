package com.example.roomer.presentation.screens.search_screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomer.R
import com.example.roomer.presentation.screens.destinations.HomeScreenDestination
import com.example.roomer.presentation.screens.destinations.SearchRoomScreenDestination
import com.example.roomer.presentation.screens.destinations.SearchRoommateScreenDestination
import com.example.roomer.presentation.ui_components.BackBtn
import com.example.roomer.presentation.ui_components.DropdownTextFieldMapped
import com.example.roomer.presentation.ui_components.FilterSelect
import com.example.roomer.presentation.ui_components.GreenButtonOutline
import com.example.roomer.presentation.ui_components.InterestField
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

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
                text = "Show results",
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
                    text = "Search filter",
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(
                        fontSize = integerResource(
                            id = R.integer.label_text
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
                            fontSize = integerResource(id = R.integer.primary_text).sp,
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
                            fontSize = integerResource(id = R.integer.primary_text).sp,
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
            DropdownTextFieldMapped(
                mapOfItems = mapOf(
                    Pair("N", "Night"),
                    Pair("D", "Day"),
                    Pair("O", "Occasionally")
                ),
                label = "Sleep time",
                value = sleepTime.value,
                onValueChange = { sleepTime.value = it }
            )
            DropdownTextFieldMapped(
                label = "Personality type",
                mapOfItems = mapOf(
                    Pair("E", "Extraverted"),
                    Pair("I", "Introverted"),
                    Pair("M", "Mixed")
                ),
                value = personality.value,
                onValueChange = { personality.value = it }
            )
            DropdownTextFieldMapped(
                label = "Attitude to smoking",
                mapOfItems = mapOf(
                    Pair("P", "Positive"),
                    Pair("N", "Negative"),
                    Pair("I", "Indifferent")
                ),
                value = smokingAttitude.value,
                onValueChange = { smokingAttitude.value = it }
            )
            DropdownTextFieldMapped(
                label = "Attitude to alcohol",
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
                label = "Sleep time",
                value = sleepTime.value,
                onValueChange = { sleepTime.value = it }
            )
            DropdownTextFieldMapped(
                mapOfItems = mapOf(
                    Pair("N", "Night"),
                    Pair("D", "Day"),
                    Pair("O", "Occasionally")
                ),
                label = "Sleep time",
                value = sleepTime.value,
                onValueChange = { sleepTime.value = it }
            )
            DropdownTextFieldMapped(
                mapOfItems = mapOf(
                    Pair("N", "Night"),
                    Pair("D", "Day"),
                    Pair("O", "Occasionally")
                ),
                label = "Sleep time",
                value = sleepTime.value,
                onValueChange = { sleepTime.value = it }
            )
            DropdownTextFieldMapped(
                mapOfItems = mapOf(
                    Pair("N", "Night"),
                    Pair("D", "Day"),
                    Pair("O", "Occasionally")
                ),
                label = "Sleep time",
                value = sleepTime.value,
                onValueChange = { sleepTime.value = it }
            )
            DropdownTextFieldMapped(
                mapOfItems = mapOf(
                    Pair("NE", "Not Employed"),
                    Pair("E", "Employed"),
                    Pair("S", "Searching For Work")
                ),
                label = "What you currently do?",
                value = employment.value,
                onValueChange = { employment.value = it }
            )
            DropdownTextFieldMapped(
                mapOfItems = mapOf(
                    Pair("N", "Neat"),
                    Pair("D", "It Depends"),
                    Pair("C", "Chaos")
                ),
                label = "Clean habits",
                value = cleanHabits.value,
                onValueChange = { cleanHabits.value = it }
            )
            InterestField(paddingValues = it, label = "Interests")
        }
    }
}
