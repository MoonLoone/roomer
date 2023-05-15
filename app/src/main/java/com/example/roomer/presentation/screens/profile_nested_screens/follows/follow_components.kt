package com.example.roomer.presentation.screens.profile_nested_screens.follows

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.roomer.R
import com.example.roomer.domain.model.entities.User
import com.example.roomer.utils.Constants
import com.example.roomer.utils.UtilsFunctions

@Composable
fun FollowCard(user: User, onClick: () -> Unit, deleteFollow: () -> Unit) {
    var expandedSettings by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(176.dp)
            .background(
                color = colorResource(id = R.color.primary),
                shape = RoundedCornerShape(20.dp)
            )
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(user.avatar)
                .crossfade(true)
                .build(),
            placeholder = painterResource(id = R.drawable.ordinary_user),
            contentDescription = user.firstName,
            modifier = Modifier
                .fillMaxHeight()
                .width(104.dp),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp, top = 8.dp, end = 12.dp, bottom = 8.dp),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.column_elements_small_margin)
            )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = UtilsFunctions.trimString(
                        user.firstName + " " + user.lastName,
                        Constants.Follows.USER_CARD_MAX_NAME
                    ),
                    style = TextStyle(
                        fontSize = integerResource(id = R.integer.label_text).sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                    Image(
                        painter = painterResource(id = R.drawable.settings_ellipsis),
                        contentDescription = stringResource(
                            id = R.string.follow_setting_icon_description
                        ),
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable {
                                expandedSettings = !expandedSettings
                            }
                    )
                }
                DropdownMenu(
                    modifier = Modifier
                        .widthIn(min = 80.dp, max = 160.dp)
                        .heightIn(min = 64.dp, max = 124.dp),
                    expanded = expandedSettings,
                    onDismissRequest = { expandedSettings = false },
                    offset = DpOffset(x = 156.dp, y = 0.dp)
                ) {
                    Text(
                        text = stringResource(R.string.delete_text),
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clickable {
                                deleteFollow()
                                expandedSettings = false
                            }
                    )
                    Divider()
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.location_icon),
                    contentDescription = stringResource(id = R.string.location_icon),
                    modifier = Modifier
                        .width(dimensionResource(id = R.dimen.small_icon))
                        .height(dimensionResource(id = R.dimen.small_icon))
                        .align(Alignment.CenterVertically)
                )
                Text(
                    text = user.city ?: "",
                    style = TextStyle(
                        fontSize = integerResource(id = R.integer.primary_text).sp,
                        color = Color.Black
                    )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.status),
                    style = TextStyle(
                        fontSize = integerResource(id = R.integer.primary_text).sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )
                Text(
                    stringResource(R.string.occasionally),
                    style = TextStyle(
                        fontSize = integerResource(id = R.integer.primary_text).sp,
                        color = Color.Black
                    ),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            ) {
                Text(
                    text = stringResource(R.string.rating_and_colon),
                    style = TextStyle(
                        fontSize = integerResource(id = R.integer.primary_text).sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )
                Text(
                    text = user.rating.toString(),
                    style = TextStyle(
                        fontSize = integerResource(id = R.integer.primary_text).sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ),
                    modifier = Modifier.padding(start = 8.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.rating_icon),
                    contentDescription = stringResource(R.string.rating_star_content_description),
                    modifier = Modifier
                        .height(dimensionResource(id = R.dimen.small_icon))
                        .width(dimensionResource(id = R.dimen.small_icon))
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
}
