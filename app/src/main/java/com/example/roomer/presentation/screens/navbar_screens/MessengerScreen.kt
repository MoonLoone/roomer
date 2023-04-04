package com.example.roomer.presentation.screens.navbar_screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.roomer.R
import com.example.roomer.domain.model.entities.Message
import com.example.roomer.presentation.screens.destinations.ChatScreenDestination
import com.example.roomer.presentation.ui_components.ChatItem
import com.example.roomer.utils.NavbarManagement
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun MessengerScreen(
    navigator: DestinationsNavigator,
    viewModel: MessengerViewModel = hiltViewModel(),
) {
    NavbarManagement.showNavbar()
    val loadingState = viewModel.state
    val listOfChats by viewModel.chats.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 24.dp,
                start = 40.dp,
                end = 40.dp
            )
    ) {
        Searcher()
        ChatsListScreen(listOfChats = listOfChats, navigator = navigator)
        if (loadingState.value.isLoading) {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
            viewModel.getChats()
        }
    }
}

@Composable
private fun ChatsListScreen(listOfChats: List<Message>, navigator: DestinationsNavigator) {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(top = 24.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (listOfChats.isEmpty()) {
            item {
                EmptyChatListNotification {
                    navigator.navigate(ChatScreenDestination(302, 17))
                }
            }
        }
        items(listOfChats.size) { index ->
            ChatItem(
                listOfChats[index],
                navigateTo = {
                    navigator.navigate(
                        ChatScreenDestination(
                            listOfChats[index].recipient.id,
                            chatId = listOfChats[index].chatId
                        )
                    )
                }
            )
        }
    }
}

@Composable
private fun Searcher() {
    var searchText by remember {
        mutableStateOf(TextFieldValue(""))
    }
    TextField(
        label = {
            Text(
                text = "Search in messages",
                style = TextStyle(
                    color = colorResource(id = R.color.primary_dark),
                    fontSize = 12.sp
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        },
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = integerResource(id = R.integer.primary_text).sp,
        ),
        value = searchText,
        onValueChange = { value ->
            if (value.text.length <= 100) {
                searchText = value
            }
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.loupe_icon),
                contentDescription = "search_icon",
                modifier = Modifier
                    .height(
                        dimensionResource(id = R.dimen.ordinary_icon)
                    )
                    .width(
                        dimensionResource(id = R.dimen.ordinary_icon)
                    ),
            )
        },
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.clear_icon),
                contentDescription = "clear_text",
                modifier = Modifier
                    .height(
                        24.dp
                    )
                    .width(
                        24.dp
                    )
                    .clickable { searchText = TextFieldValue("") },
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(
                BorderStroke(
                    2.dp,
                    colorResource(
                        id = R.color.primary_dark
                    )
                ),
                RoundedCornerShape(4.dp)
            ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = colorResource(id = R.color.white)
        )
    )
}

@Composable
private fun EmptyChatListNotification(onNavigate: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "No chats here")
        Text(
            text = "Do you want to start new chat?",
            modifier = Modifier.clickable {
                onNavigate.invoke()
            }
        )
    }
}
