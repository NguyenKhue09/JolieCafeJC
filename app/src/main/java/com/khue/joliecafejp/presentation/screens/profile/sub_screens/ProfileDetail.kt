package com.khue.joliecafejp.presentation.screens.profile.sub_screens

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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.khue.joliecafejp.R
import com.khue.joliecafejp.navigation.nav_screen.ProfileSubScreen
import com.khue.joliecafejp.presentation.common.TextFieldCustom
import com.khue.joliecafejp.presentation.components.CardCustom
import com.khue.joliecafejp.presentation.components.CircleImage
import com.khue.joliecafejp.presentation.components.ImagePickerBottomSheetContent
import com.khue.joliecafejp.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileDetail(
    navController: NavHostController
) {

    var isEdit by remember {
        mutableStateOf(false)
    }

    val userNameTextState = remember { mutableStateOf(TextFieldValue("Sweet Latte")) }
    val userNameError by remember {
        mutableStateOf("")
    }

    val userPhoneNumberState = remember { mutableStateOf(TextFieldValue("0123548655")) }
    val userPhoneNumberError by remember {
        mutableStateOf("")
    }

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
                                    isEdit = !isEdit
                                },
                                text = if(isEdit) stringResource(R.string.save) else stringResource(R.string.edit),
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
                            enabled = isEdit
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
                                if (userNameError.isNotEmpty()) Icon(
                                    Icons.Filled.Error,
                                    stringResource(R.string.error),
                                    tint = MaterialTheme.colors.error
                                )
                            },
                            placeHolder = "Sweet Latte",
                            visualTransformation = VisualTransformation.None,
                            error = userPhoneNumberError,
                            padding = 0.dp,
                            enabled = isEdit
                        )

                    }
                }
            }
        }
    }


//    BottomSheetScaffold(
//        scaffoldState = bottomSheetScaffoldState,
//        sheetPeekHeight = 0.dp,
//        backgroundColor = MaterialTheme.colors.greyPrimary,
//        topBar = {
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Start
//            ) {
//                IconButton(onClick = { navController.popBackStack() }) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.ic_arrow_back),
//                        contentDescription = stringResource(
//                            id = ProfileSubScreen.ProfileDetail.titleId
//                        ),
//                        tint = MaterialTheme.colors.textColor
//                    )
//                }
//                Text(
//                    text = stringResource(
//                        id = ProfileSubScreen.ProfileDetail.titleId
//                    ),
//                    fontSize = MaterialTheme.typography.h6.fontSize,
//                    fontFamily = ralewayMedium,
//                    color = MaterialTheme.colors.titleTextColor
//                )
//            }
//        },
//        sheetContent = {
//            ImagePickerBottomSheetContent(
//                coroutineScope = coroutineScope,
//                bottomSheetScaffoldState = bottomSheetScaffoldState
//            )
//        }
//    ) {
//
//
//    }
}