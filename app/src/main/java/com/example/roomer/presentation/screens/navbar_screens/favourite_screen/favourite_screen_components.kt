package com.example.roomer.presentation.screens.navbar_screens.favourite_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.roomer.R

@Composable
fun FavouriteLikeButton(
    isLiked: Boolean,
    dislikeHousing: () -> Unit,
    likeHousing: () -> Unit,
    modifier: Modifier = Modifier
) {
    val imagePainter = if (isLiked) painterResource(id = R.drawable.room_like_in_icon)
    else painterResource(id = R.drawable.room_like_icon)
    val onClickReaction = if (isLiked) dislikeHousing else likeHousing
    Image(
        painter = imagePainter,
        contentDescription = stringResource(id = R.string.like_icon),
        modifier = modifier
            .padding(top = 10.dp, end = 10.dp)
            .width(dimensionResource(id = R.dimen.big_icon))
            .height(dimensionResource(id = R.dimen.big_icon))
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner_full)))
            .clickable {
                onClickReaction()
            },
        alignment = Alignment.TopEnd,
    )
}
