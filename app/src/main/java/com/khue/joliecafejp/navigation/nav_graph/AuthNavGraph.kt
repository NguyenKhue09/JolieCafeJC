package com.khue.joliecafejp.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.khue.joliecafejp.navigation.nav_screen.AuthScreen
import com.khue.joliecafejp.presentation.screens.forgot_password.ForgotPassword
import com.khue.joliecafejp.presentation.screens.login.LoginScreen
import com.khue.joliecafejp.presentation.screens.login.LoginViewModel
import com.khue.joliecafejp.presentation.screens.sign_up.SignUpScreen
import kotlin.math.log


fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
) {
    navigation(
        startDestination = AuthScreen.Login.route,
        route = AUTHENTICATION_ROUTE
    ){
        composable(
            route = AuthScreen.Login.route
        ) {
            LoginScreen(navController = navController, loginViewModel)
        }
        composable(
            route = AuthScreen.SignUp.route
        ) {
            SignUpScreen(navController = navController)
        }
        composable(
            route = AuthScreen.ForgotPassword.route
        ) {
            ForgotPassword(navController = navController)
        }
    }
}