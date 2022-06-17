package com.khue.joliecafejp.navigation.nav_screen

import com.khue.joliecafejp.R
import com.khue.joliecafejp.domain.model.CartItem
import com.khue.joliecafejp.domain.model.TestItem
import com.khue.joliecafejp.navigation.nav_graph.NONE_ROUTE

sealed class CartSubScreen(
    val titleId: Int,
    val route: String
) {
    object Checkout : CartSubScreen(
        titleId = R.string.checkout,
        route = "checkout"
    )
}
