package com.example.roomer.presentation.screens.profile_nested_screens.account_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.roomer.R
import com.example.roomer.presentation.screens.destinations.ProfileScreenDestination
import com.example.roomer.presentation.screens.entrance.signup.interests_screen.InterestsScreenViewModel
import com.example.roomer.presentation.ui_components.BackBtn
import com.example.roomer.presentation.ui_components.ButtonsRowMapped
import com.example.roomer.presentation.ui_components.CitiesListViewModel
import com.example.roomer.presentation.ui_components.DateField
import com.example.roomer.presentation.ui_components.DropdownTextFieldListed
import com.example.roomer.presentation.ui_components.DropdownTextFieldMapped
import com.example.roomer.presentation.ui_components.GreenButtonPrimary
import com.example.roomer.presentation.ui_components.InterestField
import com.example.roomer.presentation.ui_components.SexField
import com.example.roomer.presentation.ui_components.SimpleAlertDialog
import com.example.roomer.presentation.ui_components.UsualTextField
import com.example.roomer.utils.Constants
import com.example.roomer.utils.NavbarManagement
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun AccountScreen(
    navigator: DestinationsNavigator,
    viewModel: AccountScreenViewModel = hiltViewModel(),
    interestsViewModel: InterestsScreenViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val columnScroll = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    val uiState by viewModel.uiState.collectAsState()

    NavbarManagement.hideNavbar()

    Scaffold(scaffoldState = scaffoldState) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = dimensionResource(id = R.dimen.screen_start_margin),
                    end = dimensionResource(id = R.dimen.screen_end_margin)
                ),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .clickable(
                        indication = null,
                        interactionSource = interactionSource
                    ) { focusManager.clearFocus() },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(columnScroll),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        dimensionResource(id = R.dimen.list_elements_margin)
                    )
                ) {
                    Spacer(
                        modifier = Modifier.size(
                            dimensionResource(id = R.dimen.account_content_top_padding)
                        )
                    )
                    AccountTitle(navigator = navigator)
                    PrimaryUserInfoFields(viewModel = viewModel)
                    AboutMeCityFields(viewModel = viewModel)
                    HabitsFields(viewModel = viewModel)
                    InterestField(
                        paddingValues = PaddingValues(
                            dimensionResource(id = R.dimen.account_interests_padding_values)
                        ),
                        label = stringResource(R.string.interests),
                        values = interestsViewModel.interests.value,
                        selectedItems = viewModel.interests,
                        onSelectedChange = { viewModel.interests = it }
                    )
                    Spacer(
                        modifier = Modifier.size(
                            dimensionResource(id = R.dimen.account_content_bottom_padding)
                        )
                    )
                }
            }
            if (uiState.error.isNotEmpty()) {
                SimpleAlertDialog(
                    title = stringResource(R.string.login_alert_dialog_text),
                    text = uiState.error
                ) { viewModel.clearState() }
            }

            if (uiState.success) {
                val message = stringResource(id = R.string.account_data_saved_message)
                LaunchedEffect(key1 = null) {
                    scaffoldState.snackbarHostState.showSnackbar(message = message)
                    viewModel.clearState()
                }
            }

            if (uiState.isLoading) LoadingView()

            GreenButtonPrimary(
                text = stringResource(R.string.account_confirm_btn_text),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = dimensionResource(id = R.dimen.account_confirm_btn_bottom_padding)
                    )
            ) {
                viewModel.updateData()
            }
        }
    }
}

@Composable
private fun LoadingView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = Modifier.size(dimensionResource(id = R.dimen.ordinary_image)),
            color = colorResource(id = R.color.primary)
        )
    }
}

@Composable
private fun AccountTitle(
    navigator: DestinationsNavigator
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BackBtn(onBackNavigation = { navigator.navigate(ProfileScreenDestination) })
        Text(
            text = stringResource(R.string.account_label),
            fontSize = integerResource(
                id = R.integer.label_text
            ).sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun PrimaryUserInfoFields(viewModel: AccountScreenViewModel) {
    UsualTextField(
        title = stringResource(R.string.first_name),
        placeholder = stringResource(R.string.first_name_placeholder),
        value = viewModel.firstName,
        onValueChange = {
            viewModel.firstName = it
        }
    )
    UsualTextField(
        title = stringResource(R.string.last_name),
        placeholder = stringResource(R.string.last_name_placeholder),
        value = viewModel.lastName,
        onValueChange = {
            viewModel.lastName = it
        }
    )
    DateField(
        label = stringResource(R.string.date_of_birth),
        value = viewModel.birthDate,
        onValueChange = {
            viewModel.birthDate = it
        }
    )
    SexField(
        value = viewModel.sex,
        onValueChange = {
            viewModel.sex = it
        }
    )
}

@Composable
private fun AboutMeCityFields(
    viewModel: AccountScreenViewModel,
    citiesListViewModel: CitiesListViewModel = hiltViewModel()
) {
    UsualTextField(
        title = stringResource(R.string.write_something_about_you),
        placeholder = stringResource(R.string.about_me),
        value = viewModel.personDescription,
        onValueChange = {
            viewModel.personDescription = it
        }
    )
    DropdownTextFieldListed(
        listOfItems = citiesListViewModel.cities.value.map { it.city },
        label = stringResource(R.string.choose_city_label),
        value = viewModel.city,
        onValueChange = { viewModel.city = it },
        itemsAmountAtOnce = Constants.CITIES_SHOWN_AT_ONCE
    )
    DropdownTextFieldMapped(
        mapOfItems = mapOf(
            Pair("NE", stringResource(R.string.not_employed)),
            Pair("E", stringResource(R.string.employed)),
            Pair("S", stringResource(R.string.searching_for_work))
        ),
        label = stringResource(R.string.what_you_currently_do_lable),
        value = viewModel.employment,
        onValueChange = {
            viewModel.employment = it
        }
    )
}

@Composable
private fun HabitsFields(
    viewModel: AccountScreenViewModel
) {
    ButtonsRowMapped(
        label = stringResource(R.string.your_usual_sleep_time),
        values = mapOf(
            Pair("N", stringResource(R.string.night)),
            Pair("D", stringResource(R.string.day)),
            Pair("O", stringResource(R.string.occasionally))
        ),
        value = viewModel.sleepTime,
        onValueChange = {
            viewModel.sleepTime = it
        }
    )
    ButtonsRowMapped(
        label = stringResource(R.string.attitude_to_alcohol_label),
        values = mapOf(
            Pair("P", stringResource(R.string.positive)),
            Pair("N", stringResource(R.string.negative)),
            Pair("I", stringResource(R.string.indifferent))
        ),
        value = viewModel.alcoholAttitude,
        onValueChange = {
            viewModel.alcoholAttitude = it
        }
    )
    ButtonsRowMapped(
        label = stringResource(R.string.attitude_to_smoking_label),
        values = mapOf(
            Pair("P", stringResource(R.string.positive)),
            Pair("N", stringResource(R.string.negative)),
            Pair("I", stringResource(R.string.indifferent))
        ),
        value = viewModel.smokingAttitude,
        onValueChange = {
            viewModel.smokingAttitude = it
        }
    )
    ButtonsRowMapped(
        label = stringResource(R.string.personality_type_label),
        values = mapOf(
            Pair("E", stringResource(R.string.extraverted)),
            Pair("I", stringResource(R.string.introverted)),
            Pair("M", stringResource(R.string.mixed))
        ),
        value = viewModel.personalityType,
        onValueChange = {
            viewModel.personalityType = it
        }
    )
    DropdownTextFieldMapped(
        mapOfItems = mapOf(
            Pair("N", stringResource(R.string.neat)),
            Pair("D", stringResource(R.string.it_depends)),
            Pair("C", stringResource(R.string.chaos))
        ),
        label = stringResource(R.string.clean_habits_label),
        value = viewModel.cleanHabits,
        onValueChange = {
            viewModel.cleanHabits = it
        }
    )
}
