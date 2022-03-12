package com.khue.joliecafejp.screens

import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khue.joliecafejp.R
import com.khue.joliecafejp.ui.theme.*

@Composable
fun LoginScreen() {

    val userNameTextState = remember { mutableStateOf(TextFieldValue()) }
    val passwordTextState = remember { mutableStateOf(TextFieldValue()) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.app_title),
            fontFamily = festiveFontFamily,
            color = MaterialTheme.colors.textColor,
            fontSize = 75.sp,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier.padding(top = 60.dp),
            text = stringResource(R.string.login_title),
            fontFamily = raleway,
            color = MaterialTheme.colors.titleTextColor,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = Modifier
                .padding(top = 32.dp, start = 20.dp)
                .align(alignment = Alignment.Start),
            text = stringResource(R.string.user_name_title),
            fontFamily = raleway,
            color = MaterialTheme.colors.textColor,
            fontSize = 16.sp,
            textAlign = TextAlign.Left,
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
                .align(alignment = Alignment.Start),
            value = userNameTextState.value,
            onValueChange = {
                userNameTextState.value = it
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = MaterialTheme.colors.titleTextColor,
                cursorColor = MaterialTheme.colors.textColor,
                textColor = MaterialTheme.colors.textColor,
            ),
            maxLines = 1
        )

        Text(
            modifier = Modifier
                .padding(top = 32.dp, start = 24.dp)
                .align(alignment = Alignment.Start),
            text = stringResource(R.string.password_title),
            fontFamily = raleway,
            color = MaterialTheme.colors.textColor,
            fontSize = 16.sp,
            textAlign = TextAlign.Left,
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
                .align(alignment = Alignment.Start),
            value = passwordTextState.value,
            onValueChange = {
                passwordTextState.value = it
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = MaterialTheme.colors.titleTextColor,
                cursorColor = MaterialTheme.colors.textColor,
                textColor = MaterialTheme.colors.textColor,
            ),
            maxLines = 1
        )

        Text(
            modifier = Modifier
                .padding(top = 12.dp, start = 20.dp)
                .align(alignment = Alignment.Start),
            text = stringResource(R.string.forgot_password_title),
            fontFamily = raleway,
            color = MaterialTheme.colors.titleTextColor,
            fontSize = 16.sp,
            textAlign = TextAlign.Left,
        )

        Button(
            modifier = Modifier.padding(top = 32.dp),
            onClick = {  },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.greyPrimaryVariant
            )
        ) {
            Text(
                text = stringResource(R.string.sign_in_content),
                fontFamily = raleway,
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
            )
        }

        Text(
            modifier = Modifier
                .padding(top = 24.dp),
            text = stringResource(R.string.or),
            fontFamily = raleway,
            color = MaterialTheme.colors.textColor,
            fontSize = 16.sp,
            textAlign = TextAlign.Left,
        )
        
        Row(
            modifier = Modifier.padding(top = 32.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {  },
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent
                ),
                elevation = ButtonDefaults.elevation(0.dp)
            ) {
                Image(
                    modifier = Modifier
                        .height(40.dp)
                        .width(40.dp),
                    painter = painterResource(id = R.drawable.fb),
                    contentDescription = "Facebook"
                )
            }
            Button(
                onClick = {  },
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent
                ),
                elevation = ButtonDefaults.elevation(0.dp)
            ) {
                Image(
                    modifier = Modifier
                        .height(40.dp)
                        .width(40.dp),
                    painter = painterResource(id = R.drawable.gg),
                    contentDescription = "Google"
                )
            }

        }


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
                    append("Not signed up yet?")
                }
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.titleTextColor,
                        fontSize = 14.sp,
                        fontFamily = raleway,
                    )
                ) {
                    append(" Sign up here")
                }
            }
        )
    }
}