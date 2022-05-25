package com.khue.joliecafejp.presentation.screens.profile.sub_screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.animateScrollBy
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.khue.joliecafejp.R
import com.khue.joliecafejp.domain.model.AddNewAddressFormState
import com.khue.joliecafejp.domain.model.Address
import com.khue.joliecafejp.firebase.firebase_authentication.gmail_password_authentication.FirebaseGmailPasswordAuth
import com.khue.joliecafejp.navigation.nav_screen.ProfileSubScreen
import com.khue.joliecafejp.presentation.common.*
import com.khue.joliecafejp.presentation.components.*
import com.khue.joliecafejp.presentation.viewmodels.AddressBookViewModel
import com.khue.joliecafejp.presentation.viewmodels.UserSharedViewModel
import com.khue.joliecafejp.ui.theme.*
import com.khue.joliecafejp.utils.AddNewAddressFormEvent
import com.khue.joliecafejp.utils.ApiResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AddressBook(
    navController: NavHostController,
    addressBookViewModel: AddressBookViewModel = hiltViewModel()
) {

    val userToken by addressBookViewModel.userToken.collectAsState(initial = "")

    var showDeleteCustomDialog by remember { mutableStateOf(false) }


    var isAddNewAddress by remember {
        mutableStateOf(false)
    }

    val addNewAddressFormState by addressBookViewModel.addNewAddressFormState.collectAsState()

    LaunchedEffect(key1 = userToken) {
        addressBookViewModel.getProducts(userToken)
    }

    val addressBooks = addressBookViewModel.addressBooks.collectAsLazyPagingItems()

    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        addressBookViewModel.validationEvents.collect { event ->
            when (event) {
                is AddressBookViewModel.ValidationEvent.Success -> {
                    val data = mapOf(
                        "phone" to addNewAddressFormState.phone,
                        "userName" to addNewAddressFormState.userName,
                        "address" to addNewAddressFormState.address
                    )
                    if (addNewAddressFormState.isDefault) {
                        addressBookViewModel.addNewDefaultAddress(
                            data = data,
                            token = userToken
                        )
                    } else {
                        addressBookViewModel.addNewAddress(
                            data = data,
                            token = userToken
                        )
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        launch {
            addressBookViewModel.addNewAddressResponse.collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        isAddNewAddress = false
                        addressBooks.refresh()
                        Toast.makeText(context, "Add new address successfully", Toast.LENGTH_SHORT)
                            .show()
                    }
                    is ApiResult.Error -> {
                        Toast.makeText(context, "Add new address failed!", Toast.LENGTH_SHORT)
                            .show()
                    }
                    else -> {}
                }
            }
        }
        launch {
            addressBookViewModel.addNewDefaultAddressResponse.collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        isAddNewAddress = false
                        addressBooks.refresh()
                        Toast.makeText(
                            context,
                            "Add new default address successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is ApiResult.Error -> {
                        Toast.makeText(
                            context,
                            "Add new default address failed!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {}
                }

            }
        }
        launch {
            addressBookViewModel.updateAddressResponse.collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        Toast.makeText(context, "Update address successfully", Toast.LENGTH_SHORT)
                            .show()
                    }
                    is ApiResult.Error -> {
                        Toast.makeText(context, "Update address failed!", Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }

            }
        }
        launch {
            addressBookViewModel.deleteAddressResponse.collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        Toast.makeText(context, "Delete address successfully", Toast.LENGTH_SHORT)
                            .show()
                        addressBooks.refresh()
                    }
                    is ApiResult.Error -> {
                        Toast.makeText(context, "Delete address failed!", Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }

            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.greyPrimary,
        topBar = {
            TopBar(
                titleId = ProfileSubScreen.AddressBook.titleId,
                navController = navController
            )
        },
    ) {

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
            ) {
                val result = handlePagingResult(addressBooks = addressBooks)

                androidx.compose.animation.AnimatedVisibility(result) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        itemsIndexed(addressBooks) { index, address ->
                            address?.let {
                                if (showDeleteCustomDialog) {
                                    CustomDialog(
                                        title = "Delete address?",
                                        content = "Do you really want to delete this address?",
                                        onDismiss = { showDeleteCustomDialog = false },
                                        onNegativeClick = {
                                            showDeleteCustomDialog = false
                                            addressBookViewModel.deleteAddress(
                                                addressId = address.id,
                                                token = userToken
                                            )
                                        },
                                        onPositiveClick = {
                                            showDeleteCustomDialog = false
                                        }
                                    )
                                }

                                AddressBookItem(
                                    address = address,
                                    paddingValues = if (index == addressBooks.itemCount - 1) PaddingValues(
                                        top = EXTRA_LARGE_PADDING,
                                        start = EXTRA_LARGE_PADDING,
                                        end = EXTRA_LARGE_PADDING,
                                        bottom = EXTRA_LARGE_PADDING
                                    ) else PaddingValues(
                                        top = EXTRA_LARGE_PADDING,
                                        start = EXTRA_LARGE_PADDING,
                                        end = EXTRA_LARGE_PADDING,
                                        bottom = 0.dp
                                    ),
                                    onDelete = {
                                        showDeleteCustomDialog = true
                                    },
                                ) { _, _, _ -> }
                            }
                        }
                    }
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(EXTRA_LARGE_PADDING)
                        .align(Alignment.BottomCenter)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    MaterialTheme.colors.greyPrimary,
                                ),
                            )
                        )
                )
            }

            CardAddNewAddress(
                isAddNewAddress = isAddNewAddress,
                addNewAddressFormState = addNewAddressFormState,
                onNewUserNameChange = { username ->
                    addressBookViewModel.onEvent(
                        event = AddNewAddressFormEvent.UserNameChanged(
                            username = username
                        )
                    )
                },
                onNewUserPhoneNumberChange = { phone ->
                    addressBookViewModel.onEvent(
                        event = AddNewAddressFormEvent.PhoneChanged(
                            phone = phone
                        )
                    )
                },
                onNewUserAddressChange = { address ->
                    addressBookViewModel.onEvent(
                        event = AddNewAddressFormEvent.AddressChanged(
                            address = address
                        )
                    )
                },
                onAddNewAddress = {
                    addressBookViewModel.onEvent(
                        event = AddNewAddressFormEvent.Submit
                    )
                },
                onCancel = {
                    addressBookViewModel.clearErrorAddNewAddressFormState()
                    addressBookViewModel.clearDataAddNewAddressFormState()
                    isAddNewAddress = false
                },
                onDefaultChange = { isDefault ->
                    addressBookViewModel.onEvent(
                        event = AddNewAddressFormEvent.IsDefaultAddressChanged(
                            isDefault = isDefault
                        )
                    )
                },
                enableForm = {
                    isAddNewAddress = true
                }
            )

            Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
        }

    }
}

@Composable
fun CardAddNewAddress(
    isAddNewAddress: Boolean,
    addNewAddressFormState: AddNewAddressFormState,
    onAddNewAddress: () -> Unit,
    onCancel: () -> Unit,
    onNewUserNameChange: (String) -> Unit,
    onNewUserPhoneNumberChange: (String) -> Unit,
    onNewUserAddressChange: (String) -> Unit,
    onDefaultChange: (Boolean) -> Unit,
    enableForm: () -> Unit
) {

    CardCustom(
        modifier = Modifier,
        paddingValues = PaddingValues(
            top = 0.dp,
            start = EXTRA_LARGE_PADDING,
            end = EXTRA_LARGE_PADDING,
            bottom = 0.dp
        ),
        onClick = {}
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = LARGE_PADDING, horizontal = EXTRA_LARGE_PADDING),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {

            AnimatedVisibility(visible = !isAddNewAddress) {
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
                            enableForm()
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

            AnimatedVisibility(visible = isAddNewAddress) {
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
                        textFieldValue = addNewAddressFormState.userName,
                        onTextChange = {
                            onNewUserNameChange(it)
                        },
                        keyBoardType = KeyboardType.Text,
                        trailingIcon = {
                            if (addNewAddressFormState.userNameError.isNotEmpty()) Icon(
                                Icons.Filled.Error,
                                stringResource(R.string.error),
                                tint = MaterialTheme.colors.error
                            )
                        },
                        placeHolder = stringResource(R.string.enter_your_new_user_name),
                        visualTransformation = VisualTransformation.None,
                        error = addNewAddressFormState.userNameError,
                        padding = 0.dp,
                        enabled = isAddNewAddress,
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
                        textFieldValue = addNewAddressFormState.phone,
                        onTextChange = {
                            onNewUserPhoneNumberChange(it)
                        },
                        keyBoardType = KeyboardType.Number,
                        trailingIcon = {
                            if (addNewAddressFormState.phoneError.isNotEmpty()) Icon(
                                Icons.Filled.Error,
                                stringResource(R.string.error),
                                tint = MaterialTheme.colors.error
                            )
                        },
                        placeHolder = stringResource(R.string.enter_your_new_phone_number),
                        visualTransformation = VisualTransformation.None,
                        error = addNewAddressFormState.phoneError,
                        padding = 0.dp,
                        enabled = isAddNewAddress,
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
                        textFieldValue = addNewAddressFormState.address,
                        onTextChange = {
                            onNewUserAddressChange(it)
                        },
                        keyBoardType = KeyboardType.Text,
                        trailingIcon = {
                            if (addNewAddressFormState.addressError.isNotEmpty()) Icon(
                                Icons.Filled.Error,
                                stringResource(R.string.error),
                                tint = MaterialTheme.colors.error
                            )
                        },
                        placeHolder = stringResource(R.string.enter_your_new_address),
                        visualTransformation = VisualTransformation.None,
                        error = addNewAddressFormState.addressError,
                        padding = 0.dp,
                        enabled = isAddNewAddress,
                    )



                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Checkbox(
                            checked = addNewAddressFormState.isDefault,
                            onCheckedChange = { checked ->
                                onDefaultChange(checked)
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
                                onCancel()
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
                            buttonContent = stringResource(R.string.add),
                            backgroundColor = MaterialTheme.colors.titleTextColor,
                            textColor = MaterialTheme.colors.textColor,
                            onClick = {
                                onAddNewAddress()
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

@Composable
fun handlePagingResult(
    addressBooks: LazyPagingItems<Address>
): Boolean {
    addressBooks.apply {
        val error = when {
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            else -> null
        }

        return when {
            loadState.refresh is LoadState.Loading -> {
                LoadingBody()
                false
            }
            error != null -> {
                Toast.makeText(LocalContext.current, error.error.message, Toast.LENGTH_SHORT).show()
                false
            }
            addressBooks.itemCount < 1 -> {
                false
            }
            else -> {
                true
            }
        }
    }
}

@Preview
@Composable
fun CardAddNewAddressPrev() {
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

//    CardAddNewAddress(
//        isAddNewAddress = isAddNewAddress,
//        isDefaultAddress = isDefaultAddress,
//        newUserNameTextState = newUserNameTextState,
//        newUserNameError = newUserNameError,
//        newUserPhoneNumberState = newUserPhoneNumberState,
//        newUserPhoneNumberError = newUserPhoneNumberError,
//        newUserAddressState = newUserAddressState,
//        newUserAddressError = newUserAddressError,
//    ) {
//
//    }

}