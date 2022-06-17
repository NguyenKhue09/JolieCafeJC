package com.khue.joliecafejp.navigation.nav_graph

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.khue.joliecafejp.domain.model.CartItem
import com.khue.joliecafejp.domain.model.TestItem
import com.khue.joliecafejp.navigation.nav_screen.CartSubScreen
import com.khue.joliecafejp.presentation.screens.cart.sub_screens.CheckoutScreen
import com.khue.joliecafejp.presentation.viewmodels.UserSharedViewModel
import com.khue.joliecafejp.utils.Constants
import com.khue.joliecafejp.utils.Constants.Companion.CARTS


fun NavGraphBuilder.cartSubNavGraph(
    navController: NavHostController,
    userSharedViewModel: UserSharedViewModel
) {
    navigation(
        startDestination = CartSubScreen.Checkout.route,
        route = CART_SUB_ROUTE
    ) {
        composable(
            route = CartSubScreen.Checkout.route
        ) {
            val result = navController.previousBackStackEntry?.savedStateHandle?.get<List<CartItem>>(CARTS)
            CheckoutScreen(
                navController = navController,
                userSharedViewModel = userSharedViewModel
            )
        }
    }
}