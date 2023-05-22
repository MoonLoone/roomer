package com.example.roomer.presentation.screens.shared_screens.user_details_screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.roomer.R
import com.example.roomer.data.shared.follow.FollowManipulateViewModel

@Composable
fun FollowButton(
    isFollowed: Boolean,
    currentUserId: Int,
    followUserId: Int,
    followManipulateViewModel: FollowManipulateViewModel = hiltViewModel()
) {
    if (isFollowed)
        FollowedButton(deleteFollow = {followManipulateViewModel.deleteFollow(currentUserId,followUserId)})
    else
        NotFollowedButton(addFollow = {
            followManipulateViewModel.addFollow(
                currentUserId,
                followUserId
            )
        })
}

@Composable
private fun NotFollowedButton(addFollow: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.black),
                shape = RoundedCornerShape(100.dp)
            )
            .clickable {
                addFollow()
            }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.follow_fill),
            modifier = Modifier.padding(2.dp),
            contentDescription = stringResource(R.string.follow_icon_description)
        )
        Text(
            text = stringResource(R.string.follow_me_text),
            modifier = Modifier.padding(2.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun FollowedButton(deleteFollow: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.black),
                shape = RoundedCornerShape(100.dp)
            )
            .clickable {
                deleteFollow()
            }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.follow_fill),
            modifier = Modifier.padding(2.dp),
            contentDescription = stringResource(R.string.follow_icon_description)
        )
        Text(
            text = stringResource(R.string.unfollow_me_text),
            modifier = Modifier.padding(2.dp),
            textAlign = TextAlign.Center
        )
    }
}
