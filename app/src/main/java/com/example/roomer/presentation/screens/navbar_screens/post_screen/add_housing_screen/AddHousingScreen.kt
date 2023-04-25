package com.example.roomer.presentation.screens.navbar_screens.post_screen.add_housing_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomer.R
import com.example.roomer.presentation.screens.destinations.AddHousingScreenDestination
import com.example.roomer.presentation.screens.destinations.PostScreenDestination
import com.example.roomer.presentation.ui_components.AddHousingScreenTextField
import com.example.roomer.presentation.ui_components.BackBtn
import com.example.roomer.presentation.ui_components.ButtonsRowMapped
import com.example.roomer.presentation.ui_components.DropdownTextFieldMapped
import com.example.roomer.presentation.ui_components.GreenButtonPrimary
import com.example.roomer.presentation.ui_components.GreenButtonPrimaryIconed
import com.example.roomer.presentation.ui_components.RedButtonPrimaryIconed
import com.example.roomer.presentation.ui_components.SelectAddressField
import com.example.roomer.presentation.ui_components.UsualTextField
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun AddHousingScreen(
    navigator: DestinationsNavigator
) {
    val priceForMonth = remember {
        mutableStateOf("")
    }

    val deposit = remember {
        mutableStateOf("")
    }

    val description = remember {
        mutableStateOf("")
    }

    val rooms = remember {
        mutableStateOf("A")
    }

    val bathrooms = remember {
        mutableStateOf("A")
    }

    val apartmentType = remember {
        mutableStateOf("F")
    }

    val sharingType = remember {
        mutableStateOf("P")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = dimensionResource(id = R.dimen.screen_top_margin),
                start = dimensionResource(id = R.dimen.screen_start_margin),
                end = dimensionResource(id = R.dimen.screen_end_margin),
                bottom = dimensionResource(id = R.dimen.navbar_height)
            )
    ) {
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
                    text = "Post your advertisement",
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
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.housing_photos),
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(88.dp)
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        8.dp
                    )
                ) {
                    items(5) { index ->
                        Image(
                            painterResource(id = R.drawable.ordinnary_room),
                            contentDescription = stringResource(id = R.string.room_image_description),
                            modifier = Modifier
                                .width(80.dp)
                                .height(80.dp)
                                .clip(RoundedCornerShape(10.dp)),
                            contentScale = ContentScale.FillBounds
                        )
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    24.dp
                )
            ) {
                GreenButtonPrimaryIconed(
                    modifier = Modifier.padding(top = 8.dp),
                    text = stringResource(R.string.add_photos_button_label),
                    trailingIcon = ImageVector.vectorResource(id = R.drawable.add_photos_icon),
                    enabled = true,
                    onClick = {
                        navigator.navigate(AddHousingScreenDestination)
                    }
                )
                RedButtonPrimaryIconed(
                    modifier = Modifier.padding(top = 8.dp),
                    text = stringResource(R.string.remove_all_photos_button_label),
                    trailingIcon = ImageVector.vectorResource(id = R.drawable.remove_icon),
                    enabled = true,
                    onClick = {
                        navigator.navigate(AddHousingScreenDestination)
                    }
                )
            }
            UsualTextField(
                title = "Price for month",
                placeholder = "500$",
                value = priceForMonth.value,
                onValueChange = { newValue -> priceForMonth.value = newValue }
            )
            UsualTextField(
                title = "Deposit",
                placeholder = "500$",
                value = deposit.value,
                onValueChange = { newValue -> deposit.value = newValue }
            )
            AddHousingScreenTextField(
                textHint = "Some words about this place",
                label = "Description",
                textFieldHeight = 112.dp
            )
            SelectAddressField(
                label = stringResource(R.string.select_addr_label),
                placeholder = stringResource(R.string.addr_placeholder)
            )
            DropdownTextFieldMapped(
                mapOfItems = mapOf(
                    Pair("A", stringResource(R.string.any)),
                    Pair("1", "1"),
                    Pair("2", "2"),
                    Pair("3+", "3+")
                ),
                label = stringResource(R.string.bedrooms_label),
                value = rooms.value,
                onValueChange = { rooms.value = it }
            )
            DropdownTextFieldMapped(
                mapOfItems = mapOf(
                    Pair("A", stringResource(R.string.any)),
                    Pair("1", "1"),
                    Pair("2", "2"),
                    Pair("3+", "3+")
                ),
                label = stringResource(R.string.bathrooms_label),
                value = bathrooms.value,
                onValueChange = { bathrooms.value = it }
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
                onValueChange = { apartmentType.value = it }
            )
            ButtonsRowMapped(
                label = "Sharing Type",
                values = mapOf(
                    Pair("P", "Private"),
                    Pair("S", "Shared")
                ),
                value = sharingType.value,
                onValueChange = { sharingType.value = it }
            )
            GreenButtonPrimary(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                text = "Post",
                enabled = true,
                onClick = {

                }
            )
        }
}
}
