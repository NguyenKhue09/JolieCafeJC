package com.khue.joliecafejp.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.firebase.auth.FirebaseAuth
import com.khue.joliecafejp.navigation.nav_graph.AUTHENTICATION_ROUTE
import com.khue.joliecafejp.navigation.nav_screen.AuthScreen
import com.khue.joliecafejp.navigation.nav_screen.BottomBarScreen
import com.khue.joliecafejp.presentation.screens.login.LoginViewModel
import com.khue.joliecafejp.ui.theme.greyPrimary
import com.khue.joliecafejp.viewmodels.HomeViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val user by loginViewModel.user.collectAsState()

    LaunchedEffect(user){
        println("Home $user")
        if (user == null) {
            navController.navigate(AUTHENTICATION_ROUTE) {
                popUpTo(BottomBarScreen.Home.route) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val pages = listOf(
        "All",
        "Coffee",
        "Tea",
        "Juice",
        "Pasty"
    )


    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {


        ScrollableTabRow(
            // Our selected tab is our current page
            selectedTabIndex = pagerState.currentPage,
            // Override the indicator, using the provided pagerTabIndicatorOffset modifier
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            }
        ) {
            // Add tabs for all of our pages
            pages.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.scrollToPage(index)
                        }
                    },
                )
            }
        }

        HorizontalPager(
            count = pages.size,
            state = pagerState,
        ) { page ->
            Text(text = pages[page])
        }

    }

}