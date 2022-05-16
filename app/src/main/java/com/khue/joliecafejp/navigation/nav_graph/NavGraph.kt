package com.khue.joliecafejp.navigation.nav_graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.khue.joliecafejp.presentation.screens.detail.DetailScreen
import com.khue.joliecafejp.presentation.viewmodels.UserSharedViewModel
import com.khue.joliecafejp.utils.Constants.Companion.IS_FAV
import com.khue.joliecafejp.utils.Constants.Companion.PRODUCT_ID

const val BOTTOM_ROUTE = "bottom"
const val AUTHENTICATION_ROUTE = "authentication"
const val PROFILE_SUB_ROUTE = "profile_sub_route"
const val HOME_SUB_ROUTE = "home_sub_route"
const val ROOT_GRAPH_ROUTE = "root"
const val NONE_ROUTE = "none"

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    userSharedViewModel: UserSharedViewModel,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = BOTTOM_ROUTE,
        route = ROOT_GRAPH_ROUTE
    ) {
        bottomNavGraph(
            navController = navController,
            userSharedViewModel = userSharedViewModel,
            paddingValues = paddingValues
        )
        authNavGraph(
            navController = navController,
            userSharedViewModel = userSharedViewModel
        )

        composable(
            route = "detail/{productId}?isFav={isFav}",
            arguments = listOf(
                navArgument(PRODUCT_ID) { type = NavType.StringType },
                navArgument(IS_FAV) { defaultValue = false }
            )
        ) { backStackEntry ->
            DetailScreen(
                navController = navController,
                productId = backStackEntry.arguments?.getString(PRODUCT_ID)
            )
        }
    }
}