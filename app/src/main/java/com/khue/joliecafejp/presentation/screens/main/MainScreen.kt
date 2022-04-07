package com.khue.joliecafejp.presentation.screens.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.khue.joliecafejp.navigation.nav_screen.BottomBarScreen
import com.khue.joliecafejp.navigation.nav_graph.SetupNavGraph
import com.khue.joliecafejp.navigation.nav_screen.AuthScreen
import com.khue.joliecafejp.navigation.nav_screen.ProfileSubScreen
import com.khue.joliecafejp.presentation.screens.login.LoginViewModel
import com.khue.joliecafejp.ui.theme.*


@Composable
fun MainScreen(
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
                BottomBar(navController = navController)
        },
        backgroundColor = MaterialTheme.colors.greyPrimary
    ) {
        SetupNavGraph(
            navController = navController,
            loginViewModel = loginViewModel,
            paddingValues = it
        )
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Favorite,
        BottomBarScreen.Cart,
        BottomBarScreen.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    var bottomNavVisible by remember {
        mutableStateOf(true)
    }

    bottomNavVisible = when(navBackStackEntry?.destination?.route) {
        AuthScreen.Login.route -> false
        AuthScreen.SignUp.route -> false
        ProfileSubScreen.ProfileDetail.route -> false
        ProfileSubScreen.AddressBook.route -> false
        ProfileSubScreen.OrderHistory.route -> false
        ProfileSubScreen.Settings.route -> false
        else -> true
    }

    AnimatedVisibility(
        visible = bottomNavVisible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        BottomNavigation(
            backgroundColor = MaterialTheme.colors.greyOpacity60Primary,
            modifier = Modifier
                .graphicsLayer {
                    shape = RoundedCornerShape(
                        topStart = bottomNavCornerRadius,
                        topEnd = bottomNavCornerRadius
                    )
                    clip = true
                }
                .height(58.dp),
            elevation = 0.dp
        ) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = { Text(text = screen.title) },
        icon = {
            Icon(
                painter = painterResource(id = screen.iconId),
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        selectedContentColor = MaterialTheme.colors.greyPrimaryVariant,
        unselectedContentColor = MaterialTheme.colors.textColor,
        alwaysShowLabel = false
    )
}
