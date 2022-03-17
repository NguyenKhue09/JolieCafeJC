package com.khue.joliecafejp.presentation.screens.login

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.khue.joliecafejp.R
import com.khue.joliecafejp.firebase.firebase_authentication.google_signin.AuthResultContract
import com.khue.joliecafejp.navigation.nav_graph.AUTHENTICATION_ROUTE
import com.khue.joliecafejp.presentation.common.GoogleSignInButton
import com.khue.joliecafejp.presentation.screens.main.MainScreen
import com.khue.joliecafejp.ui.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var text by remember {
        mutableStateOf<String?>(null)
    }


    val signInRequestCode = 1
    val authResultLauncher = 
        rememberLauncherForActivityResult(contract = AuthResultContract()) { task ->

            try {
                val account = task?.getResult(ApiException::class.java)
                if (account == null) {
                    text = "Google sign in failed"
                    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
                } else {
                    val credentials = GoogleAuthProvider.getCredential(account.idToken, null)

                    coroutineScope.launch {
                        try {
                            mAuth.signInWithCredential(credentials).await()
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, "Successfully", Toast.LENGTH_LONG).show()
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                            }
                        }
                        loginViewModel.signIn(email = account.email!!, displayName = account.displayName!!)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, account.displayName, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            } catch (e: ApiException) {
                text = "Google sign in failed"
                Toast.makeText(context, text, Toast.LENGTH_LONG).show()
                println(e.message)
            }
        }

    // https://stackoverflow.com/questions/69107068/facebook-login-with-jetpack-compose

    val userNameTextState = remember { mutableStateOf(TextFieldValue("")) }
    val passwordTextState = remember { mutableStateOf(TextFieldValue("")) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.greyPrimary),
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
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
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
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            )
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
            onClick = { },
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

            GoogleSignInButton {
                authResultLauncher.launch(signInRequestCode)
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

