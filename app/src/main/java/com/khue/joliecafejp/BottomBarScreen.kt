package com.khue.joliecafejp



import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val iconId: Int
) {
    object Home: BottomBarScreen(
        route = "home",
        title = "Home",
        iconId = R.drawable.ic_home
    )
    object Favorite: BottomBarScreen(
        route = "favorite",
        title = "Favorite",
        iconId = R.drawable.ic_favorite
    )
    object Cart: BottomBarScreen(
        route = "cart",
        title = "Cart",
        iconId = R.drawable.ic_cart
    )
    object Profile: BottomBarScreen(
        route = "profile",
        title = "Profile",
        iconId = R.drawable.ic_profile
    )
}

