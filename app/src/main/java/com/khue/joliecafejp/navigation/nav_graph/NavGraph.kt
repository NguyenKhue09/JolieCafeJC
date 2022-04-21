package com.khue.joliecafejp.navigation.nav_graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.khue.joliecafejp.navigation.nav_screen.ProfileSubScreen
import com.khue.joliecafejp.presentation.screens.detail.DetailScreen
import com.khue.joliecafejp.presentation.screens.login.LoginViewModel
import com.khue.joliecafejp.presentation.screens.profile.sub_screens.ProfileDetail

const val BOTTOM_ROUTE = "bottom"
const val AUTHENTICATION_ROUTE = "authentication"
const val PROFILE_SUB_ROUTE = "profile_sub_route"
const val HOME_SUB_ROUTE = "home_sub_route"
const val ROOT_GRAPH_ROUTE = "root"
const val NONE_ROUTE = "none"

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = BOTTOM_ROUTE,
        route = ROOT_GRAPH_ROUTE
    ) {
        bottomNavGraph(
            navController = navController,
            loginViewModel = loginViewModel,
            paddingValues = paddingValues
        )
        authNavGraph(
            navController = navController,
            loginViewModel = loginViewModel
        )

        composable(
            route = "detail"
        ) {
            DetailScreen(navController = navController)
        }
    }
}