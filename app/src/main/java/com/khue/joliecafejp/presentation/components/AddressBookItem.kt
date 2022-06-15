package com.khue.joliecafejp.presentation.components

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khue.joliecafejp.R
import com.khue.joliecafejp.domain.model.Address
import com.khue.joliecafejp.presentation.common.ButtonCustom
import com.khue.joliecafejp.presentation.common.CardCustom
import com.khue.joliecafejp.presentation.common.TextFieldCustom
import com.khue.joliecafejp.presentation.viewmodels.AddressBookViewModel
import com.khue.joliecafejp.presentation.viewmodels.UserSharedViewModel
import com.khue.joliecafejp.ui.theme.*
import com.khue.joliecafejp.utils.ApiResult
import com.khue.joliecafejp.utils.Constants.Companion.SNACK_BAR_STATUS_ERROR
import com.khue.joliecafejp.utils.Constants.Companion.SNACK_BAR_STATUS_SUCCESS
import kotlinx.coroutines.launch

@Composable
fun AddressBookItem(
    address: Address,
    isDefaultAddress: Boolean,
    addressBookViewModel: AddressBookViewModel,
    userSharedViewModel: UserSharedViewModel,
    paddingValues: PaddingValues = PaddingValues(
        top = EXTRA_LARGE_PADDING,
        start = EXTRA_LARGE_PADDING,
        end = EXTRA_LARGE_PADDING,
        bottom = 0.dp
    ),
    onDelete: () -> Unit,
    onUpdate: (String, String, String) -> Unit,
    onUpdateDefaultAddress: () -> Unit,
    showMessage: (String, Int) -> Unit
) {

    var isEdit by remember {
        mutableStateOf(false)
    }
    var startObserverEdit by remember {
        mutableStateOf(false)
    }

    val (userNameTextState, userNameTextChange) = remember { mutableStateOf(address.userName) }
    var userNameError by remember {
        mutableStateOf("")
    }

    val (userPhoneNumberState, userPhoneNumberChange) = remember { mutableStateOf(address.phone) }
    var userPhoneNumberError by remember {
        mutableStateOf("")
    }

    val (userAddressState, userAddressChange) = remember { mutableStateOf(address.address) }
    var userAddressError by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = startObserverEdit) {
        if(startObserverEdit) {
            launch {
                addressBookViewModel.updateAddressResponse.collect { result ->
                    when (result) {
                        is ApiResult.Success -> {
                            showMessage("Update address successfully", SNACK_BAR_STATUS_SUCCESS)
                            isEdit = false
                            startObserverEdit = false
                        }
                        is ApiResult.Error -> {
                            showMessage("Update address failed!", SNACK_BAR_STATUS_ERROR)
                        }
                        else -> {}
                    }

                }
            }

            launch {
                userSharedViewModel.updateUserInfosResponse.collect { updateUserInfosResponse ->
                    when(updateUserInfosResponse) {
                        is ApiResult.NullDataSuccess -> {
                            showMessage("Update default address successfully", SNACK_BAR_STATUS_SUCCESS)
                            isEdit = false
                            startObserverEdit = false
                        }
                        is ApiResult.Error -> {
                            showMessage("Update default address failed!", SNACK_BAR_STATUS_ERROR)
                        }
                        else -> {}
                    }
                }
            }
        }
    }



    CardCustom(
        onClick = {},
        paddingValues = paddingValues,
        haveBorder = isDefaultAddress
    ) {
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
                    modifier = Modifier.fillMaxWidth(if (isEdit) 1f else 0.7f),
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
                        onTextChange = {
                            userNameTextChange(it)
                        },
                        keyBoardType = KeyboardType.Text,
                        trailingIcon = {
                            if (userNameError.isNotEmpty()) Icon(
                                Icons.Filled.Error,
                                stringResource(R.string.error),
                                tint = MaterialTheme.colors.error
                            )
                        },
                        placeHolder = "Sweet Latte",
                        visualTransformation = VisualTransformation.None,
                        error = userNameError,
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
                onTextChange = {
                    userPhoneNumberChange(it)
                },
                keyBoardType = KeyboardType.Text,
                trailingIcon = {
                    if (userPhoneNumberError.isNotEmpty()) Icon(
                        Icons.Filled.Error,
                        stringResource(R.string.error),
                        tint = MaterialTheme.colors.error
                    )
                },
                placeHolder = "Sweet Latte",
                visualTransformation = VisualTransformation.None,
                error = userPhoneNumberError,
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
                onTextChange = {
                    userAddressChange(it)
                },
                keyBoardType = KeyboardType.Text,
                trailingIcon = {
                    if (userAddressError.isNotEmpty()) Icon(
                        Icons.Filled.Error,
                        stringResource(R.string.error),
                        tint = MaterialTheme.colors.error
                    )
                },
                placeHolder = "Sweet Latte",
                visualTransformation = VisualTransformation.None,
                error = userAddressError,
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
                                if(checked) {
                                    startObserverEdit = true
                                    onUpdateDefaultAddress()
                                }
                            },
                            enabled = !isDefaultAddress,
                            colors = CheckboxDefaults.colors(
                                uncheckedColor = MaterialTheme.colors.textColor,
                                checkedColor = MaterialTheme.colors.titleTextColor,
                                disabledColor = MaterialTheme.colors.titleTextColor
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

                                // clear error
                                userNameError = ""
                                userAddressError = ""
                                userPhoneNumberError = ""

                                // return original state
                                userNameTextChange(address.userName)
                                userPhoneNumberChange(address.phone)
                                userAddressChange(address.address)
                            },
                            paddingValues = PaddingValues(top = EXTRA_LARGE_PADDING),
                            contentPadding = PaddingValues(
                                horizontal = EXTRA_LARGE_PADDING,
                                vertical = MEDIUM_SMALL_PADDING
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
                                val listError = addressBookViewModel.validateAddressItem(
                                    userName = userNameTextState,
                                    phone = userPhoneNumberState,
                                    address = userAddressState
                                )

                                val haveError = listError.any { !it.successful }

                                if(haveError) {
                                    userNameError = listError[0].errorMessage
                                    userAddressError = listError[2].errorMessage
                                    userPhoneNumberError = listError[1].errorMessage
                                } else {
                                    startObserverEdit = true
                                    userNameError = ""
                                    userAddressError = ""
                                    userPhoneNumberError = ""
                                    onUpdate(userNameTextState, userPhoneNumberState, userAddressState)
                                }
                            },
                            paddingValues = PaddingValues(
                                start = EXTRA_LARGE_PADDING,
                                top = EXTRA_LARGE_PADDING
                            ),
                            contentPadding = PaddingValues(
                                horizontal = EXTRA_LARGE_PADDING,
                                vertical = MEDIUM_SMALL_PADDING
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
//    AddressBookItem(
//        name = "",
//        phoneNumber = "",
//        address = "",
//        onDelete = {},
//    ) { _, _, _ -> }
}