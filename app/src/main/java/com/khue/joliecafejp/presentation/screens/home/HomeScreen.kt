package com.khue.joliecafejp.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.*
import com.khue.joliecafejp.R
import com.khue.joliecafejp.domain.model.CategoryButtonItem
import com.khue.joliecafejp.navigation.nav_graph.AUTHENTICATION_ROUTE
import com.khue.joliecafejp.navigation.nav_screen.BottomBarScreen
import com.khue.joliecafejp.navigation.nav_screen.HomeSubScreen
import com.khue.joliecafejp.presentation.common.CategoriesButtonGroup
import com.khue.joliecafejp.presentation.common.ProductOptionsBottomSheet
import com.khue.joliecafejp.presentation.common.SearchBar
import com.khue.joliecafejp.presentation.common.TextCustom
import com.khue.joliecafejp.presentation.components.HomeTopBar
import com.khue.joliecafejp.presentation.components.HorizontalProductItem
import com.khue.joliecafejp.presentation.viewmodels.LoginViewModel
import com.khue.joliecafejp.presentation.viewmodels.HomeViewModel
import com.khue.joliecafejp.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue


@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    paddingValues: PaddingValues,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val user by loginViewModel.user.collectAsState()
    val searchTextState by homeViewModel.searchTextState
    val pagerState = rememberPagerState()

    LaunchedEffect(user) {
        println("user $user")
        if (user == null) {
            navController.navigate(AUTHENTICATION_ROUTE) {
                popUpTo(BottomBarScreen.Home.route) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }

    // Bottom Sheet
    val coroutineScope = rememberCoroutineScope()
    val state = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

    val categories = listOf(
        CategoryButtonItem(
            title = "All",
            iconId = R.drawable.ic_star
        ),
        CategoryButtonItem(
            title = "Coffee",
            iconId = R.drawable.ic_coffee
        ),
        CategoryButtonItem(
            title = "Tea",
            iconId = R.drawable.ic_leaf
        ),
        CategoryButtonItem(
            title = "Juice",
            iconId = R.drawable.ic_watermelon
        ),
        CategoryButtonItem(
            title = "Pastry",
            iconId = R.drawable.ic_croissant_svgrepo_com
        ),
        CategoryButtonItem(
            title = "Coffee",
            iconId = R.drawable.ic_coffee
        ),
        CategoryButtonItem(
            title = "Coffee",
            iconId = R.drawable.ic_coffee
        ),
        CategoryButtonItem(
            title = "More",
            iconId = R.drawable.ic_coffee
        ),
    )

    LaunchedEffect(key1 = true) {
        if (state.isVisible) {
            state.hide()
        }
    }

    ModalBottomSheetLayout(
        sheetState = state,
        modifier = Modifier.fillMaxSize(),
        sheetContent = {
            ProductOptionsBottomSheet(
                paddingValues = paddingValues,
                coroutineScope = coroutineScope,
                modalBottomSheetState = state
            )
        }
    ) {
        Scaffold(
            modifier = Modifier.padding(paddingValues),
            topBar = {
                HomeTopBar(
                    userName = user!!.displayName,
                    userCoins = 300
                ) {
                    navController.navigate(HomeSubScreen.Notifications.route) {
                        launchSingleTop = true
                    }
                }
            },
            backgroundColor = MaterialTheme.colors.greyPrimary
        ) { paddingValues ->
            val padding = paddingValues.calculateBottomPadding()


            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = EXTRA_LARGE_PADDING,
                        end = EXTRA_LARGE_PADDING,
                        bottom = padding
                    ),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                item {
                    SearchBar(
                        text = searchTextState,
                        onCloseClicked = {
                            homeViewModel.updateSearchTextState(newValue = "")
                        },
                        onSearchClicked = { searchString ->

                        },
                        onTextChange = { newValue ->
                            homeViewModel.updateSearchTextState(newValue = newValue)
                        }
                    )
                }
                item {
                    ImageSlider(pagerState = pagerState)
                }

                item {
                    TextCustom(
                        modifier = Modifier
                            .padding(vertical = EXTRA_LARGE_PADDING),
                        content = stringResource(R.string.categories),
                        color = MaterialTheme.colors.textColor2,
                        fontFamily = ralewayMedium
                    )
                }

                item {
                    CategoriesButtonGroup(
                        categories = categories,
                        selectedButton = null
                    ) { category ->
                        navController.navigate(HomeSubScreen.Categories.passCategory(category = category)) {
                            launchSingleTop = true
                        }
                    }
                }

                item {
                    TextCustom(
                        modifier = Modifier
                            .padding(vertical = EXTRA_LARGE_PADDING),
                        content = stringResource(R.string.best_sellers),
                        color = MaterialTheme.colors.textColor2,
                        fontFamily = ralewayMedium
                    )
                }

                repeat(10) {
                    item {
                        HorizontalProductItem(
                            onAdd = {
                                coroutineScope.launch { state.animateTo(ModalBottomSheetValue.Expanded) }
                            },
                            onClick = {
                                navController.navigate("detail")
                            }
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
                }

            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageSlider(
    pagerState: PagerState
) {
    val images = listOf(
        R.drawable.a,
        R.drawable.b,
        R.drawable.c,
        R.drawable.d,
        R.drawable.e,
    )

    LaunchedEffect(Unit) {
        while (true) {
            yield()
            delay(2000)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount)
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = EXTRA_LARGE_PADDING),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        HorizontalPager(
            count = images.size,
            state = pagerState
        ) { page ->
            Image(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .fillMaxWidth()
                    .height(139.dp)
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                        lerp(
                            start = 0.6f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    },
                painter = painterResource(id = images[page]),
                contentDescription = "Image slider",
                contentScale = ContentScale.Crop,
            )
        }

        Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))

        HorizontalPagerIndicator(
            pagerState = pagerState,
            inactiveColor = MaterialTheme.colors.textColor,
            activeColor = MaterialTheme.colors.textColor2
        )
    }
}



