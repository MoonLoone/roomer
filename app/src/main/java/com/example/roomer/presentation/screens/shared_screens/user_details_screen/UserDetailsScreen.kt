package com.example.roomer.presentation.screens.shared_screens.user_details_screen

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.roomer.R
import com.example.roomer.domain.model.entities.User
import com.example.roomer.domain.model.login_sign_up.InterestModel
import com.example.roomer.presentation.screens.destinations.ChatScreenDestination
import com.example.roomer.presentation.screens.entrance.signup.habits_screen.HabitTileModel
import com.example.roomer.presentation.ui_components.BackBtn
import com.example.roomer.presentation.ui_components.ExpandableText
import com.example.roomer.presentation.ui_components.FollowButton
import com.example.roomer.presentation.ui_components.GreenButtonOutline
import com.example.roomer.presentation.ui_components.GreenButtonOutlineIconed
import com.example.roomer.presentation.ui_components.HabitsTable
import com.example.roomer.utils.Constants
import com.example.roomer.utils.NavbarManagement
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.time.LocalDate
import java.time.Period
import kotlin.random.Random

@Destination
@Composable
fun UserDetailsScreen(
    user: User,
    navigator: DestinationsNavigator,
    viewModel: UserDetailsScreenViewModel = hiltViewModel()
) {
    NavbarManagement.hideNavbar()
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
    val interestsScroll = rememberScrollState()
    val columnScroll = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = dimensionResource(id = R.dimen.screen_start_margin),
                end = dimensionResource(id = R.dimen.screen_end_margin)
            ),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(columnScroll),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.column_elements_small_margin)
            )
        ) {
            DetailsHeadline {
                navigator.popBackStack()
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                UserAvatar(avatarUrl = user.avatar)
                FollowButton(
                    followManipulate = viewModel.followManipulate,
                    followUserId = user.userId,
                    currentUserId = viewModel.currentUser.value.userId
                )
            }
            UserHeadline(
                firstName = user.firstName,
                lastName = user.lastName,
                birthDate = user.birthDate ?: "",
                sex = user.sex,
                textStyleHeadline
            )
            UserLocation(user.city ?: "")
            UserTraitsWithCommentsButton(
                listOf(
                    Pair(R.string.rating_and_colon, user.rating.toString()),
                    Pair(
                        R.string.status,
                        Constants.Options.employmentOptions[user.employment]?.let {
                            stringResource(id = it)
                        } ?: ""
                    ),
                    Pair(
                        R.string.personality_type_label_colon,
                        Constants.Options.personalityOptions[user.personalityType]?.let {
                            stringResource(id = it)
                        } ?: ""
                    )
                ),
                Random.nextInt(1, 10),
                textStyleHeadlineMedium,
                textStyleSecondary
            )
            ChapterHeadline(
                headline = stringResource(id = R.string.about_me),
                textStyle = textStyleHeadline
            )
            ExpandableText(text = user.aboutMe ?: "", style = textStyleSecondary)
            InterestsScroll(user.interests, interestsScroll)
            HabitsSection(headlineStyle = textStyleHeadline, user = user)
            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.bottom_padding)))
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(id = R.dimen.message_rate_buttons_bottom_padding))
        ) {
            RateFab {}
            MessageFab {
                navigator.navigate(ChatScreenDestination(user))
            }
        }
    }
}

@Composable
private fun UserAvatar(avatarUrl: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(avatarUrl)
            .crossfade(true)
            .build(),
        placeholder = painterResource(id = R.drawable.usual_client),
        contentDescription = stringResource(id = R.string.user_avatar_content_description),
        modifier = Modifier
            .size(dimensionResource(R.dimen.user_avatar_size))
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.user_avatar_corner_radius))),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun UserLocation(city: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = R.drawable.location_icon),
            contentDescription = stringResource(
                R.string.location_icon_description
            ),
            tint = colorResource(id = R.color.black)
        )
        Text(
            text = city,
            fontSize = integerResource(id = R.integer.medium_text).sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
private fun DetailsHeadline(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BackBtn {
            onBackClick()
        }
        Text(
            text = stringResource(R.string.details_header),
            fontSize = integerResource(id = R.integer.label_text).sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun UserTraitsWithCommentsButton(
    traitIdToValue: List<Pair<Int, String>>,
    totalComments: Int,
    traitKeyStyle: TextStyle,
    traitValueStyle: TextStyle
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.column_elements_small_margin)
        )
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.column_elements_small_margin)
            ),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .width(dimensionResource(id = R.dimen.user_traits_column_width))
        ) {
            traitIdToValue.forEach {
                Text(
                    text = stringResource(id = it.first),
                    style = traitKeyStyle
                )
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.column_elements_small_margin)
            ),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .width(dimensionResource(id = R.dimen.user_traits_column_width))
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = traitIdToValue[0].second, style = traitValueStyle)
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
                text = traitIdToValue[1].second,
                style = traitValueStyle
            )
            Text(
                text = traitIdToValue[2].second,
                style = traitValueStyle
            )
        }
        GreenButtonOutlineIconed(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(
                id = R.string.comments_amount,
                totalComments
            ),
            trailingIconPainterId = R.drawable.double_arrow_icon,
            trailingIconDescriptionId = R.string.double_arrow_icon_description
        ) { }
    }
}

@Composable
private fun InterestsScroll(interests: List<InterestModel>?, scrollState: ScrollState) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.list_elements_margin)
        ),
        modifier = Modifier
            .horizontalScroll(scrollState)
    ) {
        interests?.let {
            it.forEach {
                GreenButtonOutline(text = it.interest) {}
            }
        }
    }
}

@Composable
private fun HabitsSection(headlineStyle: TextStyle, user: User) {
    ChapterHeadline(
        headline = stringResource(id = R.string.habits_title),
        textStyle = headlineStyle
    )
    HabitsTable(
        habitsList = listOf(
            HabitTileModel(
                R.drawable.alcohol_icon,
                R.string.alcohol_icon_description,
                R.string.attitude_to_alcohol_label,
                Constants.Options.attitudeOptions.getOrDefault(user.alcoholAttitude, 0)
            ),
            HabitTileModel(
                R.drawable.smoking_icon,
                R.string.smoking_icon_description,
                R.string.attitude_to_smoking_label,
                Constants.Options.attitudeOptions.getOrDefault(user.smokingAttitude, 0)
            ),
            HabitTileModel(
                R.drawable.sleep_time_icon,
                R.string.sleep_time_icon_description,
                R.string.sleep_time_label,
                Constants.Options.sleepOptions.getOrDefault(user.sleepTime, 0)
            ),
            HabitTileModel(
                R.drawable.clean_habits_icon,
                R.string.clean_habits_icon_description,
                R.string.clean_habits_label,
                Constants.Options.cleanOptions.getOrDefault(user.cleanHabits, 0)
            ),
            HabitTileModel(
                R.drawable.personality_icon,
                R.string.personality_icon,
                R.string.personality_type_label,
                Constants.Options.personalityOptions.getOrDefault(user.personalityType, 0)
            )
        )
    )
}

@Composable
private fun RateFab(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        backgroundColor = colorResource(id = R.color.primary_dark),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.fab_corner_radius)),
        contentColor = colorResource(id = R.color.secondary_color)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.icon_text_medium_margin)
            ),
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.fab_padding))
        ) {
            Icon(
                modifier = Modifier.size(dimensionResource(id = R.dimen.ordinary_icon)),
                painter = painterResource(id = R.drawable.rating_icon),
                contentDescription = stringResource(
                    id = R.string.rating_star_content_description
                ),
                tint = colorResource(id = R.color.secondary_color)
            )
            Text(text = stringResource(id = R.string.rate_button_text))
        }
    }
}

@Composable
private fun MessageFab(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        backgroundColor = colorResource(id = R.color.primary_dark),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.fab_corner_radius)),
        contentColor = colorResource(id = R.color.secondary_color)
    ) {
        Icon(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.fab_padding))
                .size(dimensionResource(id = R.dimen.big_icon)),
            painter = painterResource(id = R.drawable.big_envelope_icon),
            contentDescription = stringResource(
                id = R.string.envelope_icon_description
            ),
            tint = colorResource(id = R.color.secondary_color)
        )
    }
}

@Composable
private fun UserHeadline(
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
        stringResource(id = Constants.Options.sexOptions.getOrDefault(sex, 0))
    )
    Text(
        text = userHeadlineString,
        style = textStyle
    )
}

@Composable
private fun ChapterHeadline(headline: String, textStyle: TextStyle) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.list_elements_margin)
        )
    ) {
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
        InterestModel("big money", 5)
    )
    val user = User(
        firstName = "Rodion",
        lastName = "Ivannikov",
        employment = "S",
        sex = "M",
        avatar = "http://176.113.83.93:8000/media/avatar/default.jpg",
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
}
