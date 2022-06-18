package com.khue.joliecafejp.presentation.screens.sign_up

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
import com.khue.joliecafejp.navigation.nav_graph.BOTTOM_ROUTE
import com.khue.joliecafejp.navigation.nav_screen.AuthScreen
import com.khue.joliecafejp.presentation.common.FaceOrGoogleLogin
import com.khue.joliecafejp.presentation.common.TextCustom
import com.khue.joliecafejp.presentation.common.TextFieldCustom
import com.khue.joliecafejp.presentation.viewmodels.UserSharedViewModel
import com.khue.joliecafejp.presentation.viewmodels.SignUpViewModel
import com.khue.joliecafejp.ui.theme.*
import com.khue.joliecafejp.utils.ApiResult
import com.khue.joliecafejp.utils.RegistrationFormEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignUpScreen(
    navController: NavHostController,
    userSharedViewModel: UserSharedViewModel = hiltViewModel(),
    signUpViewModel: SignUpViewModel = hiltViewModel()
) {

    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val state = signUpViewModel.state.collectAsState()
    val userToken by userSharedViewModel.userToken.collectAsState(initial = "")

    val scrollState = rememberScrollState()
    val context = LocalContext.current

    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var confirmPasswordVisible by rememberSaveable { mutableStateOf(false) }

    // setup facebook login
    setAutoLogAppEventsEnabled(true)
    setAdvertiserIDCollectionEnabled(true)
    val facebookLogin = FirebaseFacebookLogin()
    val callbackManager: CallbackManager = CallbackManager.Factory.create()
    val auth: FirebaseAuth = Firebase.auth

    // setup google login
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
                    mAuth.signInWithCredential(credentials).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                context,
                                "Welcome back ${account.displayName}",
                                Toast.LENGTH_LONG
                            ).show()
                            val user = task.result.user
                            val isNewUser = task.result.additionalUserInfo?.isNewUser
                            user?.let {
                                val useName = user.displayName
                                val email = user.email
                                if(!useName.isNullOrEmpty() && !email.isNullOrEmpty()) {
                                    val data = mapOf(
                                        "_id" to user.uid,
                                        "fullname" to useName,
                                        "email" to email
                                    )
                                    isNewUser?.let {
                                        if (it) {
                                            userSharedViewModel.createUser(userData = data)
                                        } else {
                                            userSharedViewModel.userLogin(userId = user.uid)

                                        }
                                    }
                                }

                            }

                        } else {
                            Toast.makeText(context, "Google sign in failed", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            } catch (e: ApiException) {
                text = "Google sign in failed"
                Toast.makeText(context, text, Toast.LENGTH_LONG).show()
                println(e.message)
            }
        }

    LaunchedEffect(key1 = context) {
        signUpViewModel.validationEvents.collect { event ->
            when (event) {
                is SignUpViewModel.ValidationEvent.Success -> {
                    FirebaseGmailPasswordAuth().registerUser(
                        email = state.value.email,
                        password = state.value.password,
                        name = state.value.userName,
                        context = context,
                    ) {
                        userSharedViewModel.createUser(userData = it)
                    }
                }
            }
        }
    }

    LaunchedEffect(userToken) {
        if (userToken.isNotEmpty()) {
            navController.navigate(BOTTOM_ROUTE) {
                popUpTo(AuthScreen.Login.route) {
                    inclusive = true
                }
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        userSharedViewModel.userLoginResponse.collectLatest { response ->
            when(response) {
                is ApiResult.Error -> {
                    Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                }
                is ApiResult.Success -> {
                    Toast.makeText(context, "Get your info success", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            userSharedViewModel.cleanUserLoginResponse()
        }
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
            fontFamily = ralewayMedium,
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
            textFieldValue = state.value.userName,
            onTextChange = {
                signUpViewModel.onEvent(RegistrationFormEvent.UserNameChanged(it))
            },
            keyBoardType = KeyboardType.Text,
            trailingIcon = {
                if (state.value.userNameError.isNotEmpty()) {
                    Icon(
                        Icons.Filled.Error,
                        stringResource(R.string.error),
                        tint = MaterialTheme.colors.error
                    )
                }
            },
            placeHolder = stringResource(R.string.username_placeholder),
            visualTransformation = VisualTransformation.None,
            error = state.value.userNameError
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
            textFieldValue = state.value.email,
            onTextChange = {
                signUpViewModel.onEvent(RegistrationFormEvent.EmailChanged(it))
            },
            keyBoardType = KeyboardType.Email,
            trailingIcon = {
                if (state.value.emailError.isNotEmpty()) {
                    Icon(
                        Icons.Filled.Error,
                        stringResource(R.string.error),
                        tint = MaterialTheme.colors.error
                    )
                }
            },
            placeHolder = stringResource(R.string.email_placeholder),
            visualTransformation = VisualTransformation.None,
            error = state.value.emailError
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
            textFieldValue = state.value.password,
            onTextChange = {
                signUpViewModel.onEvent(RegistrationFormEvent.PasswordChanged(it))
            },
            keyBoardType = KeyboardType.Password,
            trailingIcon = {
                if (state.value.passwordError.isEmpty()) {
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
            error = state.value.passwordError
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
            textFieldValue = state.value.confirmPassword,
            onTextChange = {
                signUpViewModel.onEvent(RegistrationFormEvent.ConfirmPasswordChanged(it))
            },
            keyBoardType = KeyboardType.Password,
            trailingIcon = {
                if (state.value.confirmPasswordError.isEmpty()) {
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
            error = state.value.confirmPasswordError
        )

        Button(
            modifier = Modifier.padding(top = 32.dp),
            onClick = {
                signUpViewModel.onEvent(RegistrationFormEvent.Submit)
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.greyPrimaryVariant
            )
        ) {
            Text(
                text = stringResource(R.string.sign_up_content),
                fontFamily = ralewayMedium,
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
                facebookLogin.facebookLogin(context, callbackManager, auth) {
                    userSharedViewModel.userLogin(userId = it)
                }
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
                        fontFamily = ralewayMedium,
                    )
                ) {
                    append(stringResource(R.string.already_signed_up))
                }
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.titleTextColor,
                        fontSize = 14.sp,
                        fontFamily = ralewayMedium,
                    )
                ) {
                    append(stringResource(R.string.sign_in_here))
                }
            }
        )
    }
}


