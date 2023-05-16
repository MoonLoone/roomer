package com.example.roomer.presentation.screens.entrance.signup.about_me_avatar_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.roomer.R
import com.example.roomer.presentation.screens.destinations.HabitsScreenDestination
import com.example.roomer.presentation.screens.destinations.PrimaryUserInfoScreenDestination
import com.example.roomer.presentation.screens.entrance.signup.SignUpViewModel
import com.example.roomer.presentation.ui_components.CitiesListViewModel
import com.example.roomer.presentation.ui_components.DropdownTextFieldListed
import com.example.roomer.presentation.ui_components.DropdownTextFieldMapped
import com.example.roomer.presentation.ui_components.GreenButtonPrimary
import com.example.roomer.presentation.ui_components.ProfilePicture
import com.example.roomer.presentation.ui_components.SimpleAlertDialog
import com.example.roomer.presentation.ui_components.UsualTextField
import com.example.roomer.utils.Constants
import com.example.roomer.utils.navigation.SignUpNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@SignUpNavGraph
@Destination
@Composable
fun AboutMeAvatarScreen(
    navigator: DestinationsNavigator,
    signUpViewModel: SignUpViewModel,
    citiesListViewModel: CitiesListViewModel = hiltViewModel()
) {
    val uiState by signUpViewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val columnScroll = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) { focusManager.clearFocus() }
            .padding(
                start = dimensionResource(id = R.dimen.screen_start_margin),
                end = dimensionResource(id = R.dimen.screen_end_margin)
            ),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(columnScroll),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(
                    dimensionResource(id = R.dimen.list_elements_margin)
                )
            ) {
                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.sign_up_top_padding)))
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = colorResource(id = R.color.primary_dark),
                    progress = 0.4f
                )
                Text(
                    text = stringResource(R.string.just_basic_profile_info),
                    fontSize = integerResource(id = R.integer.label_text).sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = stringResource(R.string.add_profile_picture),
                    fontSize = integerResource(id = R.integer.primary_text).sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Start
                )
                ProfilePicture(
                    bitmapValue = signUpViewModel.avatar,
                    onBitmapValueChange = {
                        signUpViewModel.avatar = it
                    }
                )
                UsualTextField(
                    title = stringResource(R.string.write_something_about_you),
                    placeholder = stringResource(R.string.about_me),
                    value = signUpViewModel.personDescription,
                    onValueChange = {
                        signUpViewModel.personDescription = it
                    }
                )
                DropdownTextFieldListed(
                    listOfItems = citiesListViewModel.cities.value.map { it.city },
                    label = stringResource(R.string.choose_city_label),
                    value = signUpViewModel.city,
                    onValueChange = { signUpViewModel.city = it },
                    itemsAmountAtOnce = Constants.citiesShownAtOnce
                )
                DropdownTextFieldMapped(
                    mapOfItems = mapOf(
                        Pair("NE", stringResource(R.string.not_employed)),
                        Pair("E", stringResource(R.string.employed)),
                        Pair("S", stringResource(R.string.searching_for_work))
                    ),
                    label = stringResource(R.string.what_you_currently_do_lable),
                    value = signUpViewModel.employment,
                    onValueChange = {
                        signUpViewModel.employment = it
                    }
                )
                Spacer(modifier = Modifier
                    .size(dimensionResource(id = R.dimen.sign_up_bottom_padding)))
                if (uiState.isValid) {
                    signUpViewModel.clearState()
                    navigator.navigate(HabitsScreenDestination)
                }
                if (uiState.isError) {
                    SimpleAlertDialog(
                        title = stringResource(R.string.login_alert_dialog_text),
                        text = uiState.errorMessage
                    ) { signUpViewModel.clearError() }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(id = R.dimen.back_further_buttons_padding))
            ,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            GreenButtonPrimary(
                text = stringResource(R.string.back_button_label)
            ) {
                navigator.navigate(PrimaryUserInfoScreenDestination)
            }
            GreenButtonPrimary(
                text = stringResource(R.string.continue_button_label)
            ) {
                signUpViewModel.aboutMeAvatarScreenValidate()
            }
        }
    }

}
