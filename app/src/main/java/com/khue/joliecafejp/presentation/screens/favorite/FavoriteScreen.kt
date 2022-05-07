package com.khue.joliecafejp.presentation.screens.favorite

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.khue.joliecafejp.R
import com.khue.joliecafejp.presentation.viewmodels.FavoriteViewModel
import com.khue.joliecafejp.ui.theme.*
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun FavoriteScreen(
    paddingValues: PaddingValues,
    favoriteViewModel: FavoriteViewModel = hiltViewModel()
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
    var selectedPage by remember {
        mutableStateOf(0)
    }

    val userToken by favoriteViewModel.userToken.collectAsState(initial = "")
    val favoriteProducts = favoriteViewModel.favoriteProduct.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(MaterialTheme.colors.greyPrimary)
            .padding(start = 20.dp, top = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = stringResource(R.string.favorite),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.titleTextColor,
            fontFamily = montserratFontFamily,
        )

        CustomScrollableTabRow(
            tabs = tabs,
            pagerState = pagerState
        ) { tabIndex ->
            selectedPage = tabIndex
            coroutineScope.launch {
                pagerState.animateScrollToPage(page = tabIndex)
            }
        }

        HorizontalPager(
            count = tabs.size,
            state = pagerState,
        ) {
            LaunchedEffect(key1 = selectedPage) {
                favoriteViewModel.getUserFavoriteProducts(
                    productQuery = mapOf("type" to tabs[selectedPage]),
                    token = userToken
                )
            }

            FavoriteBody(favoriteProducts = favoriteProducts)
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
                modifier = Modifier.height(30.dp),
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
                        fontFamily = raleway,
                        fontWeight = FontWeight.Bold,
                    )
                },
            )
        }
    }
}

fun Modifier.customTabIndicatorOffset(
    currentTabPosition: TabPosition,
    tabWidth: Dp
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "customTabIndicatorOffset"
        value = currentTabPosition
    }
) {
    val currentTabWidth by animateDpAsState(
        targetValue = tabWidth,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
    )
    val indicatorOffset by animateDpAsState(
        targetValue = ((currentTabPosition.left + currentTabPosition.right - tabWidth) / 2),
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset(x = indicatorOffset)
        .width(currentTabWidth)
}
