package com.example.roomer.presentation.screens.search_screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
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
import com.example.roomer.utils.Constants.Options.apartmentOptions
import com.example.roomer.utils.Constants.Options.roomsCountOptions
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun SearchRoomScreen(
    navigator: DestinationsNavigator
) {
    val context = LocalContext.current
    var fromPrice by remember {
        mutableStateOf(context.getString(R.string.default_from_price))
    }
    var toPrice by remember {
        mutableStateOf(context.getString(R.string.default_to_price))
    }
    val location = remember {
        mutableStateOf("")
    }
    val bedrooms = remember {
        mutableStateOf(context.getString(R.string.default_bedrooms))
    }
    val bathrooms = remember {
        mutableStateOf(context.getString(R.string.default_bathrooms))
    }
    val apartmentType = remember {
        mutableStateOf(context.getString(R.string.default_apartment))
    }

    Scaffold(
        modifier = Modifier.padding(
            start = dimensionResource(id = R.dimen.screen_start_margin),
            end = dimensionResource(id = R.dimen.screen_end_margin),
            top = dimensionResource(id = R.dimen.screen_top_margin),
            bottom = dimensionResource(id = R.dimen.screen_nav_bottom_margin)
        ),
        floatingActionButton = {
            GreenButtonOutline(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(id = R.dimen.result_button_start_margin),
                        bottom = dimensionResource(id = R.dimen.result_button_bottom_margin)
                    )
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(
                            dimensionResource(id = R.dimen.rounded_corner_full)
                        )
                    )
                    .height(
                        dimensionResource(id = R.dimen.screen_nav_bottom_margin)
                    ),
                text = stringResource(R.string.show_results),
                onClick = {
                    if (fromPrice.toInt() > toPrice.toInt()) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.to_price_less_than_from_price),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        navigator.navigate(
                            SearchRoomResultsDestination(
                                fromPrice,
                                toPrice,
                                location.value,
                                bedrooms.value,
                                bathrooms.value,
                                apartmentType.value
                            )
                        )
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
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.list_elements_margin)
            )
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                BackBtn(onBackNavigation = {
                    navigator.navigate(HomeScreenDestination)
                })
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
                selectItemName = stringResource(R.string.room),
                onNavigateToFriends = { navigator.navigate(SearchRoommateScreenDestination) }
            )
            Text(
                stringResource(R.string.choose_room_parameters),
                style = TextStyle(
                    fontSize = integerResource(id = R.integer.big_text).sp,
                    color = Color.Black
                )
            )
            Text(
                stringResource(R.string.month_price),
                style = TextStyle(
                    fontSize = integerResource(id = R.integer.primary_text).sp,
                    color = colorResource(id = R.color.black),
                    fontWeight = FontWeight.Medium
                )
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        stringResource(R.string.from_price_label),
                        style = TextStyle(
                            fontSize = integerResource(id = R.integer.primary_text).sp,
                            color = colorResource(
                                id = R.color.text_secondary
                            )
                        )
                    )
                    TextField(
                        value = fromPrice,
                        onValueChange = { changedPrice ->
                            if (changedPrice.toIntOrNull() != null) {
                                fromPrice = changedPrice
                            } else {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.not_integer),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        modifier = Modifier
                            .width(dimensionResource(id = R.dimen.num_field_width))
                            .height(dimensionResource(id = R.dimen.num_field_height)),
                        placeholder = { Text(stringResource(R.string.start_price_placeholder)) },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = colorResource(
                                id = R.color.secondary_color
                            )
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
                Column {
                    Text(
                        stringResource(R.string.to_price_label),
                        style = TextStyle(
                            fontSize = integerResource(id = R.integer.primary_text).sp,
                            color = colorResource(id = R.color.text_secondary)
                        )
                    )
                    TextField(
                        value = toPrice,
                        onValueChange = { changedPrice ->
                            if (changedPrice.toIntOrNull() != null) {
                                toPrice = changedPrice
                            } else {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.not_integer),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        modifier = Modifier
                            .width(dimensionResource(id = R.dimen.num_field_width))
                            .height(dimensionResource(id = R.dimen.num_field_height)),
                        placeholder = { Text(stringResource(R.string.end_price_placeholder)) },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = colorResource(
                                id = R.color.secondary_color
                            )
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }
            UsualTextField(
                title = stringResource(R.string.location_label),
                placeholder = stringResource(R.string.put_some_city_placeholder),
                value = location.value,
                onValueChange = { location.value = it }
            )
            ButtonsRow(
                label = stringResource(R.string.bedrooms_label),
                values = roomsCountOptions.map { value -> stringResource(id = value) },
                value = bedrooms.value,
                onValueChange = { bedrooms.value = it }
            )
            ButtonsRow(
                label = stringResource(R.string.bathrooms_label),
                values = roomsCountOptions.map { value -> stringResource(id = value) },
                value = bathrooms.value,
                onValueChange = { bathrooms.value = it }
            )
            ButtonsRowMapped(
                label = stringResource(R.string.apartment_type_label),
                values = apartmentOptions.mapValues { (_, value) ->
                    stringResource(id = value)
                },
                value = apartmentType.value,
                onValueChange = { apartmentType.value = it }
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer)))
        }
    }
}
