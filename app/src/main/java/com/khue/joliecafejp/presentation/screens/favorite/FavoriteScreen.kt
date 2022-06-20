package com.khue.joliecafejp.presentation.screens.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.khue.joliecafejp.R
import com.khue.joliecafejp.domain.model.SnackBarData
import com.khue.joliecafejp.presentation.common.SnackBar
import com.khue.joliecafejp.presentation.viewmodels.FavoriteViewModel
import com.khue.joliecafejp.ui.theme.*
import com.khue.joliecafejp.utils.ApiResult
import com.khue.joliecafejp.utils.Constants
import com.khue.joliecafejp.utils.extensions.customTabIndicatorOffset
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun FavoriteScreen(
    paddingValues: PaddingValues,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val tabs = listOf(
        "All",
        "Coffee",
        "Tea",
        "Juice",
        "Pasty",
        "Milk shake",
        "Milk tea"
    )

    val pagerState = rememberPagerState(initialPage = 0)
    val coroutineScope = rememberCoroutineScope()

    val userToken by favoriteViewModel.userToken.collectAsState(initial = "")
    val favoriteProducts = favoriteViewModel.favoriteProduct.collectAsLazyPagingItems()

    val removeUserFavProductResponse = favoriteViewModel.removeUserFavProductResponse

    val snackbarHostState = remember { SnackbarHostState() }
    var snackBarData by remember {
        mutableStateOf( SnackBarData(
            iconId = R.drawable.ic_success,
            message = "",
            snackBarState = Constants.SNACK_BAR_STATUS_SUCCESS,
        )
        )
    }


    LaunchedEffect(key1 = true) {
        removeUserFavProductResponse.collectLatest { result ->
            when (result) {
                is ApiResult.NullDataSuccess -> {
                    favoriteProducts.refresh()
                    snackBarData = snackBarData.copy(
                        message = "Remove favorite product success!",
                        iconId = R.drawable.ic_success,
                        snackBarState = Constants.SNACK_BAR_STATUS_SUCCESS
                    )
                    snackbarHostState.showSnackbar("")
                }
                is ApiResult.Error -> {
                    snackBarData = snackBarData.copy(
                        message = result.message!!,
                        iconId = R.drawable.ic_error,
                        snackBarState = Constants.SNACK_BAR_STATUS_ERROR
                    )
                    snackbarHostState.showSnackbar("")
                }
                else -> {
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize().padding(paddingValues),
        backgroundColor = MaterialTheme.colors.greyPrimary,
        snackbarHost = {
            SnackBar(
                modifier = Modifier.padding(MEDIUM_PADDING),
                snackbarHostState = snackbarHostState,
                snackBarData = snackBarData
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(MaterialTheme.colors.greyPrimary)
                .padding(start = EXTRA_LARGE_PADDING, top = EXTRA_LARGE_PADDING, end = EXTRA_LARGE_PADDING),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(R.string.favorite),
                fontSize = MaterialTheme.typography.h5.fontSize,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.titleTextColor,
                fontFamily = montserratFontFamily,
            )

            CustomScrollableTabRow(
                tabs = tabs,
                pagerState = pagerState
            ) { tabIndex ->
                coroutineScope.launch {
                    pagerState.animateScrollToPage(page = tabIndex)
                }
            }

            HorizontalPager(
                count = tabs.size,
                state = pagerState,
            ) {
                LaunchedEffect(key1 = pagerState.currentPage) {
                    println("currentPage: ${pagerState.currentPage}")
                    favoriteViewModel.getUserFavoriteProducts(
                        productQuery = mapOf("type" to tabs[pagerState.currentPage]),
                        token = userToken
                    )
                }

                println("HorizontalPager recomposed")

                FavoriteBody(
                    favoriteProducts = favoriteProducts,
                    onFavClicked = { id ->
                        favoriteViewModel.removeUserFavoriteProduct(
                            token = userToken,
                            favoriteProductId = id
                        )
                    },
                    onItemClicked = { id ->
                        navController.navigate("detail/${id}?isFav=${true}")
                    },
                    showMessage = { message ->
                        println(message)
                        snackBarData = snackBarData.copy(
                            message = "Favorite product could not be loaded",
                            iconId = R.drawable.ic_error,
                            snackBarState = Constants.SNACK_BAR_STATUS_ERROR
                        )
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("")
                        }
                    }
                )
            }
        }
    }

}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun CustomScrollableTabRow(
    tabs: List<String>,
    pagerState: PagerState,
    onTabClick: (Int) -> Unit,
) {
    val density = LocalDensity.current
    val tabWidths = remember {
        val tabWidthStateList = mutableStateListOf<Dp>()
        repeat(tabs.size) {
            tabWidthStateList.add(0.dp)
        }
        tabWidthStateList
    }



    ScrollableTabRow(
        modifier = Modifier.padding(top = 24.dp),
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.Transparent,
        contentColor = MaterialTheme.colors.textColor,
        edgePadding = 0.dp,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.customTabIndicatorOffset(
                    currentTabPosition = tabPositions[pagerState.currentPage],
                    tabWidth = tabWidths[pagerState.currentPage]
                ),
                color = MaterialTheme.colors.titleTextColor
            )
        },
    ) {
        tabs.forEachIndexed { tabIndex, tab ->
            Tab(
                modifier = Modifier.height(30.dp).clip(shape = MaterialTheme.shapes.small),
                selected = pagerState.currentPage == tabIndex,
                onClick = { onTabClick(tabIndex) },
                selectedContentColor = MaterialTheme.colors.titleTextColor,
                unselectedContentColor = MaterialTheme.colors.textColor,
                text = {
                    Text(
                        modifier = Modifier.padding(horizontal = 0.dp),
                        text = tab,
                        onTextLayout = { textLayoutResult ->
                            tabWidths[tabIndex] =
                                with(density) { textLayoutResult.size.width.toDp() }
                        },
                        fontSize = 18.sp,
                        fontFamily = ralewayMedium,
                    )
                },
            )
        }
    }
}

