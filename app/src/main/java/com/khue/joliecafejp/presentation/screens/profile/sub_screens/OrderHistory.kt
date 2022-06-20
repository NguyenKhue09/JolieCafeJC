package com.khue.joliecafejp.presentation.screens.profile.sub_screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
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
import androidx.paging.compose.itemsIndexed
import com.khue.joliecafejp.R
import com.khue.joliecafejp.domain.model.BillReviewBody
import com.khue.joliecafejp.domain.model.OrderHistory
import com.khue.joliecafejp.domain.model.SnackBarData
import com.khue.joliecafejp.navigation.nav_screen.ProfileSubScreen
import com.khue.joliecafejp.presentation.common.LoadingBody
import com.khue.joliecafejp.presentation.common.SnackBar
import com.khue.joliecafejp.presentation.common.TopBar
import com.khue.joliecafejp.presentation.components.OrderHistoryItem
import com.khue.joliecafejp.presentation.components.ReviewBillDialog
import com.khue.joliecafejp.presentation.viewmodels.OrderHistoryViewModel
import com.khue.joliecafejp.ui.theme.EXTRA_LARGE_PADDING
import com.khue.joliecafejp.ui.theme.MEDIUM_PADDING
import com.khue.joliecafejp.ui.theme.greyPrimary
import com.khue.joliecafejp.utils.ApiResult
import com.khue.joliecafejp.utils.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun OrderHistory(
    navController: NavHostController,
    orderHistoryViewModel: OrderHistoryViewModel = hiltViewModel()
) {

    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val scrollToPosition = remember { mutableStateOf(0F) }

    val userToken by orderHistoryViewModel.userToken.collectAsState(initial = "")
    val bills = orderHistoryViewModel.bills.collectAsLazyPagingItems()

    val snackbarHostState = remember { SnackbarHostState() }
    var snackBarData by remember {
        mutableStateOf(
            SnackBarData(
                iconId = R.drawable.ic_success,
                message = "",
                snackBarState = Constants.SNACK_BAR_STATUS_SUCCESS,
            )
        )
    }

    var showReviewBillDialog by remember { mutableStateOf(false) }
    var reviewBillSelected by remember {
        mutableStateOf(BillReviewBody(
            billId = "",
            rating = 0f,
            content = "",
            productIds = emptyList()
        ))
    }

    LaunchedEffect(key1 = Unit) {
        orderHistoryViewModel.getUserBills(userToken)
        orderHistoryViewModel.reviewBillResponse.collectLatest { result ->
            when(result) {
                is ApiResult.Loading -> {

                }
                is ApiResult.NullDataSuccess -> {
                    snackBarData = snackBarData.copy(
                        message = "Review bill success",
                        snackBarState = Constants.SNACK_BAR_STATUS_SUCCESS
                    )
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("")
                    }
                    bills.refresh()
                }
                is ApiResult.Error -> {
                    snackBarData = snackBarData.copy(
                        message = "Review bill failed",
                        snackBarState = Constants.SNACK_BAR_STATUS_ERROR,
                        iconId = R.drawable.ic_error
                    )
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("")
                    }
                }
                else -> {}
            }
        }
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
        val result = handleBillPagingResult(bills = bills) { message, type ->
            snackBarData = SnackBarData(
                iconId = R.drawable.ic_error,
                message = "Order history could not be loaded",
                snackBarState = type
            )
            coroutineScope.launch {
                snackbarHostState.showSnackbar("")
            }
        }

        AnimatedVisibility (
            visible = result,
            exit = fadeOut(),
            enter = fadeIn()
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(EXTRA_LARGE_PADDING),
                contentPadding = PaddingValues(
                    all = EXTRA_LARGE_PADDING,
                ),
                state = scrollState
            ) {
                itemsIndexed(
                    bills,
                    key = { _, bill -> bill.id },
                ) { index, bill ->
                    var isExpanded by rememberSaveable {
                        mutableStateOf(false)
                    }

                    bill?.let {
                        OrderHistoryItem(
                            bill = bill,
                            isExpanded = isExpanded,
                            scrollToPosition = scrollToPosition,
                            onExpanded = {
                                isExpanded = !isExpanded
                                if (isExpanded) {
                                    coroutineScope.launch {
                                        delay(500L)
                                        scrollState.animateScrollToItem(index)
                                    }
                                }
                            },
                            onReviewClicked = {
                                showReviewBillDialog = true
                                reviewBillSelected = reviewBillSelected.copy(
                                    billId = bill.id,
                                    productIds = bill.products.map { item -> item.product.id }
                                )
                            }
                        )
                    }
                }
            }
        }

        if (showReviewBillDialog) {
            ReviewBillDialog(
                onDismiss = {
                    showReviewBillDialog = false
                },
                onNegativeClick = {
                    showReviewBillDialog = false
                },
                onPositiveClick = { rating, comment ->
                    reviewBillSelected = reviewBillSelected.copy(
                        rating = rating,
                        content = comment
                    )
                    println(reviewBillSelected)
                    orderHistoryViewModel.reviewBills(
                        token = userToken,
                        billReviewBody = reviewBillSelected
                    )
                    showReviewBillDialog = false
                }
            )
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