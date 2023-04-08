package com.example.roomer.presentation.ui_components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.roomer.R
import com.example.roomer.presentation.screens.appCurrentDestinationAsState
import com.example.roomer.utils.NavbarManagement

@Composable
fun Navbar(navController: NavHostController) {
    val navbarState = NavbarManagement.navbarState
    val currentDestination = navController.appCurrentDestinationAsState().value
    AnimatedVisibility(visible = navbarState.value) {
        BottomNavigation(
            backgroundColor = colorResource(id = R.color.secondary_color),
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.navbar_height))
        ) {
            NavbarManagement.NavbarItem.values().forEach { screen ->
                BottomNavigationItem(
                    selected = (currentDestination == screen.direction),
                    modifier = Modifier
                        .width(80.dp)
                        .padding(
                            start = 4.dp,
                            end = 4.dp,
                        ),
                    onClick = {
                        navController.navigate(screen.direction.route)
                    },
                    icon = {
                        if (currentDestination == screen.direction) {
                            Column {
                                Box(
                                    modifier = Modifier
                                        .width(64.dp)
                                        .height(32.dp)
                                        .clip(
                                            RoundedCornerShape(
                                                dimensionResource(
                                                    id = R.dimen.rounded_corner_ordinary
                                                )
                                            )
                                        )
                                        .background(colorResource(id = R.color.primary)),
                                ) {
                                    Image(
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .width(dimensionResource(id = R.dimen.ordinary_icon))
                                            .height(dimensionResource(id = R.dimen.ordinary_icon)),
                                        painter = painterResource(id = screen.iconSelected),
                                        contentDescription = screen.name
                                    )
                                }
                                Text(
                                    text = stringResource(id = screen.nameResId),
                                    fontSize =
                                        integerResource(id = R.integer.secondary_text).sp,

                                    color = Color.Black,
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .padding(top = 4.dp)
                                )
                            }
                        } else {
                            Column {
                                Box(
                                    modifier = Modifier
                                        .width(32.dp)
                                        .height(32.dp)
                                        .align(Alignment.CenterHorizontally)
                                ) {
                                    Image(
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .width(dimensionResource(id = R.dimen.ordinary_icon))
                                            .height(dimensionResource(id = R.dimen.ordinary_icon)),
                                        painter = painterResource(id = screen.iconUnSelected),
                                        contentDescription = screen.name
                                    )
                                }
                                Text(
                                    text = stringResource(id = screen.nameResId),
                                    fontSize =
                                        integerResource(id = R.integer.secondary_text).sp,
                                    color = colorResource(id = R.color.text_secondary),
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .padding(top = 4.dp),
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}
