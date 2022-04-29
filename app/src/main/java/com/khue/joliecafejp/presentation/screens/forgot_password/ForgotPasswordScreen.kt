package com.khue.joliecafejp.presentation.screens.forgot_password

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.khue.joliecafejp.R
import com.khue.joliecafejp.firebase.firebase_authentication.gmail_password_authentication.FirebaseGmailPasswordAuth
import com.khue.joliecafejp.presentation.common.TextCustom
import com.khue.joliecafejp.presentation.common.TextFieldCustom
import com.khue.joliecafejp.ui.theme.*

@Composable
fun ForgotPassword(navController: NavHostController) {

    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val (emailTextState, onEmailChange) = remember { mutableStateOf("") }
    var emailError by remember {
        mutableStateOf("")
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(MaterialTheme.colors.greyPrimary)
    ) {
        Text(
            text = stringResource(R.string.app_title),
            fontFamily = festiveFontFamily,
            color = MaterialTheme.colors.textColor,
            fontSize = 75.sp,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier.padding(top = 90.dp),
            text = stringResource(R.string.forgot_password_title),
            fontFamily = raleway,
            color = MaterialTheme.colors.titleTextColor,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = Modifier.padding(top = 32.dp, start = 20.dp, end = 20.dp),
            text = stringResource(R.string.forgot_password_sub_title),
            fontFamily = raleway,
            color = MaterialTheme.colors.titleTextColor,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
        )

        TextCustom(
            content = stringResource(R.string.user_name_title),
            modifier = Modifier
                .padding(top = 32.dp, start = 20.dp)
                .align(alignment = Alignment.Start),
            color = MaterialTheme.colors.textColor2,
        )

        TextFieldCustom(
            modifier = Modifier.align(alignment = Alignment.Start),
            textFieldValue = emailTextState,
            onTextChange = {
                           onEmailChange(it)
            },
            keyBoardType = KeyboardType.Email,
            trailingIcon = {
                if (emailError.isNotEmpty()) {
                    Icon(
                        Icons.Filled.Error,
                        stringResource(R.string.error),
                        tint = MaterialTheme.colors.error
                    )
                }
            },
            placeHolder = stringResource(R.string.email_placeholder),
            visualTransformation = VisualTransformation.None,
            error = emailError
        )

        Row(
            modifier = Modifier
                .padding(top = 44.dp, bottom = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                modifier = Modifier
                    .padding(end = 20.dp),
                onClick = {
                    navController.popBackStack()
                },
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent
                ),
                contentPadding = PaddingValues(all = 10.dp)
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    fontFamily = raleway,
                    color = MaterialTheme.colors.titleTextColor,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                modifier = Modifier
                    .padding(end = 20.dp),
                onClick = {
                    validateEmail(email = emailTextState.trim()) {
                        emailError = it
                    }
                    if (emailError.isEmpty()) {
                        FirebaseGmailPasswordAuth().forgotPassword(
                            email = emailTextState.trim(),
                            context= context,
                            navController = navController
                        )
                    }
                },
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.titleTextColor
                ),
                contentPadding = PaddingValues(all = 10.dp)
            ) {
                Text(
                    text = stringResource(R.string.confirm),
                    fontFamily = raleway,
                    color = MaterialTheme.colors.textColor,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

fun validateEmail(email: String, onError: (String) -> Unit) {
    when {
        email.trim().isEmpty() -> onError("Email is empty")
        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> onError("Your email wrong format")
        else -> onError("")
    }
}