package com.khue.joliecafejp.navigation.nav_graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.khue.joliecafejp.navigation.nav_screen.BottomBarScreen
import com.khue.joliecafejp.presentation.screens.cart.CardScreen
import com.khue.joliecafejp.presentation.screens.favorite.FavoriteScreen
import com.khue.joliecafejp.presentation.screens.home.HomeScreen
import com.khue.joliecafejp.presentation.screens.login.LoginViewModel
import com.khue.joliecafejp.presentation.screens.profile.ProfileScreen



fun NavGraphBuilder.bottomNavGraph(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    paddingValues: PaddingValues
) {
    navigation(
        startDestination = BottomBarScreen.Home.route,
        route = BOTTOM_ROUTE
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(navController = navController, loginViewModel)
        }
        composable(route = BottomBarScreen.Favorite.route) {
            FavoriteScreen(
                paddingValues = paddingValues
            )
        }
        composable(route = BottomBarScreen.Cart.route) {
            CardScreen()
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen(
                navController = navController,
                loginViewModel = loginViewModel
            )
        }
    }
}