package com.example.roomer.presentation.screens.navbar_screens.post_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomer.R
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.User
import com.example.roomer.presentation.screens.destinations.AddHousingScreenDestination
import com.example.roomer.presentation.ui_components.GreenButtonOutlineIconed
import com.example.roomer.presentation.ui_components.GreenButtonPrimaryIconed
import com.example.roomer.presentation.ui_components.PostCard
import com.example.roomer.utils.NavbarManagement
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun PostScreen(
    navigator: DestinationsNavigator
) {
    NavbarManagement.showNavbar()
    val myPosts = mutableListOf<Room>()
    for (i in 0..5) {
        myPosts.add(
            Room(
                id = i,
                host = User(), // current user
                fileContent = listOf(Room.Photo(photo = ""))
            )
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = dimensionResource(id = R.dimen.screen_top_margin),
                start = dimensionResource(id = R.dimen.screen_start_margin),
                end = dimensionResource(id = R.dimen.screen_end_margin),
                bottom = dimensionResource(id = R.dimen.navbar_height)
            ),
    ) {
        Text(
            text = stringResource(R.string.post_screen_title),
            style = TextStyle(
                textAlign = TextAlign.Start,
                fontSize = integerResource(id = R.integer.label_text).sp,
                fontWeight = FontWeight.Bold
            ),
        )
        if (myPosts.size > 0) {
            Box() {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = 24.dp
                        ),
                    verticalArrangement = Arrangement.spacedBy(
                        dimensionResource(id = R.dimen.list_elements_margin)
                    )
                ) {
                    items(myPosts.size) { index ->
                        PostCard(room = myPosts[index]) {
                            //...
                        }
                    }
                    item {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                        )
                    }
                }
                GreenButtonOutlineIconed(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    text = stringResource(R.string.add_post_button_label),
                    trailingIcon = ImageVector.vectorResource(id = R.drawable.postin),
                    enabled = true,
                    onClick = {
                        navigator.navigate(AddHousingScreenDestination)
                    }
                )
            }
        } else {
            Column(modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.no_posts_label),
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize = integerResource(id = R.integer.label_text).sp,
                        fontWeight = FontWeight.Bold
                    ),
                )
                GreenButtonPrimaryIconed(
                    text = stringResource(R.string.add_post_button_label),
                    trailingIcon = ImageVector.vectorResource(id = R.drawable.postin),
                    enabled = true,
                    onClick = {
                        navigator.navigate(AddHousingScreenDestination)
                    }
                )
            }
        }
    }
}
