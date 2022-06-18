package com.khue.joliecafejp.presentation.screens.home.sub_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.khue.joliecafejp.R
import com.khue.joliecafejp.domain.model.Notification
import com.khue.joliecafejp.domain.model.SnackBarData
import com.khue.joliecafejp.navigation.nav_screen.HomeSubScreen
import com.khue.joliecafejp.presentation.common.LoadingBody
import com.khue.joliecafejp.presentation.common.SnackBar
import com.khue.joliecafejp.presentation.common.TopBar
import com.khue.joliecafejp.presentation.components.NotificationItem
import com.khue.joliecafejp.presentation.viewmodels.NotificationVieModel
import com.khue.joliecafejp.ui.theme.*
import com.khue.joliecafejp.utils.Constants
import com.khue.joliecafejp.utils.Constants.Companion.listNotificationTab
import com.khue.joliecafejp.utils.extensions.customTabIndicatorOffset
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NotificationsScreen(
    navController: NavHostController,
    notificationVieModel: NotificationVieModel = hiltViewModel()
) {

    val tabs = listNotificationTab

    val pagerState = rememberPagerState(initialPage = 0)
    val coroutineScope = rememberCoroutineScope()

    val userToken by notificationVieModel.userToken.collectAsState(initial = "")
    val notifications = notificationVieModel.notifications.collectAsLazyPagingItems()

    val snackbarHostState = remember { SnackbarHostState() }
    var snackBarData by remember {
        mutableStateOf( SnackBarData(
            iconId = R.drawable.ic_success,
            message = "",
            snackBarState = Constants.SNACK_BAR_STATUS_SUCCESS,
        )
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.greyPrimary,
        topBar = {
            TopBar(
                titleId = HomeSubScreen.Notifications.titleId,
                navController = navController,
                trailingButton = null
            )
        },
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
                .background(MaterialTheme.colors.greyPrimary),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            CustomTabRow(
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
                    notificationVieModel.getNotifications(
                        token = userToken,
                        tab = tabs[pagerState.currentPage]
                    )
                }

                NotificationTabPage(
                    notifications = notifications,
                ) { message, type ->
                    snackBarData = SnackBarData(
                        iconId = R.drawable.ic_error,
                        message = message,
                        snackBarState = type
                    )
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("")
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationTabPage(
    notifications: LazyPagingItems<Notification>,
    showMessage: (String, Int) -> Unit
) {
    val result = handleNotificationPagingResult(
        notifications = notifications,
        showMessage = showMessage
    )
    if(result) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(all = EXTRA_LARGE_PADDING),
            verticalArrangement = Arrangement.spacedBy(EXTRA_LARGE_PADDING)
        ) {
            items(notifications) { notification ->
                notification?.let {
                    NotificationItem(
                        notification = notification
                    )
                }
            }
        }
    }
}

@Composable
internal fun handleNotificationPagingResult(
    notifications: LazyPagingItems<Notification>,
    showMessage: (String, Int) -> Unit
): Boolean {
    notifications.apply {
        val error = when {
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            else -> null
        }

        return when {
            loadState.refresh is LoadState.Loading -> {
                LoadingBody()
                false
            }
            error != null -> {
                showMessage(error.error.message!!, Constants.SNACK_BAR_STATUS_ERROR)
                false
            }
            notifications.itemCount < 1 -> {
                false
            }
            else -> {
                true
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun CustomTabRow(
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

    TabRow(
        modifier = Modifier.padding(top = EXTRA_LARGE_PADDING, start = EXTRA_LARGE_PADDING, end = EXTRA_LARGE_PADDING),
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.Transparent,
        contentColor = MaterialTheme.colors.textColor,
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

@Preview
@Composable
fun NotificationsScreenPrev() {
    NotificationsScreen(
        navController = rememberNavController()
    )
}