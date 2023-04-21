package com.example.roomer.presentation.ui_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.roomer.R
import com.example.roomer.domain.model.login_sign_up.interests.InterestModel
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.format.DateTimeFormatter

@Composable
fun DropdownTextFieldMapped(
    mapOfItems: Map<String, String>,
    label: String,
    value: String, // There gonna be keys
    onValueChange: (String) -> Unit,
    enabled: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }

    var isExpanded by remember {
        mutableStateOf(false)
    }
    var textFieldSize by remember {
        mutableStateOf(Size.Zero)
    }
    val icon = if (isExpanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.column_elements_small_margin)
        )
    ) {
        Text(
            text = label,
            style = TextStyle(
                fontSize = integerResource(id = R.integer.primary_text).sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )
        )
        TextField(
            value = mapOfItems.getValue(value),
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                }
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    if (enabled) {
                        isExpanded = !isExpanded
                    }
                },
            enabled = false,
            trailingIcon = {
                Icon(icon, stringResource(R.string.dropdown_icon))
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(id = R.color.secondary_color)
            ),
            textStyle = TextStyle(
                fontSize = integerResource(id = R.integer.primary_text).sp,
                color = Color.Black
            )
        )

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = Modifier.width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
            mapOfItems.forEach { entry ->
                DropdownMenuItem(
                    onClick = {
                        onValueChange(entry.key)
                        isExpanded = false
                    }
                ) { Text(text = entry.value) }
            }
        }
    }
}

@Composable
fun SexField(
    value: String,
    onValueChange: (String) -> Unit,
    title: String = stringResource(R.string.select_sex_label),
    enabled: Boolean = true
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.column_elements_small_margin)
        )
    ) {
        Text(
            text = title,
            fontSize = integerResource(id = R.integer.primary_text).sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.row_ordinal_padding)
            )
        ) {
            if (value == "M") {
                GreenButtonPrimaryIconed(
                    text = stringResource(R.string.male),
                    onClick = {},
                    trailingIcon = Icons.Filled.Male,
                    enabled = enabled
                )
                GreenButtonOutlineIconed(
                    text = stringResource(R.string.female),
                    onClick = { onValueChange("F") },
                    trailingIcon = Icons.Filled.Female,
                    enabled = enabled
                )
            } else {
                GreenButtonOutlineIconed(
                    text = stringResource(R.string.male),
                    onClick = { onValueChange("M") },
                    trailingIcon = Icons.Filled.Male,
                    enabled = enabled
                )
                GreenButtonPrimaryIconed(
                    text = stringResource(R.string.female),
                    onClick = {},
                    trailingIcon = Icons.Filled.Female,
                    enabled = enabled
                )
            }
        }
    }
}

@Composable
fun AccountScreenTextField(
    textHint: String,
    label: String = "",
    paddingValues: PaddingValues = PaddingValues(),
    textFieldHeight: Dp = dimensionResource(id = R.dimen.ordinal_text_field_height),
    text: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue(""))
) {
    Column(
        modifier = Modifier.padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            fontSize = integerResource(id = R.integer.primary_text).sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        TextField(
            value = text.value,
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = integerResource(id = R.integer.primary_text).sp,
                textAlign = TextAlign.Start
            ),
            placeholder = {
                Text(
                    text = textHint,
                    fontSize = integerResource(id = R.integer.primary_text).sp,
                    color = colorResource(id = R.color.text_secondary)
                )
            },
            onValueChange = { text.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(textFieldHeight)
                .background(colorResource(id = R.color.secondary_color)),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(id = R.color.secondary_color)
            )
        )
    }
}

@Composable
fun DateField(
    label: String = "Date field",
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true
) {
    val dialogState = rememberMaterialDialogState()
    var textState by remember {
        mutableStateOf(TextFieldValue("11.12.2002"))
    }
    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton(stringResource(R.string.positive_button_label))
            negativeButton(stringResource(R.string.negative_button_label))
        }
    ) {
        datepicker { date ->
            val formattedDate = date.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
            )
            onValueChange(formattedDate)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            fontSize = integerResource(id = R.integer.primary_text).sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium
        )
        TextField(
            value = value,
            onValueChange = { },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.calendar_icon),
                    contentDescription = stringResource(R.string.calendar_icon),
                    modifier = Modifier
                        .width(dimensionResource(id = R.dimen.ordinary_icon))
                        .height(dimensionResource(id = R.dimen.ordinary_icon))
                )
            },
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = integerResource(id = R.integer.primary_text).sp,
                textAlign = TextAlign.Start
            ),
            modifier = Modifier
                .clickable {
                    if (enabled) {
                        dialogState.show()
                    }
                }
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(id = R.color.secondary_color)
            ),
            enabled = false
        )
    }
}

@Composable
fun SelectAddressField(
    paddingValues: PaddingValues = PaddingValues(),
    label: String,
    placeholder: String
) {
    Column(
        modifier = Modifier.padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.column_elements_small_margin)
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = TextStyle(
                    fontSize = integerResource(id = R.integer.primary_text).sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium
                )
            )
            Box(
                modifier = Modifier
                    .width(dimensionResource(id = R.dimen.select_address_box))
                    .height(dimensionResource(id = R.dimen.select_address_box))
                    .background(
                        color = colorResource(id = R.color.primary_dark),
                        RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner_full))
                    )
                    .clickable {
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.select_adr_icon),
                    contentDescription = stringResource(id = R.string.select_addr_label),
                    modifier = Modifier
                        .width(dimensionResource(id = R.dimen.big_icon))
                        .height(dimensionResource(id = R.dimen.big_icon))
                )
            }
        }
        val location by remember {
            mutableStateOf(TextFieldValue(placeholder))
        }
        TextField(
            value = location,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.ordinal_text_field_height))
                .clickable {
                },
            textStyle = TextStyle(
                fontSize = integerResource(id = R.integer.primary_text).sp,
                color = Color.Gray
            ),
            enabled = false,
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.LightGray)
        )
    }
}

@Composable
fun PasswordField(
    modifier: Modifier = Modifier,
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    isError: Boolean = false,
    errorMessage: String = ""
) {
    var visibility by rememberSaveable {
        mutableStateOf(false)
    }

    val icon = if (visibility) {
        Icons.Filled.Visibility
    } else {
        Icons.Filled.VisibilityOff
    }

    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.column_elements_small_margin)
        )
    ) {
        Text(
            text = label,
            fontSize = integerResource(id = R.integer.primary_text).sp,
            color = Color.Black,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Medium
        )
        TextField(
            singleLine = true,
            enabled = enabled,
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = placeholder) },
            leadingIcon = {
                Icon(
                    Icons.Outlined.Password,
                    stringResource(R.string.icon_description)
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        visibility = !visibility
                    }
                ) {
                    Icon(
                        imageVector = icon,
                        stringResource(R.string.icon_description)
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = if (visibility) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            modifier = modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.secondary_color)),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(id = R.color.secondary_color),
                focusedIndicatorColor = colorResource(id = R.color.primary_dark)
            ),
            isError = isError
        )
        if (isError) {
            Text(
                color = Color.Red,
                text = errorMessage
            )
        }
    }
}

@Composable
fun EmailField(
    modifier: Modifier = Modifier,
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    isError: Boolean = false,
    errorMessage: String = ""
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.column_elements_small_margin)
        )
    ) {
        Text(
            text = label,
            fontSize = integerResource(id = R.integer.primary_text).sp,
            color = Color.Black,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Medium
        )
        TextField(
            singleLine = true,
            enabled = enabled,
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = placeholder) },
            leadingIcon = { Icon(Icons.Filled.Email, stringResource(R.string.icon_description)) },
            trailingIcon = {
                if (isError) {
                    Icon(
                        Icons.Filled.Error,
                        stringResource(R.string.error_icon_description),
                        tint = MaterialTheme.colors.error
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            modifier = modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.secondary_color)),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(id = R.color.secondary_color),
                focusedIndicatorColor = colorResource(id = R.color.primary_dark)
            ),
            isError = isError
        )
        if (isError) {
            Text(
                color = Color.Red,
                text = errorMessage
            )
        }
    }
}

@Composable
fun UsualTextField(
    title: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String = "",
    enabled: Boolean = true,
    singleLine: Boolean = true,
    maxLines: Int = 5,
    isNumbersInput: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.column_elements_small_margin)
        )
    ) {
        Text(
            text = title,
            fontSize = integerResource(id = R.integer.primary_text).sp,
            color = Color.Black,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Medium
        )
        TextField(
            maxLines = maxLines,
            singleLine = singleLine,
            value = value,
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = integerResource(id = R.integer.primary_text).sp,
                textAlign = TextAlign.Start
            ),
            enabled = enabled,
            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = integerResource(id = R.integer.primary_text).sp,
                    color = colorResource(id = R.color.text_secondary)
                )
            },
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.secondary_color)),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(id = R.color.secondary_color),
                focusedIndicatorColor = colorResource(id = R.color.primary_dark)
            ),
            isError = isError,
            keyboardOptions = if (isNumbersInput) {
                KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            } else {
                KeyboardOptions(keyboardType = KeyboardType.Text)
            }
        )
        if (isError) {
            Text(
                color = Color.Red,
                text = errorMessage
            )
        }
    }
}

@Composable
fun IconedTextField(
    title: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: ImageVector,
    enabled: Boolean = true,
    isError: Boolean = false,
    errorMessage: String = "",
    singleLine: Boolean = true,
    maxLines: Int = 5
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.column_elements_small_margin)
        )
    ) {
        Text(
            text = title,
            fontSize = integerResource(id = R.integer.primary_text).sp,
            color = Color.Black,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Medium
        )
        TextField(
            maxLines = maxLines,
            singleLine = singleLine,
            leadingIcon = { Icon(icon, stringResource(R.string.icon_description)) },
            value = value,
            enabled = enabled,
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = integerResource(id = R.integer.primary_text).sp,
                textAlign = TextAlign.Start
            ),
            trailingIcon = {
                if (isError) {
                    Icon(
                        Icons.Filled.Error,
                        stringResource(R.string.error_icon_description),
                        tint = MaterialTheme.colors.error
                    )
                }
            },

            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = integerResource(id = R.integer.primary_text).sp,
                    color = colorResource(id = R.color.text_secondary)
                )
            },
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.secondary_color)),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(id = R.color.secondary_color),
                focusedIndicatorColor = colorResource(id = R.color.primary_dark)
            ),
            isError = isError
        )
        if (isError) {
            Text(
                color = Color.Red,
                text = errorMessage
            )
        }
    }
}

@Composable
fun InterestField(
    paddingValues: PaddingValues,
    label: String,
    values: List<InterestModel>,
    selectedItems: List<InterestModel>,
    onSelectedChange: (List<InterestModel>) -> Unit
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    val icon = if (isExpanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    val interactionSource = remember { MutableInteractionSource() }
    var textFieldSize by remember {
        mutableStateOf(Size.Zero)
    }
    val enabled = true
    Column {
        Text(
            text = label,
            style = TextStyle(
                fontSize = integerResource(id = R.integer.primary_text).sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.padding(5.dp)
        )
        TextField(
            value = "...",
            onValueChange = { },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                }
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    if (enabled) {
                        isExpanded = !isExpanded
                    }
                },
            enabled = false,
            trailingIcon = {
                Icon(icon, stringResource(R.string.dropdown_icon))
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(id = R.color.secondary_color)
            ),
            textStyle = TextStyle(
                fontSize = integerResource(id = R.integer.primary_text).sp,
                color = Color.Black
            )
        )
        if (isExpanded) {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .background(colorResource(id = R.color.secondary_color)),
                verticalArrangement = Arrangement.spacedBy(
                    dimensionResource(id = R.dimen.column_elements_small_margin)
                )
            ) {
                for (item in values) {
                    var checkedState by remember {
                        mutableStateOf(false)
                    }
                    Row {
                        Checkbox(
                            checked = checkedState,
                            onCheckedChange = {
                                checkedState = it
                                if (checkedState) {
                                    selectedItems.plus(item).let(onSelectedChange)
                                } else {
                                    selectedItems.minus(item).let(onSelectedChange)
                                }
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = colorResource(id = R.color.primary_dark)
                            )
                        )
                        Text(
                            item.interest,
                            style = TextStyle(
                                fontSize = integerResource(id = R.integer.primary_text).sp,
                                color = colorResource(id = R.color.text_secondary)
                            ),
                            modifier = Modifier.padding(top = 10.dp)
                        )
                    }
                    Divider(
                        color = Color.Black,
                        modifier = Modifier.padding(
                            top = dimensionResource(id = R.dimen.divider_top_padding),
                            bottom = dimensionResource(
                                id = R.dimen.screen_bottom_margin
                            )
                        )
                    )
                }
            }
        }
    }
}
