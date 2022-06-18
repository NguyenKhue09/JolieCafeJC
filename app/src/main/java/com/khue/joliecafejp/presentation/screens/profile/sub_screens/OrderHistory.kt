package com.khue.joliecafejp.presentation.screens.profile.sub_screens

import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.khue.joliecafejp.R
import com.khue.joliecafejp.domain.model.OrderHistory
import com.khue.joliecafejp.domain.model.SnackBarData
import com.khue.joliecafejp.navigation.nav_screen.ProfileSubScreen
import com.khue.joliecafejp.presentation.common.LoadingBody
import com.khue.joliecafejp.presentation.common.SnackBar
import com.khue.joliecafejp.presentation.components.OrderHistoryItem
import com.khue.joliecafejp.presentation.common.TopBar
import com.khue.joliecafejp.presentation.viewmodels.OrderHistoryViewModel
import com.khue.joliecafejp.ui.theme.EXTRA_LARGE_PADDING
import com.khue.joliecafejp.ui.theme.MEDIUM_PADDING
import com.khue.joliecafejp.ui.theme.greyPrimary
import com.khue.joliecafejp.utils.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OrderHistory(
    navController: NavHostController,
    orderHistoryViewModel: OrderHistoryViewModel = hiltViewModel()
) {

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val scrollToPosition = remember { mutableStateOf(0F) }

    val userToken by orderHistoryViewModel.userToken.collectAsState(initial = "")
    val bills = orderHistoryViewModel.bills.collectAsLazyPagingItems()

    val snackbarHostState = remember { SnackbarHostState() }
    var snackBarData by remember {
        mutableStateOf( SnackBarData(
            iconId = R.drawable.ic_success,
            message = "",
            snackBarState = Constants.SNACK_BAR_STATUS_SUCCESS,
        )
        )
    }

    LaunchedEffect(key1 = Unit) {
        orderHistoryViewModel.getUserBills(userToken)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.greyPrimary,
        topBar = {
            TopBar(
                titleId = ProfileSubScreen.OrderHistory.titleId,
                navController = navController
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
        val result = handleBillPagingResult(bills = bills) {message, type ->
            snackBarData = SnackBarData(
                iconId = R.drawable.ic_error,
                message = message,
                snackBarState = type
            )
            coroutineScope.launch {
                snackbarHostState.showSnackbar("")
            }
        }

        if(result) {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(EXTRA_LARGE_PADDING),
                contentPadding = PaddingValues(
                    all = EXTRA_LARGE_PADDING,
                )
            ) {
                items(bills) { bill ->
                    var isExpanded by rememberSaveable  {
                        mutableStateOf(false)
                    }

                    bill?.let {
                        OrderHistoryItem(
                            bill = bill,
                            isExpanded = isExpanded,
                            scrollToPosition = scrollToPosition,
                            onExpanded = {
                                isExpanded = !isExpanded
                                coroutineScope.launch {
                                    delay(500L)
                                    scrollState.animateScrollBy(scrollToPosition.value)
                                }
                            },
                            onReviewClicked = {}
                        )
                    }
                }
            }
        }

    }
}

@Composable
internal fun handleBillPagingResult(
    bills: LazyPagingItems<OrderHistory>,
    showMessage: (String, Int) -> Unit
): Boolean {
    bills.apply {
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
            bills.itemCount < 1 -> {
                false
            }
            else -> {
                true
            }
        }
    }
}

@Preview
@Composable
fun OrderHistoryPrev() {
    OrderHistory(
        navController = rememberNavController()
    )
}