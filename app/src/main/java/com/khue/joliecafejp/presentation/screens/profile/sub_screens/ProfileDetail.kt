package com.khue.joliecafejp.presentation.screens.profile.sub_screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.khue.joliecafejp.R
import com.khue.joliecafejp.navigation.nav_screen.ProfileSubScreen
import com.khue.joliecafejp.presentation.common.TextFieldCustom
import com.khue.joliecafejp.presentation.components.ButtonCustom
import com.khue.joliecafejp.presentation.components.CardCustom
import com.khue.joliecafejp.presentation.components.CircleImage
import com.khue.joliecafejp.presentation.components.ImagePickerBottomSheetContent
import com.khue.joliecafejp.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileDetail(
    navController: NavHostController
) {

    val focusManager = LocalFocusManager.current

    val isEdit = remember {
        mutableStateOf(false)
    }
    val isChangePassword = remember {
        mutableStateOf(false)
    }

    val passwordError = remember {
        mutableStateOf("")
    }
    val passwordVisible = rememberSaveable { mutableStateOf(false) }
    val passwordTextState = remember { mutableStateOf(TextFieldValue("password")) }


    val userNameTextState = remember { mutableStateOf(TextFieldValue("Sweet Latte")) }
    val userNameError = remember {
        mutableStateOf("")
    }

    val userPhoneNumberState = remember { mutableStateOf(TextFieldValue("0123548655")) }
    val userPhoneNumberError = remember {
        mutableStateOf("")
    }

    val createNewPassword = rememberSaveable { mutableStateOf(false) }

    val newPasswordTextState = remember { mutableStateOf(TextFieldValue("")) }
    val newPasswordError = remember {
        mutableStateOf("")
    }
    val newPasswordVisible = rememberSaveable { mutableStateOf(false) }

    val confirmNewPasswordState = remember { mutableStateOf(TextFieldValue("")) }
    val confirmNewPasswordError = remember {
        mutableStateOf("")
    }
    val confirmNewPasswordVisible = rememberSaveable { mutableStateOf(false) }


    val scrollState = rememberScrollState()

    // Bottom Sheet
    val coroutineScope = rememberCoroutineScope()
    val state = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

    ModalBottomSheetLayout(
        sheetState = state,
        modifier = Modifier.fillMaxSize(),
        sheetContent = {
            ImagePickerBottomSheetContent(
                coroutineScope = coroutineScope,
                modalBottomSheetState = state
            )
        }
    ) {
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
                            id = ProfileSubScreen.ProfileDetail.titleId
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
                    .verticalScroll(scrollState)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                BoxUserImage(
                    coroutineScope = coroutineScope,
                    state = state
                )

                CardUserEmail()

                CardUserNameAndPhone(
                    isEdit = isEdit,
                    userNameTextState = userNameTextState,
                    userNameError = userNameError,
                    userPhoneNumberState = userPhoneNumberState,
                    userPhoneNumberError = userPhoneNumberError
                )

                AnimatedVisibility(
                    visible = !createNewPassword.value,
                ) {
                    CardChangePassword(
                        createNewPassword = createNewPassword,
                        isChangePassword = isChangePassword,
                        passwordTextState = passwordTextState,
                        passwordError = passwordError,
                        passwordVisible = passwordVisible
                    )
                }

                AnimatedVisibility(
                    visible = createNewPassword.value,
                ) {
                    CardNewPassword(
                        createNewPassword = createNewPassword,
                        newPasswordTextState = newPasswordTextState,
                        newPasswordError = newPasswordError,
                        newPasswordVisible = newPasswordVisible,
                        confirmNewPasswordState = confirmNewPasswordState,
                        confirmNewPasswordError = confirmNewPasswordError,
                        confirmNewPasswordVisible = confirmNewPasswordVisible,
                        focusManager = focusManager
                    )
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BoxUserImage(
    coroutineScope: CoroutineScope,
    state: ModalBottomSheetState
) {
    Box(
        modifier = Modifier.padding(bottom = EXTRA_EXTRA_LARGE_PADDING),
        contentAlignment = Alignment.BottomEnd
    ) {
        CircleImage()
        IconButton(
            modifier = Modifier
                .offset(ICON_BUTTON_PROFILE_OFFSET, ICON_BUTTON_PROFILE_OFFSET)
                .size(ICON_BUTTON_PROFILE_DETAIL_SIZE)
                .clip(CircleShape)
                .background(color = MaterialTheme.colors.greySecondary),
            onClick = {
                coroutineScope.launch { state.show() }
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = stringResource(R.string.image_picker),
                tint = MaterialTheme.colors.textColor
            )
        }
    }
}

@Composable
fun CardUserEmail() {
    CardCustom(onClick = {}) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = LARGE_PADDING, horizontal = EXTRA_LARGE_PADDING),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(R.string.email_title),
                fontFamily = raleway,
                color = MaterialTheme.colors.greySecondary,
                fontSize = MaterialTheme.typography.caption.fontSize,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.padding(top = SMALL_PADDING),
                text = "sweetlatte@gmail.com",
                fontFamily = montserratFontFamily,
                color = MaterialTheme.colors.textColor,
                fontSize = MaterialTheme.typography.body1.fontSize,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun CardUserNameAndPhone(
    isEdit: MutableState<Boolean>,
    userNameTextState: MutableState<TextFieldValue>,
    userNameError: MutableState<String>,
    userPhoneNumberState: MutableState<TextFieldValue>,
    userPhoneNumberError: MutableState<String>,
) {
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
                Text(
                    text = stringResource(R.string.name),
                    fontFamily = raleway,
                    color = MaterialTheme.colors.greySecondary,
                    fontSize = MaterialTheme.typography.caption.fontSize,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        isEdit.value = !isEdit.value
                    },
                    text = if (isEdit.value) stringResource(R.string.save) else stringResource(
                        R.string.edit
                    ),
                    fontFamily = raleway,
                    color = MaterialTheme.colors.titleTextColor,
                    fontSize = MaterialTheme.typography.body2.fontSize,
                    fontWeight = FontWeight.Bold
                )
            }

            TextFieldCustom(
                modifier = Modifier.align(alignment = Alignment.Start),
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
                enabled = isEdit.value
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
                enabled = isEdit.value
            )

        }
    }
}

@Composable
fun CardChangePassword(
    createNewPassword: MutableState<Boolean>,
    isChangePassword: MutableState<Boolean>,
    passwordTextState: MutableState<TextFieldValue>,
    passwordError: MutableState<String>,
    passwordVisible: MutableState<Boolean>
) {
    CardCustom(onClick = {}) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = LARGE_PADDING,
                    horizontal = EXTRA_LARGE_PADDING
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (isChangePassword.value) stringResource(R.string.current_password) else stringResource(
                        id = R.string.password_title
                    ),
                    fontFamily = raleway,
                    color = MaterialTheme.colors.greySecondary,
                    fontSize = MaterialTheme.typography.caption.fontSize,
                    fontWeight = FontWeight.Bold
                )

                AnimatedVisibility(
                    visible = !isChangePassword.value
                ) {
                    Text(
                        modifier = Modifier.clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            isChangePassword.value = true
                            passwordTextState.value = TextFieldValue("")
                        },
                        text = stringResource(R.string.change),
                        fontFamily = raleway,
                        color = MaterialTheme.colors.titleTextColor,
                        fontSize = MaterialTheme.typography.body2.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            TextFieldCustom(
                modifier = Modifier.align(alignment = Alignment.Start),
                textFieldValue = passwordTextState,
                keyBoardType = KeyboardType.Password,
                trailingIcon = {
                    if (passwordError.value.isEmpty()) {
                        val image = if (passwordVisible.value)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        val description =
                            if (passwordVisible.value) stringResource(R.string.hide_password) else stringResource(
                                R.string.show_password
                            )

                        IconButton(
                            onClick = {
                                passwordVisible.value = !passwordVisible.value
                            },
                            enabled = isChangePassword.value
                        ) {
                            Icon(imageVector = image, description)
                        }
                    } else {
                        Icon(
                            Icons.Filled.Error,
                            stringResource(R.string.error),
                            tint = MaterialTheme.colors.error
                        )
                    }

                },
                placeHolder = stringResource(R.string.password_placeholder),
                visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                error = passwordError.value,
                enabled = isChangePassword.value,
                padding = 0.dp,
            )

            AnimatedVisibility(
                visible = isChangePassword.value
            ) {
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
                            isChangePassword.value = false
                            passwordTextState.value = TextFieldValue("password")
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
                            isChangePassword.value = false
                            passwordTextState.value = TextFieldValue("password")
                            createNewPassword.value = true
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

@Composable
fun CardNewPassword(
    createNewPassword: MutableState<Boolean>,
    newPasswordTextState: MutableState<TextFieldValue>,
    newPasswordError: MutableState<String>,
    newPasswordVisible: MutableState<Boolean>,
    confirmNewPasswordState: MutableState<TextFieldValue>,
    confirmNewPasswordError: MutableState<String>,
    confirmNewPasswordVisible: MutableState<Boolean>,
    focusManager: FocusManager
) {
    CardCustom(
        onClick = {},
        paddingValues = PaddingValues(all = EXTRA_LARGE_PADDING)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = LARGE_PADDING,
                    horizontal = EXTRA_LARGE_PADDING
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {

            Text(
                text = stringResource(R.string.new_password),
                fontFamily = raleway,
                color = MaterialTheme.colors.greySecondary,
                fontSize = MaterialTheme.typography.caption.fontSize,
                fontWeight = FontWeight.Bold
            )

            TextFieldCustom(
                modifier = Modifier.align(alignment = Alignment.Start),
                textFieldValue = newPasswordTextState,
                keyBoardType = KeyboardType.Password,
                trailingIcon = {
                    if (newPasswordError.value.isEmpty()) {
                        val image = if (newPasswordVisible.value)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        val description =
                            if (newPasswordVisible.value) stringResource(R.string.hide_password) else stringResource(
                                R.string.show_password
                            )

                        IconButton(onClick = {
                            newPasswordVisible.value = !newPasswordVisible.value
                        }) {
                            Icon(imageVector = image, description)
                        }
                    } else {
                        Icon(
                            Icons.Filled.Error,
                            stringResource(R.string.error),
                            tint = MaterialTheme.colors.error
                        )
                    }
                },
                placeHolder = stringResource(R.string.enter_your_new_password),
                visualTransformation = if (newPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                error = newPasswordError.value,
                padding = 0.dp,
                enabled = createNewPassword.value
            )

            Text(
                modifier = Modifier.padding(top = EXTRA_LARGE_PADDING),
                text = stringResource(id = R.string.confirm_password_title),
                fontFamily = raleway,
                color = MaterialTheme.colors.greySecondary,
                fontSize = MaterialTheme.typography.caption.fontSize,
                fontWeight = FontWeight.Bold
            )

            TextFieldCustom(
                modifier = Modifier.align(alignment = Alignment.Start),
                textFieldValue = confirmNewPasswordState,
                keyBoardType = KeyboardType.Password,
                trailingIcon = {
                    if (confirmNewPasswordError.value.isEmpty()) {
                        val image = if (confirmNewPasswordVisible.value)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        val description =
                            if (confirmNewPasswordVisible.value) stringResource(R.string.hide_password) else stringResource(
                                R.string.show_password
                            )

                        IconButton(onClick = {
                            confirmNewPasswordVisible.value = !confirmNewPasswordVisible.value
                        }) {
                            Icon(imageVector = image, description)
                        }
                    } else {
                        Icon(
                            Icons.Filled.Error,
                            stringResource(R.string.error),
                            tint = MaterialTheme.colors.error
                        )
                    }
                },
                placeHolder = stringResource(id = R.string.confirm_password_placeholder),
                visualTransformation = if (confirmNewPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                error = confirmNewPasswordError.value,
                padding = 0.dp,
                enabled = createNewPassword.value
            )

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
                        focusManager.clearFocus()
                        newPasswordTextState.value = TextFieldValue("")
                        confirmNewPasswordState.value = TextFieldValue("")
                        createNewPassword.value = false
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
                        focusManager.clearFocus()
                        newPasswordTextState.value = TextFieldValue("")
                        confirmNewPasswordState.value = TextFieldValue("")
                        createNewPassword.value = false
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