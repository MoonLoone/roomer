package com.example.roomer.presentation.screens.search_screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
        modifier = Modifier.padding(
            top = dimensionResource(id = R.dimen.screen_top_margin),
            start = dimensionResource(id = R.dimen.screen_start_margin),
            end = dimensionResource(id = R.dimen.screen_end_margin),
        ),
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
                },
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.list_elements_margin),
            ),
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                BackBtn(
                    onBackNavigation = {
                        navigator.navigate(HomeScreenDestination)
                    },
                )
                Text(
                    text = stringResource(R.string.search_filter),
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(
                        fontSize = integerResource(
                            id = R.integer.label_text,
                        ).sp,
                        color = Color.Black,
                    ),
                    textAlign = TextAlign.Center,
                )
            }
            FilterSelect(
                selectItemName = "Room",
                onNavigateToFriends = { navigator.navigate(SearchRoommateScreenDestination) },
            )
            Text(
                stringResource(R.string.choose_room_parameters),
                style = TextStyle(
                    fontSize = integerResource(id = R.integer.big_text).sp,
                    color = Color.Black,
                ),
            )
            Text(
                stringResource(R.string.month_price),
                style = TextStyle(
                    fontSize = integerResource(id = R.integer.primary_text).sp,
                    color = Color.Black,
                ),
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Text(
                        stringResource(R.string.from_price_label),
                        style = TextStyle(
                            fontSize = integerResource(id = R.integer.primary_text).sp,
                            color = colorResource(
                                id = R.color.text_secondary,
                            ),
                        ),
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
                                id = R.color.secondary_color,
                            ),
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                }
                Column {
                    Text(
                        stringResource(R.string.to_price_label),
                        style = TextStyle(
                            fontSize = integerResource(id = R.integer.primary_text).sp,
                            color = colorResource(id = R.color.text_secondary),
                        ),
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
                                id = R.color.secondary_color,
                            ),
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                }
            }
            UsualTextField(
                title = stringResource(R.string.location_label),
                placeholder = stringResource(R.string.put_some_city_placeholder),
                value = location.value,
                onValueChange = { newValue -> location.value = newValue },
            )
            ButtonsRow(
                label = stringResource(R.string.bedrooms_label),
                values = listOf(stringResource(R.string.any), "1", "2", ">3"),
                value = bedrooms.value,
                onValueChange = { bedrooms.value = it },
            )
            ButtonsRow(
                label = stringResource(R.string.bathrooms_label),
                values = listOf(stringResource(R.string.any), "1", "2", ">3"),
                value = bathrooms.value,
                onValueChange = { bathrooms.value = it },
            )
            ButtonsRowMapped(
                label = stringResource(R.string.apartment_type_label),
                values = mapOf(
                    Pair("F", stringResource(R.string.flat)),
                    Pair("DU", stringResource(R.string.duplex)),
                    Pair("H", stringResource(R.string.house)),
                    Pair("DO", stringResource(R.string.dorm))
                ),
                value = apartmentType.value,
                onValueChange = { apartmentType.value = it },
            )
        }
    }
}
