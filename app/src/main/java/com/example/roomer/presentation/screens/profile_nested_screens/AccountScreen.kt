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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomer.R
import com.example.roomer.presentation.screens.destinations.ProfileScreenDestination
import com.example.roomer.presentation.ui_components.BackBtn
import com.example.roomer.presentation.ui_components.ScreenTextField
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
                top = 24.dp,
                start = 40.dp,
                end = 40.dp
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
                    id = R.integer.label_text_size
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
            ScreenTextField(label = stringResource(R.string.first_name), textHint = "Vasya")
            ScreenTextField(label = stringResource(R.string.last_name), textHint = "Pupkin")
//                DateField(label = stringResource(R.string.date_of_birth))
//                SexField()
//                DropdownTextField(listOfItems = Choices.employment, label = stringResource(R.string.employment))
            ScreenTextField(
                textHint = stringResource(R.string.hint_about_you),
                label = stringResource(R.string.about_me),
                textFieldHeight = 112
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
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(100.dp)
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
                            .width(18.dp)
                            .height(18.dp),
                    )
                    Text(
                        text = stringResource(id = R.string.habits_button),
                        style = TextStyle(
                            color = colorResource(id = R.color.primary_dark),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                }
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .width(146.dp)
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(100.dp)
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
                            .width(18.dp)
                            .height(18.dp),
                    )
                    Text(
                        text = stringResource(id = R.string.interests_button),
                        style = TextStyle(
                            color = colorResource(id = R.color.primary_dark),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                }
            }
        }
    }
}
