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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.roomer.R
import com.example.roomer.domain.model.entities.Message
import com.example.roomer.domain.model.entities.User
import com.example.roomer.presentation.screens.destinations.MessengerScreenDestination
import com.example.roomer.presentation.ui_components.BackBtn
import com.example.roomer.presentation.ui_components.Message
import com.example.roomer.utils.NavbarManagement
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.flowOf

@Destination
@Composable
fun ChatScreen(
    navigator: DestinationsNavigator,
    viewModel: ChatScreenViewModel = hiltViewModel(),
    recipientId: Int,
    chatId: Int
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
        val messages = if (viewModel.socketConnectionState.value) {
            viewModel.messagesPager.collectAsLazyPagingItems()
        }
        else flowOf<PagingData<Message>>(PagingData.empty()).collectAsLazyPagingItems()
        MessagesList(
            messages = messages,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { id -> viewModel.messageRead(id) }
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
        modifier = Modifier.fillMaxWidth()
    ) {
        BackBtn(onBackNavigation = { onNavigateTo.invoke() })
        Image(
            modifier = Modifier
                .width(56.dp)
                .height(56.dp)
                .padding(start = 16.dp),
            painter = painterResource(id = R.drawable.ordinary_client),
            contentDescription = stringResource(R.string.user_avatar_content_description),
            alignment = Alignment.Center
        )
        Text(
            text = stringResource(R.string.username_here),
            modifier = Modifier.padding(start = 8.dp),
            style = TextStyle(
                color = Color.Black,
                fontSize = integerResource(
                    id = R.integer.primary_text
                ).sp,
                fontWeight = FontWeight.Bold
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
private fun MessagesList(
    messages: LazyPagingItems<Message>,
    modifier: Modifier,
    checkMessage: (Int) -> Unit
) {
    val lazyListState: LazyListState = rememberLazyListState()
    LazyColumn(modifier = modifier, state = lazyListState, reverseLayout = true) {
        items(messages) { item ->
            item?.let { message ->
                if (!message.isChecked && message.recipient == User()) {
                    message.isChecked = true
                    checkMessage.invoke(message.id + 1)
                }
                Message(
                    isUserMessage = message.donor == User(),
                    text = message.text,
                    data = message.dateTime
                )
            }
        }
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
                    text = stringResource(R.string.type_your_message),
                    style = TextStyle(
                        color = colorResource(
                            id = R.color.text_secondary
                        ),
                        fontSize = integerResource(id = R.integer.primary_text).sp
                    )
                )
            },
            onValueChange = { editMessageText.value = it },
            trailingIcon = {
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.add_icon),
                        contentDescription = stringResource(R.string.add_icon_placeholder),
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
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.send_icon),
                            contentDescription = stringResource(
                                R.string.enter_message_content_description
                            ),
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
