package com.khue.joliecafejp.navigation.nav_screen

import com.khue.joliecafejp.R

sealed class HomeSubScreen(
    val titleId: Int,
    val route: String
) {
    object Notifications: HomeSubScreen(
        titleId = R.string.notifications,
        route = "notifications"
    )
    object Categories: HomeSubScreen(
        titleId = R.string.categories,
        route = "categories/{category}?search={search}"
    ) {
        fun passCategory(category: String, search: String?): String {
            if(search.isNullOrEmpty()) {
                return "categories/$category"
            }
            return "categories/$category?search=$search"
        }
    }
}
