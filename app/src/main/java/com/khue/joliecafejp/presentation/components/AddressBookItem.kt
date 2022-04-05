package com.khue.joliecafejp.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khue.joliecafejp.R
import com.khue.joliecafejp.presentation.common.TextFieldCustom
import com.khue.joliecafejp.ui.theme.*

@Composable
fun AddressBookItem(
    name: String,
    phoneNumber: String,
    address: String,
    onDelete: () -> Unit,
    onUpdate: (String, String, String) -> Unit,
) {

    var isEdit by remember {
        mutableStateOf(false)
    }

    var isDefaultAddress by remember {
        mutableStateOf(false)
    }

    val userNameTextState = remember { mutableStateOf(TextFieldValue("Sweet Latte")) }
    val userNameError = remember {
        mutableStateOf("")
    }

    val userPhoneNumberState = remember { mutableStateOf(TextFieldValue("0123548655")) }
    val userPhoneNumberError = remember {
        mutableStateOf("")
    }

    val userAddressState = remember { mutableStateOf(TextFieldValue("12 Robusta Street, Frappe District, White City")) }
    val userAddressError = remember {
        mutableStateOf("")
    }

    CardCustom(onClick = {}) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = LARGE_PADDING, horizontal = EXTRA_LARGE_PADDING),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth(if(isEdit) 1f else 0.7f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {

                    AnimatedVisibility(visible = isEdit) {
                        Text(
                            text = stringResource(R.string.name),
                            fontFamily = raleway,
                            color = MaterialTheme.colors.greySecondary,
                            fontSize = MaterialTheme.typography.caption.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    TextFieldCustom(
                        textFieldValue = userNameTextState,
                        keyBoardType = KeyboardType.Text,
                        trailingIcon = {
                            if (userNameError.value.isNotEmpty()) Icon(
                                Icons.Filled.Error,
                                stringResource(R.string.error),
                                tint = MaterialTheme.colors.error
                            )
                        },
                        placeHolder = "Sweet Latte",
                        visualTransformation = VisualTransformation.None,
                        error = userNameError.value,
                        padding = 0.dp,
                        enabled = isEdit,
                    )
                }

                AnimatedVisibility(visible = !isEdit) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = onDelete
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_trash),
                                contentDescription = stringResource(R.string.delete),
                                tint = MaterialTheme.colors.titleTextColor
                            )
                        }
                        IconButton(
                            onClick = {
                                isEdit = true
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_edit),
                                contentDescription = stringResource(R.string.edit),
                                tint = MaterialTheme.colors.titleTextColor
                            )
                        }
                    }
                }
            }

            AnimatedVisibility(visible = isEdit) {
                Text(
                    modifier = Modifier.padding(top = EXTRA_LARGE_PADDING),
                    text = stringResource(R.string.phone_number),
                    fontFamily = raleway,
                    color = MaterialTheme.colors.greySecondary,
                    fontSize = MaterialTheme.typography.caption.fontSize,
                    fontWeight = FontWeight.Bold
                )
            }

            TextFieldCustom(
                modifier = Modifier.align(alignment = Alignment.Start),
                textFieldValue = userPhoneNumberState,
                keyBoardType = KeyboardType.Text,
                trailingIcon = {
                    if (userPhoneNumberError.value.isNotEmpty()) Icon(
                        Icons.Filled.Error,
                        stringResource(R.string.error),
                        tint = MaterialTheme.colors.error
                    )
                },
                placeHolder = "Sweet Latte",
                visualTransformation = VisualTransformation.None,
                error = userPhoneNumberError.value,
                padding = 0.dp,
                enabled = isEdit,
            )

            AnimatedVisibility(visible = isEdit) {
                Text(
                    modifier = Modifier.padding(top = EXTRA_LARGE_PADDING),
                    text = stringResource(R.string.address),
                    fontFamily = raleway,
                    color = MaterialTheme.colors.greySecondary,
                    fontSize = MaterialTheme.typography.caption.fontSize,
                    fontWeight = FontWeight.Bold
                )
            }

            TextFieldCustom(
                modifier = Modifier.align(alignment = Alignment.Start),
                textFieldValue = userAddressState,
                keyBoardType = KeyboardType.Text,
                trailingIcon = {
                    if (userAddressError.value.isNotEmpty()) Icon(
                        Icons.Filled.Error,
                        stringResource(R.string.error),
                        tint = MaterialTheme.colors.error
                    )
                },
                placeHolder = "Sweet Latte",
                visualTransformation = VisualTransformation.None,
                error = userAddressError.value,
                padding = 0.dp,
                enabled = isEdit,
                maxLines = 2,
                singleLine = false
            )

            AnimatedVisibility(visible = isEdit) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Checkbox(
                            checked = isDefaultAddress,
                            onCheckedChange = { checked ->
                                isDefaultAddress = checked
                            },
                            colors = CheckboxDefaults.colors(
                                uncheckedColor = MaterialTheme.colors.textColor,
                                checkedColor = MaterialTheme.colors.titleTextColor
                            )
                        )

                        Text(
                            text = stringResource(R.string.set_as_default_address),
                            fontFamily = raleway,
                            color = MaterialTheme.colors.greySecondary,
                            fontSize = MaterialTheme.typography.caption.fontSize,
                            fontWeight = FontWeight.Bold
                        )

                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        ButtonCustom(
                            buttonContent = stringResource(id = R.string.cancel),
                            backgroundColor = Color.Transparent,
                            textColor = MaterialTheme.colors.textColor,
                            onClick = {
                                      isEdit = false
                            },
                            paddingValues = PaddingValues(top = EXTRA_LARGE_PADDING),
                            contentPadding = PaddingValues(
                                horizontal = EXTRA_LARGE_PADDING,
                                vertical = EXTRA_SMALL_PADDING
                            ),
                            buttonElevation = ButtonDefaults.elevation(
                                defaultElevation = 0.dp,
                                pressedElevation = 0.dp
                            )
                        )
                        ButtonCustom(
                            buttonContent = stringResource(id = R.string.confirm),
                            backgroundColor = MaterialTheme.colors.titleTextColor,
                            textColor = MaterialTheme.colors.textColor,
                            onClick = {
                                      isEdit = false
                            },
                            paddingValues = PaddingValues(
                                start = EXTRA_LARGE_PADDING,
                                top = EXTRA_LARGE_PADDING
                            ),
                            contentPadding = PaddingValues(
                                horizontal = EXTRA_LARGE_PADDING,
                                vertical = EXTRA_SMALL_PADDING
                            ),
                            buttonElevation = null
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun AddressBookPrev() {
    AddressBookItem(
        name = "",
        phoneNumber = "",
        address = "",
        onDelete = {},
        onUpdate = { _, _, _ ->},
    )
}