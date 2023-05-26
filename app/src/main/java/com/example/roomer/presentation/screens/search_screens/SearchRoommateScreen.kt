package com.example.roomer.presentation.screens.search_screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.roomer.R
import com.example.roomer.domain.model.login_sign_up.InterestModel
import com.example.roomer.presentation.screens.destinations.HomeScreenDestination
import com.example.roomer.presentation.screens.destinations.SearchRoomScreenDestination
import com.example.roomer.presentation.screens.destinations.SearchRoommateResultsDestination
import com.example.roomer.presentation.screens.entrance.signup.interests_screen.InterestsScreenViewModel
import com.example.roomer.presentation.ui_components.BackBtn
import com.example.roomer.presentation.ui_components.ButtonsRowMapped
import com.example.roomer.presentation.ui_components.CitiesListViewModel
import com.example.roomer.presentation.ui_components.DropdownTextFieldListed
import com.example.roomer.presentation.ui_components.DropdownTextFieldMapped
import com.example.roomer.presentation.ui_components.FilterSelect
import com.example.roomer.presentation.ui_components.GreenButtonOutline
import com.example.roomer.presentation.ui_components.InterestField
import com.example.roomer.utils.Constants
import com.example.roomer.utils.Constants.Options.attitudeOptions
import com.example.roomer.utils.Constants.Options.cleanOptions
import com.example.roomer.utils.Constants.Options.employmentOptions
import com.example.roomer.utils.Constants.Options.personalityOptions
import com.example.roomer.utils.Constants.Options.sexOptions
import com.example.roomer.utils.Constants.Options.sleepOptions
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun SearchRoommateScreen(
    navigator: DestinationsNavigator,
    interestsScreenViewModel: InterestsScreenViewModel = hiltViewModel(),
    citiesListViewModel: CitiesListViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val sex = remember {
        mutableStateOf("A`")
    }
    var fromAge by remember {
        mutableStateOf("0")
    }
    var toAge by remember {
        mutableStateOf("100")
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
    var interests: List<InterestModel> = listOf()

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
                    .padding(
                        start = dimensionResource(id = R.dimen.result_button_start_margin),
                        bottom = dimensionResource(id = R.dimen.result_button_bottom_margin)
                    )
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.result_button_height)),
                text = stringResource(R.string.show_results)
            ) {
                if (fromAge.toInt() > toAge.toInt()) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.to_age_less_than_from_age),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    navigator.navigate(
                        SearchRoommateResultsDestination(
                            sex.value,
                            location.value,
                            fromAge,
                            toAge,
                            employment.value,
                            alcoholAttitude.value,
                            smokingAttitude.value,
                            sleepTime.value,
                            personality.value,
                            cleanHabits.value,
                            interests.joinToString("\n") { it.id.toString() }
                        )
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(
                    bottom = it.calculateBottomPadding() +
                        dimensionResource(R.dimen.column_bottom_margin)
                )
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.list_elements_margin)
            )
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
                selectItemName = stringResource(R.string.roommate),
                onClick = { navigator.navigate(SearchRoomScreenDestination) }
            )
            Text(
                stringResource(R.string.choose_roommate_parameters),
                style = TextStyle(
                    fontSize = integerResource(id = R.integer.big_text).sp,
                    color = Color.Black
                )
            )
            ButtonsRowMapped(
                label = stringResource(R.string.sex),
                values = sexOptions.mapValues { (_, value) ->
                    stringResource(id = value)
                },
                value = sex.value,
                onValueChange = { if (it != "") sex.value = it }
            )
            Text(
                stringResource(R.string.age_label),
                style = TextStyle(
                    fontSize = integerResource(id = R.integer.primary_text).sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium
                )
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
                            if (changedText.toIntOrNull() != null) {
                                fromAge = changedText
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
                        placeholder = { Text(stringResource(R.string.start_age_placeholder)) },
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
                            if (changedText.toIntOrNull() != null) {
                                toAge = changedText
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
                        placeholder = { Text(stringResource(R.string.end_age_placeholder)) },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = colorResource(
                                id = R.color.secondary_color
                            )
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }
            DropdownTextFieldListed(
                listOfItems = citiesListViewModel.cities.value.map { it.city },
                label = stringResource(id = R.string.choose_city_label),
                value = location.value,
                onValueChange = { location.value = it },
                itemsAmountAtOnce = Constants.CITIES_SHOWN_AT_ONCE
            )
            DropdownTextFieldMapped(
                mapOfItems = sleepOptions.mapValues { (_, value) ->
                    stringResource(id = value)
                },
                label = stringResource(R.string.sleep_time_label),
                value = sleepTime.value,
                onValueChange = { sleepTime.value = it }
            )
            DropdownTextFieldMapped(
                label = stringResource(R.string.personality_type_label),
                mapOfItems = personalityOptions.mapValues { (_, value) ->
                    stringResource(id = value)
                },
                value = personality.value,
                onValueChange = { personality.value = it }
            )
            DropdownTextFieldMapped(
                label = stringResource(R.string.attitude_to_smoking_label),
                mapOfItems = attitudeOptions.mapValues { (_, value) ->
                    stringResource(id = value)
                },
                value = smokingAttitude.value,
                onValueChange = { smokingAttitude.value = it }
            )
            DropdownTextFieldMapped(
                label = stringResource(R.string.attitude_to_alcohol_label),
                mapOfItems = attitudeOptions.mapValues { (_, value) ->
                    stringResource(id = value)
                },
                value = alcoholAttitude.value,
                onValueChange = { alcoholAttitude.value = it }
            )
            DropdownTextFieldMapped(
                mapOfItems = employmentOptions.mapValues { (_, value) ->
                    stringResource(id = value)
                },
                label = stringResource(R.string.what_you_currently_do_lable),
                value = employment.value,
                onValueChange = { employment.value = it }
            )
            DropdownTextFieldMapped(
                mapOfItems = cleanOptions.mapValues { (_, value) ->
                    stringResource(id = value)
                },
                label = stringResource(R.string.clean_habits_label),
                value = cleanHabits.value,
                onValueChange = { cleanHabits.value = it }
            )

            val interestsList = interestsScreenViewModel.interests.value
            InterestField(
                paddingValues = it,
                label = stringResource(R.string.interests),
                values = interestsList,
                selectedItems = interests,
                onSelectedChange = { interests = it }
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer)))
        }
    }
}
