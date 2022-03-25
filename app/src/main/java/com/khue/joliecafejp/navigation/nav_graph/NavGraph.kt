package com.khue.joliecafejp.navigation.nav_graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

const val BOTTOM_ROUTE = "bottom"
const val AUTHENTICATION_ROUTE = "authentication"
const val ROOT_GRAPH_ROUTE = "root"

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = BOTTOM_ROUTE,
        route = ROOT_GRAPH_ROUTE
    ) {
        bottomNavGraph(navController = navController)
        authNavGraph(navController = navController)
    }
}