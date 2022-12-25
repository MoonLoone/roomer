package com.example.roomer.ui_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.roomer.R
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.format.DateTimeFormatter


@Composable
fun DropdownTextField(
    listOfItems: List<String>,
    label: String,
    paddingValues: PaddingValues = PaddingValues(top = 16.dp)
) {
    var selectedItem by remember {
        mutableStateOf(listOfItems[0])
    }
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var textFieldSize by remember {
        mutableStateOf(Size.Zero)
    }
    val icon = if (isExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown
    Column(
        modifier = Modifier.padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            style = TextStyle(
                fontSize = integerResource(id = R.integer.primary_text_size).sp,
                color = Color.Black
            )
        )
        TextField(
            value = selectedItem,
            onValueChange = {
                selectedItem = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                }
                .clickable { isExpanded = !isExpanded },
            enabled = false,
            trailingIcon = {
                Icon(icon, "Dropdown icon")
            },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = colorResource(id = R.color.secondary_color)),
            textStyle = TextStyle(fontSize = 14.sp, color = Color.Black)
        )

        DropdownMenu(
            expanded = isExpanded, onDismissRequest = { isExpanded = false },
            modifier = Modifier.width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
            listOfItems.forEach { text ->
                DropdownMenuItem(onClick = {
                    selectedItem = text
                    isExpanded = false
                }) { Text(text = text) }
            }
        }
    }
}


@Composable
fun SelectSex(paddingValues: PaddingValues = PaddingValues(top = 16.dp)) {
    Column(
        modifier = Modifier.padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        var isMaleSelected by remember {
            mutableStateOf(true)
        }
        Text(
            text = "Sex",
            fontSize = integerResource(id = R.integer.primary_text_size).sp,
            color = Color.Black
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier
                    .height(40.dp)
                    .width(98.dp)
                    .border(
                        1.dp,
                        if (isMaleSelected) colorResource(id = R.color.primary_dark) else Color.Black,
                        RoundedCornerShape(100.dp),
                    )
                    .background(
                        if (isMaleSelected) colorResource(id = R.color.primary_dark) else Color.White,
                        RoundedCornerShape(100.dp)
                    )
                    .clickable {
                        isMaleSelected = true
                    },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = if (isMaleSelected) R.drawable.sex_male_in_icon else R.drawable.sex_male_un_icon),
                    contentDescription = "Male icon",
                    modifier = Modifier
                        .width(18.dp)
                        .height(18.dp),
                )
                Text(
                    text = "Male",
                    modifier = Modifier.padding(start = 4.dp),
                    fontSize = 14.sp,
                    color = if (isMaleSelected) colorResource(
                        id = R.color.secondary_color
                    ) else colorResource(id = R.color.primary_dark)
                )
            }
            Row(
                modifier = Modifier
                    .height(40.dp)
                    .width(98.dp)
                    .border(
                        1.dp,
                        if (!isMaleSelected) colorResource(id = R.color.primary_dark) else Color.Black,
                        RoundedCornerShape(100.dp)
                    )
                    .background(
                        if (!isMaleSelected) colorResource(id = R.color.primary_dark) else Color.White,
                        RoundedCornerShape(100.dp)
                    )
                    .clickable {
                        isMaleSelected = false
                    },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = if (!isMaleSelected) R.drawable.sex_female_in_icon else R.drawable.sex_female_un_icon),
                    contentDescription = "Female icon",
                    modifier = Modifier
                        .width(18.dp)
                        .height(18.dp),
                )
                Text(
                    text = "Female",
                    modifier = Modifier.padding(start = 4.dp),
                    fontSize = 14.sp,
                    color = if (!isMaleSelected) colorResource(
                        id = R.color.secondary_color
                    ) else colorResource(id = R.color.primary_dark)
                )
            }
        }
    }
}


@Composable
fun ScreenTextField(
    textHint: String,
    label: String = "",
    paddingValues: PaddingValues = PaddingValues(top = 16.dp),
    textFieldHeight: Int = 56,
) {
    var text by remember {
        mutableStateOf(TextFieldValue(""))
    }
    Column(
        modifier = Modifier.padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            fontSize = integerResource(id = R.integer.primary_text_size).sp,
            color = Color.Black
        )
        TextField(
            value = text,
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = integerResource(id = R.integer.primary_text_size).sp,
                textAlign = TextAlign.Start,
            ),
            placeholder = {
                Text(
                    text = textHint,
                    fontSize = integerResource(id = R.integer.primary_text_size).sp,
                    color = colorResource(id = R.color.text_secondary)
                )
            },
            onValueChange = { text = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(textFieldHeight.dp)
                .background(colorResource(id = R.color.secondary_color)),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = colorResource(id = R.color.secondary_color))
        )
    }
}


@Composable
fun DateField(
    label: String = "Date field", paddingValues: PaddingValues = PaddingValues(top = 16.dp)
) {
    val dialogState = rememberMaterialDialogState()
    var textState by remember {
        mutableStateOf(TextFieldValue("11.12.2002"))
    }
    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    ) {
        datepicker { date ->
            val formattedDate = date.format(
                DateTimeFormatter.ofPattern("dd.MM.yyyy")
            )
            textState = TextFieldValue(formattedDate)
        }
    }
    Column(
        modifier = Modifier.padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            fontSize = integerResource(id = R.integer.primary_text_size).sp,
            color = Color.Black
        )
        TextField(
            value = textState.text, onValueChange = { },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.calendar_icon),
                    contentDescription = "Calendar icon",
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp),
                )
            },
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = integerResource(id = R.integer.primary_text_size).sp,
                textAlign = TextAlign.Start,
            ),
            modifier = Modifier
                .clickable {
                    dialogState.show()
                }
                .fillMaxWidth()
                .height(56.dp),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = colorResource(id = R.color.secondary_color)),
            enabled = false,
        )
    }
}

@Composable
fun SelectAddressField(
    paddingValues: PaddingValues = PaddingValues(top = 16.dp),
    label: String,
    placeholder: String
) {
    Column(
        modifier = Modifier.padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = TextStyle(
                    fontSize = integerResource(id = R.integer.primary_text_size).sp,
                    color = Color.Black,
                ),
            )
            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp)
                    .background(
                        color = colorResource(id = R.color.primary_dark),
                        RoundedCornerShape(100.dp)
                    )
                    .clickable {

                    },
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.select_adr_icon),
                    contentDescription = "Select address",
                    modifier = Modifier
                        .width(32.dp)
                        .height(32.dp),
                )
            }
        }
        var location by remember {
            mutableStateOf(TextFieldValue(placeholder))
        }
        TextField(
            value = location, onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clickable {

                },
            textStyle = TextStyle(
                fontSize = integerResource(id = R.integer.primary_text_size).sp,
                color = Color.Gray,
            ),
            enabled = false,
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.LightGray)
        )
    }
}

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    label: String = "Password"
) {

    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    val icon = if (passwordVisibility)
        Icons.Filled.Visibility
    else
        Icons.Filled.VisibilityOff

    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            fontSize = integerResource(id = R.integer.primary_text_size).sp,
            color = Color.Black,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Medium
        )
        TextField(
            value = password,
            onValueChange = {
                password = it
            },
            placeholder = { Text(text = "Password") },
            leadingIcon = { Icon(Icons.Outlined.Password, "password icon") },
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(
                        imageVector = icon,
                        "Some stuff"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = if (passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation(),
            modifier = modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.secondary_color)),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = colorResource(id = R.color.secondary_color))
        )
    }
}

@Composable
fun EmailField(modifier: Modifier = Modifier) {

    var email by rememberSaveable { mutableStateOf("") }


    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Email",
            fontSize = integerResource(id = R.integer.primary_text_size).sp,
            color = Color.Black,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Medium
        )
        TextField(
            value = email,
            onValueChange = {
                email = it
            },
            placeholder = { Text(text = "Email") },
            leadingIcon = { Icon(Icons.Outlined.Email, "some icon") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            modifier = modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.secondary_color)),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = colorResource(id = R.color.secondary_color))
        )
    }
}

@Composable
fun InterestField(paddingValues: PaddingValues, label: String) {
    var interestsList by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var openDialog by remember {
        mutableStateOf(false)
    }
    val icon = if (openDialog)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown
    Column() {
        Column(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = label,
                style = TextStyle(
                    fontSize = integerResource(id = R.integer.primary_text_size).sp,
                    color = Color.Black
                )
            )
            TextField(
                value = interestsList.text,
                onValueChange = {
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { openDialog = !openDialog },
                enabled = false,
                trailingIcon = {
                    Icon(icon, "Dropdown icon")
                },
                colors = TextFieldDefaults.textFieldColors(backgroundColor = colorResource(id = R.color.secondary_color)),
                textStyle = TextStyle(fontSize = 14.sp, color = Color.Black)
            )
        }
        if (openDialog) {
            AlertDialog(
                onDismissRequest = { openDialog = false },
                title = { Text("Change interests") },
                text = {

                },
                buttons = {
                    Button(modifier = Modifier.fillMaxWidth(), onClick = { openDialog = false }) {
                        Text("Dismiss")
                    }
                }
            )
        }
    }
}
