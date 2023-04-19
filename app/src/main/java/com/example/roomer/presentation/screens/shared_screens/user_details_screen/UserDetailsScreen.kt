package com.example.roomer.presentation.screens.shared_screens.user_details_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.roomer.R
import com.example.roomer.presentation.ui_components.BackBtn

@Preview
@Composable
fun UserDetailsScreen(
//    navigator: DestinationsNavigator
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = dimensionResource(id = R.dimen.screen_top_margin),
                start = dimensionResource(id = R.dimen.screen_start_margin),
                end = dimensionResource(id = R.dimen.screen_end_margin)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            BackBtn {
//                navigator.popBackStack()
            }
        }
    }
}
