package com.khue.joliecafejp.navigation.nav_screen

import com.khue.joliecafejp.R
import com.khue.joliecafejp.navigation.nav_graph.NONE_ROUTE

sealed class ProfileSubScreen(
    val titleId: Int,
    val iconId: Int,
    val route: String
) {
    object ProfileDetail : ProfileSubScreen(
        titleId = R.string.profile,
        iconId = R.drawable.ic_profile,
        route = "profile_detail"
    )
    object AddressBook : ProfileSubScreen(
        titleId = R.string.address_book,
        iconId = R.drawable.ic_map,
        route = "address_book"
    )
    object OrderHistory : ProfileSubScreen(
        titleId = R.string.order_history,
        iconId = R.drawable.ic_order,
        route = "order_history"
    )
    object Settings : ProfileSubScreen(
        titleId = R.string.settings,
        iconId = R.drawable.ic_settings,
        route = "settings"
    )
    object SignOut : ProfileSubScreen(
        titleId = R.string.sign_out,
        iconId = R.drawable.ic_log_out,
        route = NONE_ROUTE
    )
}
