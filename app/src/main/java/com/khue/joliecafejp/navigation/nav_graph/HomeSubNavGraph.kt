package com.khue.joliecafejp.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.khue.joliecafejp.navigation.nav_screen.HomeSubScreen
import com.khue.joliecafejp.presentation.screens.home.sub_screens.CategoriesScreen
import com.khue.joliecafejp.presentation.screens.home.sub_screens.NotificationsScreen


fun NavGraphBuilder.homeSubNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = HomeSubScreen.Notifications.route,
        route = HOME_SUB_ROUTE
    ) {
        composable(
            route = HomeSubScreen.Notifications.route
        ) {
            NotificationsScreen(
                navController = navController
            )
        }
        composable(
            route = HomeSubScreen.Categories.route
        ) {
            CategoriesScreen()
        }
    }
}