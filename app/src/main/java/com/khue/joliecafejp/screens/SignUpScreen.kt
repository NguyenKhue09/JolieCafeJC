package com.khue.joliecafejp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khue.joliecafejp.R
import com.khue.joliecafejp.ui.theme.*

@Composable
fun SignUpScreen() {

    val scrollState = rememberScrollState()

    val userNameTextState = remember { mutableStateOf(TextFieldValue("")) }
    val passwordTextState = remember { mutableStateOf(TextFieldValue("")) }
    val emailTextState = remember { mutableStateOf(TextFieldValue("")) }
    val confirmPasswordTextState = remember { mutableStateOf(TextFieldValue("")) }

    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var confirmPasswordVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Text(
            text = stringResource(R.string.app_title),
            fontFamily = festiveFontFamily,
            color = MaterialTheme.colors.textColor,
            fontSize = 75.sp,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier.padding(top = 40.dp),
            text = stringResource(R.string.sign_up_title),
            fontFamily = raleway,
            color = MaterialTheme.colors.titleTextColor,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        TextCustom(
            content = stringResource(R.string.user_name_title),
            modifier = Modifier
                .padding(top = 28.dp, start = 20.dp)
                .align(alignment = Alignment.Start),
            color = MaterialTheme.colors.textColor2,
        )

        TextFieldCustom(
            modifier = Modifier.align(alignment = Alignment.Start),
            textFieldValue = userNameTextState,
            keyBoardType = KeyboardType.Text,
            trailingIcon = null,
            placeHolder = stringResource(R.string.username_placeholder),
            visualTransformation = VisualTransformation.None
        )

        TextCustom(
            content = stringResource(R.string.email_title),
            modifier = Modifier
                .padding(top = 20.dp, start = 20.dp)
                .align(alignment = Alignment.Start),
            color = MaterialTheme.colors.textColor2,
        )

        TextFieldCustom(
            modifier = Modifier.align(alignment = Alignment.Start),
            textFieldValue = emailTextState,
            keyBoardType = KeyboardType.Email,
            trailingIcon = null,
            placeHolder = stringResource(R.string.email_placeholder),
            visualTransformation = VisualTransformation.None
        )

        TextCustom(
            content = stringResource(R.string.password_title),
            modifier = Modifier
                .padding(top = 20.dp, start = 20.dp)
                .align(alignment = Alignment.Start),
            color = MaterialTheme.colors.textColor2,
        )

        TextFieldCustom(
            modifier = Modifier.align(alignment = Alignment.Start),
            textFieldValue = passwordTextState,
            keyBoardType = KeyboardType.Password,
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                val description =
                    if (passwordVisible) stringResource(R.string.hide_password) else stringResource(
                        R.string.show_password
                    )

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, description)
                }
            },
            placeHolder = stringResource(R.string.password_placeholder),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        )

        TextCustom(
            content = stringResource(R.string.confirm_password_title),
            modifier = Modifier
                .padding(top = 20.dp, start = 20.dp)
                .align(alignment = Alignment.Start),
            color = MaterialTheme.colors.textColor2,
        )

        TextFieldCustom(
            modifier = Modifier.align(alignment = Alignment.Start),
            textFieldValue = confirmPasswordTextState,
            keyBoardType = KeyboardType.Password,
            trailingIcon = {
                val image = if (confirmPasswordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                val description =
                    if (confirmPasswordVisible) stringResource(R.string.hide_password) else stringResource(
                        R.string.show_password
                    )

                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    Icon(imageVector = image, description)
                }
            },
            placeHolder = stringResource(R.string.confirm_password_placeholder),
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        )

        Button(
            modifier = Modifier.padding(top = 32.dp),
            onClick = { },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.greyPrimaryVariant
            )
        ) {
            Text(
                text = stringResource(R.string.sign_up_content),
                fontFamily = raleway,
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
            )
        }

        TextCustom(
            modifier = Modifier
                .padding(top = 24.dp),
            content = stringResource(R.string.or),
            color = MaterialTheme.colors.textColor,
        )

        FaceOrGoogleLogin()

        Text(
            modifier = Modifier
                .padding(bottom = 20.dp, top = 32.dp),
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.textColor,
                        fontSize = 14.sp,
                        fontFamily = raleway,
                    )
                ) {
                    append(stringResource(R.string.already_signed_up))
                }
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.titleTextColor,
                        fontSize = 14.sp,
                        fontFamily = raleway,
                    )
                ) {
                    append(stringResource(R.string.sign_in_here))
                }
            }
        )
    }
}


@Composable
fun TextCustom(content: String, modifier: Modifier, color: Color) {

    Text(
        modifier = modifier,
        text = content,
        fontFamily = raleway,
        color = color,
        fontSize = 16.sp,
        textAlign = TextAlign.Left,
    )
}

@Composable
fun TextFieldCustom(
    modifier: Modifier,
    textFieldValue: MutableState<TextFieldValue>,
    keyBoardType: KeyboardType,
    trailingIcon: @Composable() (() -> Unit)?,
    placeHolder: String,
    visualTransformation: VisualTransformation,
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp),
        value = textFieldValue.value,
        onValueChange = {
            textFieldValue.value = it
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = MaterialTheme.colors.titleTextColor,
            cursorColor = MaterialTheme.colors.textColor,
            textColor = MaterialTheme.colors.textColor,
        ),
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyBoardType,
            imeAction = ImeAction.Next
        ),
        trailingIcon = trailingIcon,
        placeholder = {
            Text(
                text = placeHolder,
                fontFamily = raleway,
                color = MaterialTheme.colors.textColor,
                fontSize = 13.sp,
                textAlign = TextAlign.Left,
            )
        },
        visualTransformation = visualTransformation
    )
}

@Composable
fun FaceOrGoogleLogin() {
    Row(
        modifier = Modifier.padding(top = 32.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .padding(end = 8.dp)
                .height(36.dp)
                .width(36.dp)
                .clickable {

                },
            painter = painterResource(id = R.drawable.fb),
            contentDescription = "Facebook"
        )
        Image(
            modifier = Modifier
                .height(40.dp)
                .width(40.dp)
                .clickable {

                },
            painter = painterResource(id = R.drawable.gg),
            contentDescription = "Google"
        )

    }
}