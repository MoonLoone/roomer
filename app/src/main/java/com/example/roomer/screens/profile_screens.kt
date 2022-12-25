package com.example.roomer.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.roomer.R
import com.example.roomer.ui_components.*
import com.example.roomer.utils.NavbarItem

@Composable
fun AccountScreen() {
    val navController = NavbarItem.Profile.navHostController ?: rememberNavController()
    val listOfEmployments = listOf<String>("Employed", "Unemployed", "Seasonable")
    Scaffold(bottomBar = { Navbar(navController, NavbarItem.Profile.name) }) {
        val padding = it
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp, bottom = 88.dp, start = 40.dp, end = 40.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BackBtn(onBackNavigation = { navController.navigate(NavbarItem.Profile.name) })
                Text(
                    text = "Account",
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
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                ScreenTextField(label = "First Name", textHint = "Vasya")
                ScreenTextField(label = "Last Name", textHint = "Pupkin")
                DateField(label = "Date of birth")
                SelectSex()
                DropdownTextField(listOfItems = listOfEmployments, label = "Employment")
                ScreenTextField(
                    textHint = "Some text about you",
                    label = "About me",
                    textFieldHeight = 112
                )
                SelectAddressField(
                    label = "Select address at the map",
                    placeholder = "Here will be your address"
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
                            contentDescription = "Habits icon",
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .width(18.dp)
                                .height(18.dp),
                        )
                        Text(
                            text = "Open habits",
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
                            contentDescription = "Habits icon",
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .width(18.dp)
                                .height(18.dp),
                        )
                        Text(
                            text = "Open interests",
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
}
