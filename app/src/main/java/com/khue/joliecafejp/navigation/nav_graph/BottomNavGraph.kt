package com.khue.joliecafejp.navigation.nav_graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.khue.joliecafejp.navigation.nav_screen.BottomBarScreen
import com.khue.joliecafejp.presentation.screens.cart.CartScreen
import com.khue.joliecafejp.presentation.screens.favorite.FavoriteScreen
import com.khue.joliecafejp.presentation.screens.home.HomeScreen
import com.khue.joliecafejp.presentation.viewmodels.UserSharedViewModel
import com.khue.joliecafejp.presentation.screens.profile.ProfileScreen



fun NavGraphBuilder.bottomNavGraph(
    navController: NavHostController,
    userSharedViewModel: UserSharedViewModel,
    paddingValues: PaddingValues
) {
    navigation(
        startDestination = "home-root",
        route = BOTTOM_ROUTE
    ) {
        navigation(
            startDestination = BottomBarScreen.Home.route,
            route = "home-root"
        ) {
            composable(route = BottomBarScreen.Home.route) {
                HomeScreen(
                    navController = navController,
                    userSharedViewModel = userSharedViewModel,
                    paddingValues = paddingValues
                )
            }
            homeSubNavGraph(navController = navController)
        }

        composable(route = BottomBarScreen.Favorite.route) {
            FavoriteScreen(
                paddingValues = paddingValues,
                navController = navController
            )
        }
        composable(route = BottomBarScreen.Cart.route) {
            CartScreen( userSharedViewModel = userSharedViewModel)
        }

        navigation(
            startDestination = BottomBarScreen.Profile.route,
            route = "profile-root"
        ) {
            composable(route = BottomBarScreen.Profile.route) {
                ProfileScreen(
                    navController = navController,
                    userSharedViewModel = userSharedViewModel
                )
            }
            profileNavGraph(
                navController = navController,
                userSharedViewModel = userSharedViewModel
            )
        }
    }
}