package com.example.roomer.presentation.screens.search_screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.roomer.presentation.screens.destinations.SearchRoomResultsDestination
import com.example.roomer.presentation.screens.destinations.SearchRoommateScreenDestination
import com.example.roomer.presentation.ui_components.BackBtn
import com.example.roomer.presentation.ui_components.ButtonsRow
import com.example.roomer.presentation.ui_components.ButtonsRowMapped
import com.example.roomer.presentation.ui_components.FilterSelect
import com.example.roomer.presentation.ui_components.GreenButtonOutline
import com.example.roomer.presentation.ui_components.UsualTextField
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

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
        mutableStateOf("")
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
        modifier = Modifier.padding(start = 40.dp, end = 40.dp, top = 16.dp, bottom = 40.dp),
        floatingActionButton = {
            GreenButtonOutline(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, bottom = 40.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(100.dp))
                    .height(40.dp),
                text = "Show results",
                onClick = {
                    if (fromPrice > toPrice) {
                        Toast.makeText(context, "To price less than from price", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        navigator.navigate(SearchRoomResultsDestination(fromPrice,
                            toPrice, location.value, bedrooms.value,
                            bathrooms.value, apartmentType.value))
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
                        onValueChange = { changedPrice ->
                            fromPrice = changedPrice
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
            UsualTextField(
                title = "Location",
                placeholder = "Put some city, street",
                value = location.value,
                onValueChange = { location.value = it }
            )
            ButtonsRow(
                label = "Bedrooms",
                values = listOf("Any", "1", "2", ">3"),
                value = bedrooms.value,
                onValueChange = { bedrooms.value = it }
            )
            ButtonsRow(
                label = "Bathrooms",
                values = listOf("Any", "1", "2", ">3"),
                value = bathrooms.value,
                onValueChange = { bathrooms.value = it }
            )
            ButtonsRowMapped(
                label = "Apartment Type",
                values = mapOf(
                    Pair("F", "Flat"),
                    Pair("DU", "Duplex"),
                    Pair("H", "House"),
                    Pair("DO", "Dorm")
                ),
                value = apartmentType.value,
                onValueChange = { apartmentType.value = it }
            )
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}
