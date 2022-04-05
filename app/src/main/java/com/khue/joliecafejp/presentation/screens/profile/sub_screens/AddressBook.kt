package com.khue.joliecafejp.presentation.screens.profile.sub_screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.navigation.NavHostController
import com.khue.joliecafejp.R
import com.khue.joliecafejp.navigation.nav_screen.ProfileSubScreen
import com.khue.joliecafejp.presentation.common.TextFieldCustom
import com.khue.joliecafejp.presentation.components.AddressBookItem
import com.khue.joliecafejp.presentation.components.ButtonCustom
import com.khue.joliecafejp.presentation.components.CardCustom
import com.khue.joliecafejp.ui.theme.*

@Composable
fun AddressBook(
    navController: NavHostController
) {

    val scrollState = rememberScrollState()
    val nestedScrollState = rememberScrollState()

    val isAddNewAddress = remember {
        mutableStateOf(false)
    }

    val isDefaultAddress = remember {
        mutableStateOf(false)
    }

    val newUserNameTextState = remember { mutableStateOf(TextFieldValue("")) }
    val newUserNameError = remember {
        mutableStateOf("")
    }

    val newUserPhoneNumberState = remember { mutableStateOf(TextFieldValue("")) }
    val newUserPhoneNumberError = remember {
        mutableStateOf("")
    }

    val newUserAddressState =
        remember { mutableStateOf(TextFieldValue("")) }
    val newUserAddressError = remember {
        mutableStateOf("")
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.greyPrimary,
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = stringResource(
                            id = ProfileSubScreen.ProfileDetail.titleId
                        ),
                        tint = MaterialTheme.colors.textColor
                    )
                }
                Text(
                    text = stringResource(
                        id = ProfileSubScreen.AddressBook.titleId
                    ),
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    fontFamily = ralewayMedium,
                    color = MaterialTheme.colors.titleTextColor
                )
            }
        },
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier
                    .verticalScroll(state = nestedScrollState),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                repeat(5) {
                    AddressBookItem(
                        name = "",
                        phoneNumber = "",
                        address = "",
                        onDelete = {},
                        onUpdate = { _, _, _ -> },
                    )
                }
            }

            CardAddNewAddress(
                isAddNewAddress = isAddNewAddress,
                isDefaultAddress = isDefaultAddress,
                newUserNameTextState = newUserNameTextState,
                newUserNameError = newUserNameError,
                newUserPhoneNumberState = newUserPhoneNumberState,
                newUserPhoneNumberError = newUserPhoneNumberError,
                newUserAddressState = newUserAddressState,
                newUserAddressError = newUserAddressError,
            ) {

            }

            Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
        }

    }
}

@Composable
fun CardAddNewAddress(
    isAddNewAddress: MutableState<Boolean>,
    isDefaultAddress: MutableState<Boolean>,
    newUserNameTextState: MutableState<TextFieldValue>,
    newUserNameError: MutableState<String>,
    newUserPhoneNumberState: MutableState<TextFieldValue>,
    newUserPhoneNumberError: MutableState<String>,
    newUserAddressState: MutableState<TextFieldValue>,
    newUserAddressError: MutableState<String>,
    onAddNewAddress: () -> Unit,
) {

    CardCustom(onClick = {}) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = LARGE_PADDING, horizontal = EXTRA_LARGE_PADDING),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {

            AnimatedVisibility(visible = !isAddNewAddress.value) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.add_new_address),
                        fontFamily = raleway,
                        color = MaterialTheme.colors.textColor,
                        fontSize = MaterialTheme.typography.subtitle2.fontSize,
                        fontWeight = FontWeight.Bold
                    )

                    IconButton(
                        onClick = {
                            isAddNewAddress.value = true
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add),
                            contentDescription = stringResource(id = R.string.add_new_address),
                            tint = MaterialTheme.colors.titleTextColor
                        )
                    }
                }
            }

            AnimatedVisibility(visible = isAddNewAddress.value) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = stringResource(R.string.name),
                        fontFamily = raleway,
                        color = MaterialTheme.colors.greySecondary,
                        fontSize = MaterialTheme.typography.caption.fontSize,
                        fontWeight = FontWeight.Bold
                    )

                    TextFieldCustom(
                        textFieldValue = newUserNameTextState,
                        keyBoardType = KeyboardType.Text,
                        trailingIcon = {
                            if (newUserNameError.value.isNotEmpty()) Icon(
                                Icons.Filled.Error,
                                stringResource(R.string.error),
                                tint = MaterialTheme.colors.error
                            )
                        },
                        placeHolder = stringResource(R.string.enter_your_new_user_name),
                        visualTransformation = VisualTransformation.None,
                        error = newUserNameError.value,
                        padding = 0.dp,
                        enabled = isAddNewAddress.value,
                    )

                    Text(
                        modifier = Modifier.padding(top = EXTRA_LARGE_PADDING),
                        text = stringResource(R.string.phone_number),
                        fontFamily = raleway,
                        color = MaterialTheme.colors.greySecondary,
                        fontSize = MaterialTheme.typography.caption.fontSize,
                        fontWeight = FontWeight.Bold
                    )

                    TextFieldCustom(
                        modifier = Modifier.align(alignment = Alignment.Start),
                        textFieldValue = newUserPhoneNumberState,
                        keyBoardType = KeyboardType.Text,
                        trailingIcon = {
                            if (newUserPhoneNumberError.value.isNotEmpty()) Icon(
                                Icons.Filled.Error,
                                stringResource(R.string.error),
                                tint = MaterialTheme.colors.error
                            )
                        },
                        placeHolder = stringResource(R.string.enter_your_new_phone_number),
                        visualTransformation = VisualTransformation.None,
                        error = newUserPhoneNumberError.value,
                        padding = 0.dp,
                        enabled = isAddNewAddress.value,
                    )


                    Text(
                        modifier = Modifier.padding(top = EXTRA_LARGE_PADDING),
                        text = stringResource(R.string.address),
                        fontFamily = raleway,
                        color = MaterialTheme.colors.greySecondary,
                        fontSize = MaterialTheme.typography.caption.fontSize,
                        fontWeight = FontWeight.Bold
                    )


                    TextFieldCustom(
                        modifier = Modifier.align(alignment = Alignment.Start),
                        textFieldValue = newUserAddressState,
                        keyBoardType = KeyboardType.Text,
                        trailingIcon = {
                            if (newUserAddressError.value.isNotEmpty()) Icon(
                                Icons.Filled.Error,
                                stringResource(R.string.error),
                                tint = MaterialTheme.colors.error
                            )
                        },
                        placeHolder = stringResource(R.string.enter_your_new_address),
                        visualTransformation = VisualTransformation.None,
                        error = newUserAddressError.value,
                        padding = 0.dp,
                        enabled = isAddNewAddress.value,
                    )



                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Checkbox(
                            checked = isDefaultAddress.value,
                            onCheckedChange = { checked ->
                                isDefaultAddress.value = checked
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
                                isAddNewAddress.value = false
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
                            buttonContent = stringResource(R.string.add),
                            backgroundColor = MaterialTheme.colors.titleTextColor,
                            textColor = MaterialTheme.colors.textColor,
                            onClick = {
                                isAddNewAddress.value = false
                                onAddNewAddress()
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
fun CardAddNewAddressPrev() {
    val isAddNewAddress = remember {
        mutableStateOf(true)
    }

    val isDefaultAddress = remember {
        mutableStateOf(false)
    }

    val newUserNameTextState = remember { mutableStateOf(TextFieldValue("")) }
    val newUserNameError = remember {
        mutableStateOf("")
    }

    val newUserPhoneNumberState = remember { mutableStateOf(TextFieldValue("")) }
    val newUserPhoneNumberError = remember {
        mutableStateOf("")
    }

    val newUserAddressState =
        remember { mutableStateOf(TextFieldValue("")) }
    val newUserAddressError = remember {
        mutableStateOf("")
    }

    CardAddNewAddress(
        isAddNewAddress = isAddNewAddress,
        isDefaultAddress = isDefaultAddress,
        newUserNameTextState = newUserNameTextState,
        newUserNameError = newUserNameError,
        newUserPhoneNumberState = newUserPhoneNumberState,
        newUserPhoneNumberError = newUserPhoneNumberError,
        newUserAddressState = newUserAddressState,
        newUserAddressError = newUserAddressError,
    ) {

    }

}