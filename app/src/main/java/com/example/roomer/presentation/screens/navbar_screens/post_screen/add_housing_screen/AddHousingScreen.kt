package com.example.roomer.presentation.screens.navbar_screens.post_screen.add_housing_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.roomer.R
import com.example.roomer.presentation.screens.destinations.PostScreenDestination
import com.example.roomer.presentation.ui_components.BackBtn
import com.example.roomer.presentation.ui_components.BasicConfirmDialog
import com.example.roomer.presentation.ui_components.ButtonsRow
import com.example.roomer.presentation.ui_components.ButtonsRowMapped
import com.example.roomer.presentation.ui_components.GreenButtonPrimary
import com.example.roomer.presentation.ui_components.HousingPhotosComponent
import com.example.roomer.presentation.ui_components.SimpleAlertDialog
import com.example.roomer.presentation.ui_components.UsualTextField
import com.example.roomer.utils.Constants
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

/**
 * The Add Housing Screen.
 * Allows users to add their housing advertisements.
 *
 * @author Andrey Karanik
 */

@Destination
@Composable
fun AddHousingScreen(
    navigator: DestinationsNavigator,
    viewModel: AddHousingScreenViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    if (state.isSuccess) {
        navigator.navigate(PostScreenDestination)
    }
    if (state.isError) {
        SimpleAlertDialog(
            title = stringResource(R.string.login_alert_dialog_text),
            text = state.errorMessage
        ) {
            viewModel.clearState()
        }
    }
    if (state.isInternetProblem) {
        SimpleAlertDialog(
            title = stringResource(R.string.login_alert_dialog_text),
            text = state.errorMessage
        ) {
            viewModel.clearState()
        }
    }
    if (viewModel.postConfirmation) {
        BasicConfirmDialog(
            text = stringResource(R.string.add_housing_confirm_dialog_text),
            confirmOnClick = {
                viewModel.postAdvertisement()
            },
            dismissOnClick = {
                viewModel.hideConfirmDialog()
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = dimensionResource(id = R.dimen.screen_top_margin),
                start = dimensionResource(id = R.dimen.screen_start_margin),
                end = dimensionResource(id = R.dimen.screen_end_margin),
            )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = colorResource(id = R.color.primary_dark)
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(
                        dimensionResource(id = R.dimen.list_elements_margin)
                    )
                ) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        BackBtn(
                            onBackNavigation = {
                                navigator.navigate(PostScreenDestination)
                            }
                        )
                        Text(
                            text = stringResource(R.string.post_advertisement_title),
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
                    HousingPhotosComponent(
                        bitmapListValue = viewModel.roomImages,
                        onBitmapListValueChange = {}
                    )
                    UsualTextField(
                        title = stringResource(R.string.month_price),
                        placeholder = stringResource(R.string.month_price_placeholder),
                        value = viewModel.monthPrice,
                        onValueChange = { newValue -> viewModel.monthPrice = newValue }
                    )
                    UsualTextField(
                        title = stringResource(R.string.description_label),
                        placeholder = stringResource(R.string.description_label),
                        singleLine = false,
                        value = viewModel.description,
                        onValueChange = { newValue -> viewModel.description = newValue }
                    )
                    ButtonsRow(
                        label = stringResource(R.string.bedrooms_label),
                        values = Constants.RoomPost.ROOMS_COUNT_LIST,
                        value = viewModel.bedroomsCount,
                        onValueChange = { viewModel.bedroomsCount = it }
                    )
                    ButtonsRow(
                        label = stringResource(R.string.bathrooms_label),
                        values = Constants.RoomPost.ROOMS_COUNT_LIST,
                        value = viewModel.bathroomsCount,
                        onValueChange = { viewModel.bathroomsCount = it }
                    )
                    ButtonsRowMapped(
                        label = stringResource(R.string.apartment_type_label),
                        values = mapOf(
                            Pair("F", stringResource(R.string.flat)),
                            Pair("DU", stringResource(R.string.duplex)),
                            Pair("H", stringResource(R.string.house)),
                            Pair("DO", stringResource(R.string.dorm))
                        ),
                        value = viewModel.apartmentType,
                        onValueChange = { viewModel.apartmentType = it }
                    )
                    ButtonsRowMapped(
                        label = stringResource(R.string.sharing_type_label),
                        values = mapOf(
                            Pair("P", stringResource(R.string.sharing_type_private)),
                            Pair("S", stringResource(R.string.sharing_type_shared))
                        ),
                        value = viewModel.sharingType,
                        onValueChange = { viewModel.sharingType = it }
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    )
                }

                GreenButtonPrimary(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    text = stringResource(R.string.post_button_label),
                    enabled = true,
                    onClick = {
                        viewModel.showConfirmDialog()
                    }
                )
            }
        }
    }
}
