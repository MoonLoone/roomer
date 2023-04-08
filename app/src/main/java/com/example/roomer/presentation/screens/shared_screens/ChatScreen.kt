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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
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
import kotlinx.coroutines.launch

@Destination
@Composable
fun ChatScreen(
    navigator: DestinationsNavigator,
    viewModel: ChatScreenViewModel = hiltViewModel(),
    recipientId: Int,
    chatId: Int,
) {
    NavbarManagement.hideNavbar()
    viewModel.startChat(recipientId, chatId)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = dimensionResource(id = R.dimen.screen_top_margin),
                start = dimensionResource(id = R.dimen.screen_start_margin),
                end = dimensionResource(id = R.dimen.screen_end_margin),
                bottom = dimensionResource(id = R.dimen.screen_bottom_margin)
            )
    ) {
        TopLine { navigator.navigate(MessengerScreenDestination) }
        val messageText = remember {
            mutableStateOf(TextFieldValue(""))
        }
        val messages = viewModel.messages.collectAsState()
        MessagesList(
            messages = messages.value,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        )
        EnterMessage(
            editMessageText = messageText,
            onSend = { message -> viewModel.sendMessage(message) }
        )
    }
}

@Composable
private fun TopLine(onNavigateTo: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        BackBtn(onBackNavigation = { onNavigateTo.invoke() })
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
                    id = R.integer.primary_text
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
}

@Composable
private fun MessagesList(messages: List<Message>, modifier: Modifier) {
    val lazyListState: LazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    LazyColumn(modifier = modifier, state = lazyListState) {
        items(messages.size) { index ->
            Message(
                isUserMessage = false,
                text = messages[index].text,
                data = messages[index].dateTime
            )
        }
        scope.launch { lazyListState.scrollToItem(messages.size) }
    }
}

@Composable
private fun EnterMessage(
    editMessageText: MutableState<TextFieldValue>,
    onSend: (message: String) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = editMessageText.value,
            placeholder = {
                Text(
                    text = "Type your message",
                    style = TextStyle(
                        color = colorResource(
                            id = R.color.text_secondary
                        ),
                        fontSize = integerResource(id = R.integer.primary_text).sp,
                    )
                )
            },
            onValueChange = { editMessageText.value = it },
            trailingIcon = {
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.add_icon),
                        contentDescription = "Add icon",
                        modifier = Modifier
                            .width(dimensionResource(id = R.dimen.big_icon))
                            .height(dimensionResource(id = R.dimen.big_icon))
                    )
                    Box(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .width(48.dp)
                            .height(32.dp)
                            .background(
                                color = colorResource(id = R.color.secondary_color),
                                RoundedCornerShape(
                                    dimensionResource(id = R.dimen.rounded_corner_full)
                                )
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
                                .width(dimensionResource(id = R.dimen.ordinary_icon))
                                .height(dimensionResource(id = R.dimen.ordinary_icon))
                                .clickable {
                                    onSend(editMessageText.value.text)
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
