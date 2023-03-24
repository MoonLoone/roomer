package com.example.roomer.presentation.screens.shared_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.roomer.R
import com.example.roomer.domain.model.entities.Message
import com.example.roomer.presentation.screens.destinations.MessengerScreenDestination
import com.example.roomer.presentation.ui_components.BackBtn
import com.example.roomer.presentation.ui_components.Message
import com.example.roomer.utils.NavbarManagement
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun ChatScreen(
    navigator: DestinationsNavigator,
    viewModel: ChatScreenViewModel = hiltViewModel()
) {
    NavbarManagement.hideNavbar()
    viewModel.startChat()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp, bottom = 16.dp, start = 40.dp, end = 40.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            BackBtn(onBackNavigation = { navigator.navigate(MessengerScreenDestination) })
            Image(
                modifier = Modifier
                    .width(56.dp)
                    .height(56.dp)
                    .padding(start = 16.dp),
                painter = painterResource(id = R.drawable.ordinary_client),
                contentDescription = "Client avatar",
                alignment = Alignment.Center,
            )
            Text(
                text = "Username here",
                modifier = Modifier.padding(start = 8.dp),
                style = TextStyle(
                    color = Color.Black,
                    fontSize = integerResource(
                        id = R.integer.primary_text_size
                    ).sp,
                    fontWeight = FontWeight.Bold,
                )
            )
        }
        Divider(
            color = colorResource(id = R.color.black),
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
        )

        var messages by remember {
            mutableStateOf(listOf<Message>())
        }
        val mutableListOfMessages = mutableListOf<Message>()
        var editMessageText by remember {
            mutableStateOf(TextFieldValue(""))
        }
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(messages.size) { index ->
                Message(
                    isUserMessage = false,
                    text = messages[index].text,
                    data = messages[index].dateTime
                )
            }
        }
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.Bottom) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = editMessageText,
                placeholder = {
                    Text(
                        text = "Type your message",
                        style = TextStyle(
                            color = colorResource(
                                id = R.color.text_secondary
                            ),
                            fontSize = integerResource(id = R.integer.primary_text_size).sp,
                        )
                    )
                },
                onValueChange = { editMessageText = it },
                trailingIcon = {
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.add_icon),
                            contentDescription = "Add icon",
                            modifier = Modifier
                                .width(32.dp)
                                .height(32.dp)
                        )
                        Box(
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .width(48.dp)
                                .height(32.dp)
                                .background(
                                    color = colorResource(id = R.color.secondary_color),
                                    RoundedCornerShape(100.dp)
                                )
                                .clickable {
                                },
                            contentAlignment = Alignment.Center,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.send_icon),
                                contentDescription = "Enter message",
                                alignment = Alignment.Center,
                                modifier = Modifier
                                    .width(24.dp)
                                    .height(24.dp)
                                    .clickable {
                                        viewModel.sendMessage(editMessageText.text)
                                    }
                            )
                        }
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = colorResource(id = R.color.primary)
                )
            )
        }
    }
}
