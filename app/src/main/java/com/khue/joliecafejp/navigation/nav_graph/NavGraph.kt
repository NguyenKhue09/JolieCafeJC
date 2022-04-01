package com.khue.joliecafejp.navigation.nav_graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.khue.joliecafejp.presentation.screens.login.LoginViewModel

const val BOTTOM_ROUTE = "bottom"
const val AUTHENTICATION_ROUTE = "authentication"
const val PROFILE_SUB_ROUTE = "profile_sub_route"
const val ROOT_GRAPH_ROUTE = "root"
const val NONE_ROUTE = "none"

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    loginViewModel: LoginViewModel
) {
    NavHost(
        navController = navController,
        startDestination = BOTTOM_ROUTE,
        route = ROOT_GRAPH_ROUTE
    ) {
        bottomNavGraph(navController = navController, loginViewModel)
        authNavGraph(navController = navController, loginViewModel)
    }
}