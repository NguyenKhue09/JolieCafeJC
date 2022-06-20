package com.khue.joliecafejp.navigation.nav_graph

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.khue.joliecafejp.navigation.nav_screen.HomeSubScreen
import com.khue.joliecafejp.presentation.screens.home.sub_screens.CategoriesScreen
import com.khue.joliecafejp.presentation.screens.home.sub_screens.NotificationsScreen
import com.khue.joliecafejp.utils.Constants.Companion.CATEGORY
import com.khue.joliecafejp.utils.Constants.Companion.SEARCH


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
            route = HomeSubScreen.Categories.route,
            arguments = listOf(
                navArgument(CATEGORY) {
                    type = NavType.StringType
                },
                navArgument(SEARCH) {
                    nullable = true
                    defaultValue = null
                }
            )
        ) { navBackStackEntry ->

            val action = navBackStackEntry.arguments?.getString(CATEGORY) ?: "All"

            println(action)

            CategoriesScreen(
                navController = navController
            )
        }
    }
}