package com.khue.joliecafejp.navigation.nav_screen


sealed class AuthScreen(
    val title: String,
    val route: String,
) {
    object Login: AuthScreen(
        route = "login",
        title = "Login",
    )
    object SignUp: AuthScreen(
        route = "signup",
        title = "Sign Up",
    )
    object ForgotPassword: AuthScreen(
        title = "Forgot Password",
        route = "forgotPassword"
    )
}
