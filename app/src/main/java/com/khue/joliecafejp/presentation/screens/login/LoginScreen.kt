package com.khue.joliecafejp.presentation.screens.login

import android.app.Activity
import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.facebook.CallbackManager
import com.facebook.FacebookSdk
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.khue.joliecafejp.R
import com.khue.joliecafejp.firebase.firebase_authentication.face_book_signin.FirebaseFacebookLogin
import com.khue.joliecafejp.firebase.firebase_authentication.gmail_password_authentication.FirebaseGmailPasswordAuth
import com.khue.joliecafejp.firebase.firebase_authentication.google_signin.AuthResultContract
import com.khue.joliecafejp.navigation.nav_graph.AUTHENTICATION_ROUTE
import com.khue.joliecafejp.navigation.nav_graph.BOTTOM_ROUTE
import com.khue.joliecafejp.navigation.nav_screen.AuthScreen
import com.khue.joliecafejp.presentation.common.FaceOrGoogleLogin
import com.khue.joliecafejp.presentation.common.TextCustom
import com.khue.joliecafejp.presentation.common.TextFieldCustom
import com.khue.joliecafejp.ui.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel
) {

    val user by loginViewModel.user.collectAsState()

    LaunchedEffect(user) {
        println("Login $user")
        if (user != null) {
            navController.navigate(BOTTOM_ROUTE) {
                popUpTo(AuthScreen.Login.route) {
                    inclusive = true
                }
            }
        }
    }

    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    var text by remember {
        mutableStateOf<String?>(null)
    }

    // setup facebook login
    FacebookSdk.setAutoLogAppEventsEnabled(true)
    FacebookSdk.setAdvertiserIDCollectionEnabled(true)
    val facebookLogin = FirebaseFacebookLogin()
    val callbackManager: CallbackManager = CallbackManager.Factory.create()


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
                    mAuth.signInWithCredential(credentials).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Welcome back ${account.displayName}", Toast.LENGTH_LONG).show()
                            loginViewModel.signIn(
                                email = account.email!!,
                                displayName = account.displayName!!
                            )
                        } else {
                            Toast.makeText(context, "Google sign in failed", Toast.LENGTH_LONG).show()
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
    var userNameError by remember {
        mutableStateOf("")
    }
    var passwordError by remember {
        mutableStateOf("")
    }

    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(MaterialTheme.colors.greyPrimary),
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
            trailingIcon = {
                if (userNameError.isNotEmpty()) Icon(
                    Icons.Filled.Error,
                    stringResource(R.string.error),
                    tint = MaterialTheme.colors.error
                )
            },
            placeHolder = stringResource(R.string.username_placeholder),
            visualTransformation = VisualTransformation.None,
            error = userNameError,
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
                if (passwordError.isEmpty()) {
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
                } else {
                    Icon(
                        Icons.Filled.Error,
                        stringResource(R.string.error),
                        tint = MaterialTheme.colors.error
                    )
                }

            },
            placeHolder = stringResource(R.string.password_placeholder),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            error = passwordError
        )

        Text(
            modifier = Modifier
                .padding(top = 12.dp, start = 20.dp)
                .align(alignment = Alignment.Start)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    navController.navigate(AuthScreen.ForgotPassword.route) {
                        launchSingleTop = true
                    }
                },
            text = stringResource(R.string.forgot_password),
            fontFamily = raleway,
            color = MaterialTheme.colors.titleTextColor,
            fontSize = 16.sp,
            textAlign = TextAlign.Left,
        )


        Button(
            modifier = Modifier.padding(top = 32.dp),
            onClick = {
                validateUserName(userNameTextState.value.text.trim()) {
                    userNameError = it
                }
                validatePassword(passwordTextState.value.text.trim()) {
                    passwordError = it
                }

                if (userNameError.isEmpty() && passwordError.isEmpty()) {
                    FirebaseGmailPasswordAuth().loginUser(
                        email = userNameTextState.value.text.trim(),
                        password = passwordTextState.value.text.trim(),
                        context = context,
                        navController = navController
                    )
                }
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.greyPrimaryVariant
            )
        ) {
            Text(
                text = stringResource(R.string.sign_in_content),
                fontFamily = raleway,
                color = Color.White,
                fontWeight = FontWeight.Bold,
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


        FaceOrGoogleLogin(
            googleAction = {
                authResultLauncher.launch(signInRequestCode)
            },
            faceAction = {
                facebookLogin.facebookLogin(context, callbackManager, mAuth, loginViewModel)
            }
        )

        Text(
            modifier = Modifier
                .padding(bottom = 20.dp, top = 32.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    navController.navigate(AuthScreen.SignUp.route)
                },
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.textColor,
                        fontSize = 14.sp,
                        fontFamily = raleway,
                    )
                ) {
                    append(stringResource(R.string.not_signed_up_yet))
                }
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.titleTextColor,
                        fontSize = 14.sp,
                        fontFamily = raleway,
                    )
                ) {
                    append(stringResource(R.string.sign_up_here))
                }
            }
        )
    }

    HandleBackPress(context =  context)
}

fun validateUserName(userName: String, onError: (String) -> Unit) {
    when {
        userName.trim().isEmpty() -> onError("Username is empty")
        !Patterns.EMAIL_ADDRESS.matcher(userName).matches() -> onError("Your username wrong format")
        else -> onError("")
    }
}

fun validatePassword(password: String, onError: (String) -> Unit) {
    when {
        password.trim().isEmpty() -> onError("Password is empty")
        password.length < 6 -> onError("Password less then 6 character")
        else -> onError("")
    }
}

@Composable
fun HandleBackPress(context: Context) {
    val activity = (context as? Activity)
    BackHandler {
        activity?.finish()
    }
}

