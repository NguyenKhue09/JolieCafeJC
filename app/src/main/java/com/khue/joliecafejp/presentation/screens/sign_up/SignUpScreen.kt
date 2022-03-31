package com.khue.joliecafejp.presentation.screens.sign_up

import android.util.Patterns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.facebook.CallbackManager
import com.facebook.FacebookSdk.setAdvertiserIDCollectionEnabled
import com.facebook.FacebookSdk.setAutoLogAppEventsEnabled
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.khue.joliecafejp.R
import com.khue.joliecafejp.firebase.firebase_authentication.face_book_signin.FirebaseFacebookLogin
import com.khue.joliecafejp.firebase.firebase_authentication.gmail_password_authentication.FirebaseGmailPasswordAuth
import com.khue.joliecafejp.firebase.firebase_authentication.google_signin.AuthResultContract
import com.khue.joliecafejp.presentation.common.FaceOrGoogleLogin
import com.khue.joliecafejp.presentation.common.TextCustom
import com.khue.joliecafejp.presentation.common.TextFieldCustom
import com.khue.joliecafejp.presentation.screens.login.LoginViewModel
import com.khue.joliecafejp.ui.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@Composable
fun SignUpScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {

    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val userNameTextState = remember { mutableStateOf(TextFieldValue("")) }
    val passwordTextState = remember { mutableStateOf(TextFieldValue("")) }
    val emailTextState = remember { mutableStateOf(TextFieldValue("")) }
    val confirmPasswordTextState = remember { mutableStateOf(TextFieldValue("")) }

    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var confirmPasswordVisible by rememberSaveable { mutableStateOf(false) }

    // setup facebook login
    setAutoLogAppEventsEnabled(true)
    setAdvertiserIDCollectionEnabled(true)
    val facebookLogin = FirebaseFacebookLogin()
    val callbackManager: CallbackManager = CallbackManager.Factory.create()
    val auth: FirebaseAuth = Firebase.auth

    // setup google login
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
                            auth.signInWithCredential(credentials).await()
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, "Successfully", Toast.LENGTH_LONG).show()
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                            }
                        }
                        loginViewModel.signIn(
                            email = account.email!!,
                            displayName = account.displayName!!
                        )
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

    var userNameError by remember {
        mutableStateOf("")
    }
    var emailError by remember {
        mutableStateOf("")
    }
    var passwordError by remember {
        mutableStateOf("")
    }
    var confirmPasswordError by remember {
        mutableStateOf("")
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
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
            trailingIcon = {
                if (userNameError.isNotEmpty()) {
                    Icon(
                        Icons.Filled.Error,
                        stringResource(R.string.error),
                        tint = MaterialTheme.colors.error
                    )
                }
            },
            placeHolder = stringResource(R.string.username_placeholder),
            visualTransformation = VisualTransformation.None,
            error = userNameError
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
                if (confirmPasswordError.isEmpty()) {
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
                } else {
                    Icon(
                        Icons.Filled.Error,
                        stringResource(R.string.error),
                        tint = MaterialTheme.colors.error
                    )
                }
            },
            placeHolder = stringResource(R.string.confirm_password_placeholder),
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            error = confirmPasswordError
        )

        Button(
            modifier = Modifier.padding(top = 32.dp),
            onClick = {
                validateUserName(username = userNameTextState.value.text.trim()) {
                    userNameError = it
                }
                validateEmail(email = emailTextState.value.text.trim()) {
                    emailError = it
                }
                validatePassword(password = passwordTextState.value.text.trim()) {
                    passwordError = it
                }
                validateConfirmPassword(
                    password = passwordTextState.value.text.trim(),
                    confirmPassword = confirmPasswordTextState.value.text.trim()
                ) {
                    confirmPasswordError = it
                }
                if (userNameError.isEmpty() && emailError.isEmpty()
                    && passwordError.isEmpty() && confirmPasswordError.isEmpty()
                ) {
                    FirebaseGmailPasswordAuth().registerUser(
                        email = emailTextState.value.text.trim(),
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
                text = stringResource(R.string.sign_up_content),
                fontFamily = raleway,
                color = Color.White,
                fontWeight = FontWeight.Bold,
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

        FaceOrGoogleLogin(
            googleAction = {
                authResultLauncher.launch(signInRequestCode)
            },
            faceAction = {
                facebookLogin.facebookLogin(context, callbackManager, auth, loginViewModel)
            }
        )

        Text(
            modifier = Modifier
                .padding(bottom = 20.dp, top = 32.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    navController.popBackStack()
                },
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

fun validateUserName(username: String, onError: (String) -> Unit) {
    when {
        username.trim().isEmpty() -> onError("Username is empty")
        else -> onError("")
    }
}

fun validateEmail(email: String, onError: (String) -> Unit) {
    when {
        email.trim().isEmpty() -> onError("Email is empty")
        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> onError("Your email wrong format")
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

fun validateConfirmPassword(password: String, confirmPassword: String, onError: (String) -> Unit) {
    when {
        confirmPassword.trim().isEmpty() -> onError("Confirm password is empty")
        confirmPassword.length < 6 -> onError("Confirm password less then 6 character")
        password != confirmPassword -> onError("Your confirm password not match your password")
        else -> onError("")
    }
}


