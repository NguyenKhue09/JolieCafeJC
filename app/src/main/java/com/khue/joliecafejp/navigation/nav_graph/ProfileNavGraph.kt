package com.khue.joliecafejp.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.khue.joliecafejp.navigation.nav_screen.ProfileSubScreen
import com.khue.joliecafejp.presentation.viewmodels.UserSharedViewModel
import com.khue.joliecafejp.presentation.screens.profile.sub_screens.AddressBook
import com.khue.joliecafejp.presentation.screens.profile.sub_screens.OrderHistory
import com.khue.joliecafejp.presentation.screens.profile.sub_screens.ProfileDetail
import com.khue.joliecafejp.presentation.screens.profile.sub_screens.Settings

fun NavGraphBuilder.profileNavGraph(
    navController: NavHostController,
    userSharedViewModel: UserSharedViewModel,
) {
    navigation(
        startDestination = ProfileSubScreen.ProfileDetail.route,
        route = PROFILE_SUB_ROUTE
    ){
        composable(
            route = ProfileSubScreen.ProfileDetail.route
        ) {
            ProfileDetail(navController = navController, userSharedViewModel = userSharedViewModel)
        }
        composable(
            route = ProfileSubScreen.AddressBook.route
        ) {
            AddressBook(navController = navController, userSharedViewModel = userSharedViewModel)
        }
        composable(
            route = ProfileSubScreen.OrderHistory.route
        ) {
            OrderHistory(navController = navController)
        }
        composable(
            route = ProfileSubScreen.Settings.route
        ) {
            Settings(
                navHostController = navController
            )
        }
    }
}