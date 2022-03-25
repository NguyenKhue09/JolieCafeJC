package com.khue.joliecafejp.presentation.screens.favorite

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.khue.joliecafejp.R
import com.khue.joliecafejp.ui.theme.*
import kotlinx.coroutines.launch


@Composable
fun FavoriteScreen() {

    val tabs = listOf(
        "All",
        "Coffee",
        "Tea",
        "Juice",
        "Pasty"
    )

    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.greyPrimary)
            .padding(start = 20.dp, top = 20.dp, bottom = 58.dp, end = 20.dp),
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
            selectedTabIndex = selectedTabIndex,
        ) { tabIndex ->
            selectedTabIndex = tabIndex
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize().background(color = Color.White)
        ) {
            Text(tabs[selectedTabIndex])
        }
    }
}


@Composable
fun CustomScrollableTabRow(
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabClick: (Int) -> Unit
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
        selectedTabIndex = selectedTabIndex,
        backgroundColor = Color.Transparent,
        contentColor = MaterialTheme.colors.textColor,
        edgePadding = 0.dp,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.customTabIndicatorOffset(
                    currentTabPosition = tabPositions[selectedTabIndex],
                    tabWidth = tabWidths[selectedTabIndex]
                ),
                color = MaterialTheme.colors.titleTextColor
            )
        },
    ) {
        tabs.forEachIndexed { tabIndex, tab ->
            Tab(
                modifier = Modifier.height(30.dp),
                selected = selectedTabIndex == tabIndex,
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
