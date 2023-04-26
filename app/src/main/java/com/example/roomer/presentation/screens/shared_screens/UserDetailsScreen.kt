package com.example.roomer.presentation.screens.shared_screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoubleArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.roomer.R
import com.example.roomer.domain.model.entities.User
import com.example.roomer.domain.model.login_sign_up.InterestModel
import com.example.roomer.presentation.screens.entrance.signup.habits_screen.HabitTileModel
import com.example.roomer.presentation.ui_components.BackBtn
import com.example.roomer.presentation.ui_components.GreenButtonOutline
import com.example.roomer.presentation.ui_components.GreenButtonOutlineIconed
import com.example.roomer.presentation.ui_components.HabitsTable
import com.example.roomer.utils.Constants
import java.time.LocalDate
import java.time.Period
import kotlin.random.Random

@Composable
fun UserDetailsScreen(user: User) {
    val textStyleHeadline = TextStyle(
        color = colorResource(id = R.color.black),
        fontWeight = FontWeight.Medium,
        fontSize = integerResource(id = R.integer.big_text).sp
    )
    val textStyleSecondary = TextStyle(
        color = colorResource(id = R.color.text_secondary),
        fontWeight = FontWeight.Normal,
        fontSize = integerResource(id = R.integer.medium_text).sp
    )
    val textStyleHeadlineMedium = TextStyle(
        color = colorResource(id = R.color.text_secondary),
        fontWeight = FontWeight.Medium,
        fontSize = integerResource(id = R.integer.medium_text).sp
    )
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = dimensionResource(id = R.dimen.screen_top_margin),
                start = dimensionResource(id = R.dimen.screen_start_margin),
                end = dimensionResource(id = R.dimen.screen_end_margin),
                bottom = dimensionResource(id = R.dimen.screen_bottom_margin)
            ),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.column_elements_small_margin))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BackBtn {
                //navigator.pop
            }
            Text(
                text = stringResource(R.string.details_header),
                fontSize = integerResource(
                    id = R.integer.label_text
                ).sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth()
            )
        }
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(user.avatar)
                .crossfade(true)
                .build(),
            placeholder = painterResource(id = R.drawable.usual_client),
            contentDescription = stringResource(id = R.string.user_avatar_content_description),
            modifier = Modifier
                .size(dimensionResource(R.dimen.rounded_corner_full)),
            contentScale = ContentScale.FillBounds
        )
        UserHeadline(
            firstName = user.firstName,
            lastName = user.lastName,
            birthDate = user.birthDate ?: "",
            sex = user.sex,
            textStyleHeadline
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.location_icon),
                contentDescription = stringResource(
                    R.string.location_icon_description
                ),
                tint = colorResource(id = R.color.black)
            )
            Text(
                text = user.city ?: "",
                fontSize = integerResource(id = R.integer.medium_text).sp,
                fontWeight = FontWeight.Normal
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.column_elements_small_margin))) {
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.column_elements_small_margin)),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(id = R.string.rating_and_colon),
                    style = textStyleHeadlineMedium
                )
                Text(
                    text = stringResource(id = R.string.status),
                    style = textStyleHeadlineMedium
                )
                Text(
                    text = stringResource(id = R.string.personality_type_label_colon),
                    style = textStyleHeadlineMedium
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.column_elements_small_margin)),
                horizontalAlignment = Alignment.Start
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = user.rating.toString(), style = textStyleSecondary)
                    Icon(
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.small_icon)),
                        painter = painterResource(id = R.drawable.rating_icon),
                        contentDescription = stringResource(
                            id = R.string.rating_star_content_description
                        ),
                        tint = colorResource(id = R.color.text_secondary)
                    )
                }
                Text(
                    text = Constants.Maps.employmentMap[user.employment] ?: "",
                    style = textStyleSecondary
                )
                Text(
                    text = Constants.Maps.personalityMap[user.personalityType] ?: "",
                    style = textStyleSecondary
                )
            }
            GreenButtonOutline(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(
                    id = R.string.comments_amount,
                    Random.nextInt(5, 10)
                ), onClick = { /*TODO*/ }
            )
        }
        ChapterHeadline(
            headline = stringResource(id = R.string.about_me),
            textStyle = textStyleHeadline
        )
        Text(text = user.aboutMe ?: "", style = textStyleSecondary)
        Row(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.list_elements_margin)),
            modifier = Modifier
                .horizontalScroll(scrollState)
        ) {
            user.interests?.let {
                it.forEach {
                    GreenButtonOutline(text = it.interest) {}
                }
            }
        }
        ChapterHeadline(
            headline = stringResource(id = R.string.habits_title),
            textStyle = textStyleHeadline
        )
        HabitsTable(habitsList = listOf(
            HabitTileModel(R.drawable.alcohol_icon, R.string.alcohol_icon_description, R.string.attitude_to_alcohol_label, )
        ))
//        Row(horizontalArrangement = Arrangement.SpaceBetween) {
//            FloatingActionButton(
//                onClick = { /*TODO*/ },
//                backgroundColor = colorResource(id = R.color.primary_dark),
//                shape = Shape()
//            ) {
//                Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.icon_text_medium_margin))) {
//                    Icon(
//                        modifier = Modifier.size(dimensionResource(id = R.dimen.ordinary_icon)),
//                        painter = painterResource(id = R.drawable.rating_icon),
//                        contentDescription = stringResource(
//                            id = R.string.rating_star_content_description
//                        ),
//                        tint = colorResource(id = R.color.secondary_color)
//                    )
//                    Text(text = stringResource(id = R.string.rate_button_text))
//                }
//            }
//        }


    }
}

@Composable
fun UserHeadline(
    firstName: String,
    lastName: String,
    birthDate: String,
    sex: String,
    textStyle: TextStyle
) {
    val userDate = birthDate.split(birthDate[4]).map { it.toInt() }
    val userAge = Period.between(
        LocalDate.of(userDate[0], userDate[1], userDate[2]),
        LocalDate.now()
    ).years

    val userHeadlineString = stringResource(
        R.string.details_headline_user,
        firstName.replaceFirstChar(Char::uppercaseChar),
        lastName.replaceFirstChar(Char::uppercaseChar),
        userAge,
        Constants.Maps.sexMap[sex] ?: ""
    )
    Text(
        text = userHeadlineString,
        style = textStyle
    )
}

@Composable
fun ChapterHeadline(headline: String, textStyle: TextStyle) {
    Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.list_elements_margin))) {
        Divider(color = colorResource(id = R.color.black))
        Text(text = headline, style = textStyle)
    }
}

@Preview
@Composable
fun ScreenPreview() {
    val interests = listOf(
        InterestModel("beer", 0),
        InterestModel("fish", 1),
        InterestModel("movies", 2),
        InterestModel("chips", 3),
        InterestModel("popcorn", 4),
        InterestModel("big money", 5),
    )
    val user = User(
        firstName = "Rodion",
        lastName = "Ivannikov",
        employment = "E",
        sex = "M",
        alcoholAttitude = "I",
        smokingAttitude = "I",
        sleepTime = "N",
        personalityType = "E",
        cleanHabits = "N",
        city = "Astrakhan",
        birthDate = "2001.08.19",
        aboutMe = "I'm quite decent person who loves warm sunny weather and chatting with friends!",
        interests = interests
    )
    UserDetailsScreen(user = user)
}
