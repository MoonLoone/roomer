package com.example.roomer.presentation.screens.profile_nested_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomer.R
import com.example.roomer.presentation.screens.destinations.ProfileScreenDestination
import com.example.roomer.presentation.ui_components.AccountScreenTextField
import com.example.roomer.presentation.ui_components.BackBtn
import com.example.roomer.presentation.ui_components.SelectAddressField
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun AccountScreen(
    navigator: DestinationsNavigator,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                top = dimensionResource(id = R.dimen.screen_top_margin),
                start = dimensionResource(id = R.dimen.screen_start_margin),
                end = dimensionResource(id = R.dimen.screen_end_margin),
            ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BackBtn(onBackNavigation = { navigator.navigate(ProfileScreenDestination) })
            Text(
                text = stringResource(R.string.account_title),
                fontSize = integerResource(
                    id = R.integer.label_text
                ).sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AccountScreenTextField(label = stringResource(R.string.first_name), textHint = "Vasya")
            AccountScreenTextField(label = stringResource(R.string.last_name), textHint = "Pupkin")
            AccountScreenTextField(
                textHint = stringResource(R.string.hint_about_you),
                label = stringResource(R.string.about_me),
                textFieldHeight = 112.dp
            )
            SelectAddressField(
                label = stringResource(R.string.select_addr_title),
                placeholder = stringResource(R.string.addr_placeholder)
            )
            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .width(146.dp)
                        .border(
                            width = dimensionResource(id = R.dimen.ordinary_border),
                            color = Color.Black,
                            shape = RoundedCornerShape(
                                dimensionResource(id = R.dimen.rounded_corner_full)
                            )
                        )
                        .clickable {
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.habits_icon),
                        contentDescription = stringResource(R.string.habits_icon_description),
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .width(dimensionResource(id = R.dimen.ordinary_icon))
                            .height(dimensionResource(id = R.dimen.ordinary_icon)),
                    )
                    Text(
                        text = stringResource(id = R.string.habits_button),
                        style = TextStyle(
                            color = colorResource(id = R.color.primary_dark),
                            fontSize = integerResource(id = R.integer.primary_text).sp,
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                }
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .width(146.dp)
                        .border(
                            width = dimensionResource(id = R.dimen.ordinary_border),
                            color = Color.Black,
                            shape = RoundedCornerShape(
                                dimensionResource(id = R.dimen.rounded_corner_full)
                            )
                        )
                        .clickable {
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.interests_icon),
                        contentDescription = stringResource(
                            id = R.string.habits_icon_description
                        ),
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .width(dimensionResource(id = R.dimen.ordinary_icon))
                            .height(dimensionResource(id = R.dimen.ordinary_icon)),
                    )
                    Text(
                        text = stringResource(id = R.string.interests_button),
                        style = TextStyle(
                            color = colorResource(id = R.color.primary_dark),
                            fontSize = integerResource(id = R.integer.primary_text).sp,
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                }
            }
        }
    }
}
