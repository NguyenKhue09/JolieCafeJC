package com.khue.joliecafejp.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.khue.joliecafejp.navigation.nav_screen.AuthScreen
import com.khue.joliecafejp.presentation.screens.login.LoginScreen
import com.khue.joliecafejp.presentation.screens.sign_up.SignUpScreen


fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = AuthScreen.Login.route,
        route = AUTHENTICATION_ROUTE
    ){
        composable(
            route = AuthScreen.Login.route
        ) {
            LoginScreen(navController = navController)
        }
        composable(
            route = AuthScreen.SignUp.route
        ) {
            SignUpScreen(navController = navController)
        }
    }
}