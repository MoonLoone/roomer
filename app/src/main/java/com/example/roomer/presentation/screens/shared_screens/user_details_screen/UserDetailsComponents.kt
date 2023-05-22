package com.example.roomer.presentation.screens.shared_screens.user_details_screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.roomer.R
import com.example.roomer.presentation.ui_components.GreenButtonOutlineIconed

@Composable
fun FollowButton(
    isFollowed: Boolean,
    onClickFollow: () -> Unit,
    onClickUnfollow: () -> Unit
) {
    if (isFollowed)
        FollowedButton(
            onClickUnfollow = onClickUnfollow
        )
    else
        NotFollowedButton(onClickFollow = onClickFollow)
}

@Composable
fun NotFollowedButton(
    onClickFollow: () -> Unit,
) {
    GreenButtonOutlineIconed(
        text = stringResource(R.string.follow_me_text),
        trailingIconPainterId = R.drawable.follow_fill,
        trailingIconDescriptionId = R.string.follow_icon_description
    ) {
        onClickFollow()
    }
}

@Composable
fun FollowedButton(
    onClickUnfollow: () -> Unit,
) {
    GreenButtonOutlineIconed(
        text = stringResource(R.string.unfollow_me_text),
        trailingIconPainterId = R.drawable.follow_fill,
        trailingIconDescriptionId = R.string.unfollow_icon_description,
    ) {
        onClickUnfollow()
    }
}
