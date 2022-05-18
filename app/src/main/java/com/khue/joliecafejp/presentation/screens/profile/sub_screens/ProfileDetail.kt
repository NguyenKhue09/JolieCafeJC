package com.khue.joliecafejp.presentation.screens.profile.sub_screens

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.khue.joliecafejp.R
import com.khue.joliecafejp.domain.model.ProfileUpdateFormState
import com.khue.joliecafejp.navigation.nav_graph.AUTHENTICATION_ROUTE
import com.khue.joliecafejp.navigation.nav_screen.BottomBarScreen
import com.khue.joliecafejp.navigation.nav_screen.ProfileSubScreen
import com.khue.joliecafejp.presentation.common.*
import com.khue.joliecafejp.presentation.components.*
import com.khue.joliecafejp.presentation.viewmodels.UserSharedViewModel
import com.khue.joliecafejp.presentation.viewmodels.ProfileDetailViewModel
import com.khue.joliecafejp.ui.theme.*
import com.khue.joliecafejp.utils.ApiResult
import com.khue.joliecafejp.utils.ProfileUpdateFormEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileDetail(
    navController: NavHostController,
    userSharedViewModel: UserSharedViewModel,
    profileDetailViewModel: ProfileDetailViewModel = hiltViewModel()
) {

    val focusManager = LocalFocusManager.current

    val validateState by profileDetailViewModel.state.collectAsState()

    val userLoginResponse by userSharedViewModel.userInfos.collectAsState()
    val userToken by userSharedViewModel.userToken.collectAsState(initial = "")

    val isEdit = remember {
        mutableStateOf(false)
    }
    val isChangePassword = remember {
        mutableStateOf(false)
    }

    val passwordVisible = rememberSaveable { mutableStateOf(false) }
    var createNewPassword  by rememberSaveable { mutableStateOf(false) }
    val newPasswordVisible = rememberSaveable { mutableStateOf(false) }
    val confirmNewPasswordVisible = rememberSaveable { mutableStateOf(false) }


    val scrollState = rememberScrollState()

    // Bottom Sheet
    val coroutineScope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

    var isGGorFaceLogin by remember {
        mutableStateOf(true)
    }


    FirebaseAuth.getInstance().currentUser?.getIdToken(false)?.addOnCompleteListener {
        if (it.isSuccessful) {
            val result = it.result.signInProvider
            if (result.equals("password")) {
                isGGorFaceLogin = false
            }
        }
    }

    LaunchedEffect(key1 = true) {
        if (userLoginResponse.data == null) {
            userSharedViewModel.getUserInfos(token = userToken)
        } else {
            profileDetailViewModel.onPhoneAndNameChangeEvent(
                ProfileUpdateFormEvent.UserNameChanged(username = userLoginResponse.data!!.fullName)
            )
            profileDetailViewModel.onPhoneAndNameChangeEvent(
                ProfileUpdateFormEvent.UserPhoneNumberChanged(userPhoneNumber = userLoginResponse.data!!.phone ?: "You don't have phone number")
            )
        }
    }

    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        profileDetailViewModel.validationEvents.collect { event ->
            when (event) {
                is ProfileDetailViewModel.ValidationEvent.PhoneAndNameSuccess -> {
                    userSharedViewModel.updateUserInfos(
                        token = userToken,
                        userInfos = mapOf(
                            "fullname" to validateState.userName,
                            "phone" to validateState.userPhoneNumber
                        )
                    )
                }
                is ProfileDetailViewModel.ValidationEvent.CurrentPasswordValid -> {
                    profileDetailViewModel.reAuthentication(password = validateState.currentPassword)
                }
                is ProfileDetailViewModel.ValidationEvent.ReAuthenticationSuccess -> {
                    isChangePassword.value = false
                    createNewPassword = true
                    profileDetailViewModel.onCurrentPasswordChangeEvent(event = ProfileUpdateFormEvent.CurrentPasswordChanged(currentPassword = "password"))
                }
                is ProfileDetailViewModel.ValidationEvent.NewPasswordValid -> {
                    profileDetailViewModel.updateNewPassword(password = validateState.newPassword)
                }
                is ProfileDetailViewModel.ValidationEvent.ChangePasswordSuccess -> {
                    createNewPassword = false
                    Toast.makeText(context, "Change password success", Toast.LENGTH_SHORT).show()
                    cleanChangePasswordFormState(
                        focusManager = focusManager,
                        profileDetailViewModel = profileDetailViewModel
                    )
                    logOutUser(userSharedViewModel, navController)
                }
                is ProfileDetailViewModel.ValidationEvent.ChangePasswordFailed -> {
                    createNewPassword = true
                    Toast.makeText(context, "Change password failed!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LaunchedEffect(key1 = true) {
        userSharedViewModel.updateUserInfosResponse.collect { result ->
            when(result) {
                is ApiResult.NullDataSuccess -> {
                    isEdit.value = false
                    Toast.makeText(context, "Update phone and name success", Toast.LENGTH_SHORT).show()
                }
                is ApiResult.Error -> {
                    isEdit.value = true
                    Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }


    DisposableEffect(key1 = Unit) {
        onDispose {
            userSharedViewModel.cleanUpdateUserInfosResponse()
        }
    }

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        modifier = Modifier.fillMaxSize(),
        sheetContent = {
            ImagePickerBottomSheetContent(
                onHideImagePickerBottomSheet = {
                    coroutineScope.launch { modalBottomSheetState.hide() }
                }
            )
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            backgroundColor = MaterialTheme.colors.greyPrimary,
            topBar = {
                TopBar(
                    titleId = ProfileSubScreen.ProfileDetail.titleId,
                    navController = navController
                )
            },
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .verticalScroll(scrollState)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                BoxUserImage(
                    coroutineScope = coroutineScope,
                    state = modalBottomSheetState,
                    isGGorFaceLogin = isGGorFaceLogin,
                    image = userLoginResponse.data?.thumbnail
                        ?: FirebaseAuth.getInstance().currentUser?.photoUrl.toString()
                )

                CardUserEmail(
                    userEmail = userLoginResponse.data?.email ?: "Can't load your email"
                )

                CardUserNameAndPhone(
                    isEdit = isEdit,
                    validateState = validateState,
                    onUserNameChange = { userName ->
                        profileDetailViewModel.onPhoneAndNameChangeEvent(
                            ProfileUpdateFormEvent.UserNameChanged(
                                username = userName
                            )
                        )
                    },
                    onUserPhoneNumberChange = { phoneNumber ->
                        profileDetailViewModel.onPhoneAndNameChangeEvent(
                            ProfileUpdateFormEvent.UserPhoneNumberChanged(
                                userPhoneNumber = phoneNumber
                            )
                        )
                    },
                    onSaveUserData = {
                        if(isEdit.value) profileDetailViewModel.onPhoneAndNameChangeEvent(ProfileUpdateFormEvent.PhoneAndNameSubmit)
                        isEdit.value = true
                    }
                )

                AnimatedVisibility(
                    visible = !createNewPassword,
                ) {
                    CardChangePassword(
                        validateState = validateState,
                        isGGorFaceLogin = isGGorFaceLogin,
                        isChangePassword = isChangePassword,
                        passwordVisible = passwordVisible,
                        onPasswordChange = { currentPassword ->
                            profileDetailViewModel.onCurrentPasswordChangeEvent(event = ProfileUpdateFormEvent.CurrentPasswordChanged(currentPassword = currentPassword))
                        },
                        onConfirmCurrentPassword = {
                            profileDetailViewModel.onCurrentPasswordChangeEvent(event = ProfileUpdateFormEvent.CurrentPasswordSubmit)
                        }
                    )
                }

                AnimatedVisibility(
                    visible = createNewPassword,
                ) {
                    CardNewPassword(
                        createNewPassword = createNewPassword,
                        validateState = validateState,
                        newPasswordVisible = newPasswordVisible,
                        confirmNewPasswordVisible = confirmNewPasswordVisible,
                        onNewPasswordChange = { newPassword ->
                            profileDetailViewModel.onCreateNewPasswordChangeEvent(event = ProfileUpdateFormEvent.NewPasswordChanged(newPassword = newPassword))
                        },
                        onNewConfirmPasswordChange = { confirmPassword ->
                            profileDetailViewModel.onCreateNewPasswordChangeEvent(event = ProfileUpdateFormEvent.ConfirmPasswordChanged(confirmPassword = confirmPassword))
                        },
                        onSubmitNewPassword = {
                            profileDetailViewModel.onCreateNewPasswordChangeEvent(event = ProfileUpdateFormEvent.ChangeNewPasswordSubmit)
                        },
                        onCancelChangeNewPassword = {
                            createNewPassword = false
                            cleanChangePasswordFormState(
                                focusManager = focusManager,
                                profileDetailViewModel = profileDetailViewModel
                            )
                        }
                    )
                }
            }
        }
    }
}

fun logOutUser(userSharedViewModel: UserSharedViewModel, navController: NavHostController) {
    FirebaseAuth.getInstance().signOut()
    userSharedViewModel.signOut()
    navController.navigate(AUTHENTICATION_ROUTE) {
        popUpTo(BottomBarScreen.Home.route) {
            inclusive = true
        }
        launchSingleTop = true
    }
}

fun cleanChangePasswordFormState(
    focusManager: FocusManager,
    profileDetailViewModel: ProfileDetailViewModel
) {
    focusManager.clearFocus()
    profileDetailViewModel.onCreateNewPasswordChangeEvent(event = ProfileUpdateFormEvent.NewPasswordChanged(newPassword = ""))
    profileDetailViewModel.onCreateNewPasswordChangeEvent(event = ProfileUpdateFormEvent.ConfirmPasswordChanged(confirmPassword = ""))
    profileDetailViewModel.cleanChangeNewPasswordError()
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BoxUserImage(
    coroutineScope: CoroutineScope,
    state: ModalBottomSheetState,
    isGGorFaceLogin: Boolean,
    image: String
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier.padding(bottom = EXTRA_EXTRA_LARGE_PADDING),
        contentAlignment = Alignment.BottomEnd
    ) {
        CustomImage(
            image = image
        )
        IconButton(
            modifier = Modifier
                .offset(ICON_BUTTON_PROFILE_OFFSET, ICON_BUTTON_PROFILE_OFFSET)
                .size(ICON_BUTTON_PROFILE_DETAIL_SIZE)
                .clip(CircleShape)
                .background(color = MaterialTheme.colors.greySecondary),
            onClick = {
                if (isGGorFaceLogin) {
                    Toast.makeText(
                        context,
                        "Only login by Email and Password can change avatar",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    coroutineScope.launch { state.show() }
                }
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
fun CardUserEmail(
    userEmail: String
) {
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
                text = userEmail,
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
    validateState: ProfileUpdateFormState,
    onUserNameChange: (String) -> Unit,
    onUserPhoneNumberChange: (String) -> Unit,
    onSaveUserData: () -> Unit
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
                        onSaveUserData()
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
                textFieldValue = validateState.userName,
                onTextChange = {
                    onUserNameChange(it)
                },
                keyBoardType = KeyboardType.Text,
                trailingIcon = {
                    if (validateState.userNameError.isNotEmpty()) Icon(
                        Icons.Filled.Error,
                        stringResource(R.string.error),
                        tint = MaterialTheme.colors.error
                    )
                },
                placeHolder = "Sweet Latte",
                visualTransformation = VisualTransformation.None,
                error = validateState.userNameError,
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
                textFieldValue = validateState.userPhoneNumber,
                onTextChange = {
                    onUserPhoneNumberChange(it)
                },
                keyBoardType = KeyboardType.Phone,
                trailingIcon = {
                    if (validateState.userPhoneNumberError.isNotEmpty()) Icon(
                        Icons.Filled.Error,
                        stringResource(R.string.error),
                        tint = MaterialTheme.colors.error
                    )
                },
                placeHolder = "Sweet Latte",
                visualTransformation = VisualTransformation.None,
                error = validateState.userPhoneNumberError,
                padding = 0.dp,
                enabled = isEdit.value
            )

        }
    }
}

@Composable
fun CardChangePassword(
    validateState: ProfileUpdateFormState,
    isGGorFaceLogin: Boolean,
    isChangePassword: MutableState<Boolean>,
    passwordVisible: MutableState<Boolean>,
    onPasswordChange: (String) -> Unit,
    onConfirmCurrentPassword: () -> Unit,
) {

    val context = LocalContext.current

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
                            if (isGGorFaceLogin) {
                                Toast.makeText(
                                    context,
                                    "Only login by Email and Password can change password",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                isChangePassword.value = true
                                onPasswordChange("")
                            }
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
                textFieldValue = validateState.currentPassword,
                onTextChange = {
                    onPasswordChange(it)
                },
                keyBoardType = KeyboardType.Password,
                trailingIcon = {
                    if (validateState.currentPasswordError.isEmpty()) {
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
                error = validateState.currentPasswordError,
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
                            onPasswordChange("password")
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
                            onConfirmCurrentPassword()
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

@Composable
fun CardNewPassword(
    createNewPassword: Boolean,
    validateState: ProfileUpdateFormState,
    newPasswordVisible: MutableState<Boolean>,
    confirmNewPasswordVisible: MutableState<Boolean>,
    onNewPasswordChange: (String) -> Unit,
    onNewConfirmPasswordChange: (String) -> Unit,
    onSubmitNewPassword: () -> Unit,
    onCancelChangeNewPassword: () -> Unit
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
                textFieldValue = validateState.newPassword,
                onTextChange = {
                    onNewPasswordChange(it)
                },
                keyBoardType = KeyboardType.Password,
                trailingIcon = {
                    if (validateState.newPasswordError.isEmpty()) {
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
                error = validateState.newPasswordError,
                padding = 0.dp,
                enabled = createNewPassword
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
                textFieldValue = validateState.confirmPassword,
                onTextChange = {
                    onNewConfirmPasswordChange(it)
                },
                keyBoardType = KeyboardType.Password,
                trailingIcon = {
                    if (validateState.confirmPasswordError.isEmpty()) {
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
                error = validateState.confirmPasswordError,
                padding = 0.dp,
                enabled = createNewPassword
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
                        onCancelChangeNewPassword()
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
                        onSubmitNewPassword()
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