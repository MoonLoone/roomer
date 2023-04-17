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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
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
        modifier = Modifier.padding(
            top = dimensionResource(id = R.dimen.screen_top_margin),
            start = dimensionResource(id = R.dimen.screen_start_margin),
            end = dimensionResource(id = R.dimen.screen_end_margin),
            bottom = dimensionResource(id = R.dimen.screen_bottom_margin),
        ),
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
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.list_elements_margin)
            ),
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                BackBtn(onBackNavigation = { navigator.navigate(HomeScreenDestination) })
                Text(
                    text = stringResource(R.string.search_filter),
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
                stringResource(R.string.choose_roommate_parameters),
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.Black
                ),
            )
            Text(
                stringResource(R.string.age_label),
                style = TextStyle(
                    fontSize = integerResource(id = R.integer.primary_text).sp,
                    color = Color.Black
                ),
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        stringResource(R.string.from_age_label),
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
                        stringResource(R.string.to_age_label),
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
                    Pair("N", stringResource(R.string.night)),
                    Pair("D", stringResource(R.string.day)),
                    Pair("O", stringResource(R.string.occasionally))
                ),
                label = stringResource(R.string.sleep_time_label),
                value = sleepTime.value,
                onValueChange = { sleepTime.value = it }
            )
            DropdownTextFieldMapped(
                label = stringResource(R.string.personality_type_label),
                mapOfItems = mapOf(
                    Pair("E", stringResource(R.string.extraverted)),
                    Pair("I", stringResource(R.string.introverted)),
                    Pair("M", stringResource(R.string.mixed))
                ),
                value = personality.value,
                onValueChange = { personality.value = it }
            )
            DropdownTextFieldMapped(
                label = stringResource(R.string.attitude_to_smoking_label),
                mapOfItems = mapOf(
                    Pair("P", stringResource(R.string.positive)),
                    Pair("N", stringResource(R.string.negative)),
                    Pair("I", stringResource(R.string.indifferent))
                ),
                value = smokingAttitude.value,
                onValueChange = { smokingAttitude.value = it }
            )
            DropdownTextFieldMapped(
                label = stringResource(R.string.attitude_to_alcohol_label),
                mapOfItems = mapOf(
                    Pair("P", stringResource(R.string.positive)),
                    Pair("N", stringResource(R.string.negative)),
                    Pair("I", stringResource(R.string.indifferent))
                ),
                value = alcoholAttitude.value,
                onValueChange = { alcoholAttitude.value = it }
            )
            DropdownTextFieldMapped(
                mapOfItems = mapOf(
                    Pair("N", stringResource(R.string.night)),
                    Pair("D", stringResource(R.string.day)),
                    Pair("O", stringResource(R.string.occasionally))
                ),
                label = stringResource(R.string.sleep_time_label),
                value = sleepTime.value,
                onValueChange = { sleepTime.value = it }
            )
            DropdownTextFieldMapped(
                mapOfItems = mapOf(
                    Pair("N", stringResource(R.string.night)),
                    Pair("D", stringResource(R.string.day)),
                    Pair("O", stringResource(R.string.occasionally))
                ),
                label = stringResource(R.string.sleep_time_label),
                value = sleepTime.value,
                onValueChange = { sleepTime.value = it }
            )
            DropdownTextFieldMapped(
                mapOfItems = mapOf(
                    Pair("N", stringResource(R.string.night)),
                    Pair("D", stringResource(R.string.day)),
                    Pair("O", stringResource(R.string.occasionally))
                ),
                label = stringResource(R.string.sleep_time_label),
                value = sleepTime.value,
                onValueChange = { sleepTime.value = it }
            )
            DropdownTextFieldMapped(
                mapOfItems = mapOf(
                    Pair("N", stringResource(R.string.night)),
                    Pair("D", stringResource(R.string.day)),
                    Pair("O", stringResource(R.string.occasionally))
                ),
                label = stringResource(R.string.sleep_time_label),
                value = sleepTime.value,
                onValueChange = { sleepTime.value = it }
            )
            DropdownTextFieldMapped(
                mapOfItems = mapOf(
                    Pair("NE", stringResource(R.string.not_employed)),
                    Pair("E", stringResource(R.string.employed)),
                    Pair("S", stringResource(R.string.searching_for_work))
                ),
                label = stringResource(R.string.what_you_currently_do_lable),
                value = employment.value,
                onValueChange = { employment.value = it }
            )
            DropdownTextFieldMapped(
                mapOfItems = mapOf(
                    Pair("N", stringResource(R.string.neat)),
                    Pair("D", stringResource(R.string.it_depends)),
                    Pair("C", stringResource(R.string.chaos))
                ),
                label = stringResource(R.string.clean_habits_label),
                value = cleanHabits.value,
                onValueChange = { cleanHabits.value = it }
            )
            InterestField(paddingValues = it, label = stringResource(R.string.interests))
        }
    }
}
