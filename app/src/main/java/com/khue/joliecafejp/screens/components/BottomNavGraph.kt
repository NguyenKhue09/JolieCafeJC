package com.khue.joliecafejp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.khue.joliecafejp.screens.CartScreen
import com.khue.joliecafejp.screens.FavoriteScreen
import com.khue.joliecafejp.screens.HomeScreen
import com.khue.joliecafejp.screens.ProfileScreen
import com.khue.joliecafejp.screens.components.BottomBarScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen()
        }
        composable(route = BottomBarScreen.Favorite.route) {
            FavoriteScreen()
        }
        composable(route = BottomBarScreen.Cart.route) {
            CartScreen()
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen()
        }

    }
}