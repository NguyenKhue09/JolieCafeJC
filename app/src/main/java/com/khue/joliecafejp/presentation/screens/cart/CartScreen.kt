package com.khue.joliecafejp.presentation.screens.cart

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.khue.joliecafejp.R
import com.khue.joliecafejp.domain.model.CartItemByCategory
import com.khue.joliecafejp.domain.model.SnackBarData
import com.khue.joliecafejp.presentation.common.LoadingBody
import com.khue.joliecafejp.presentation.common.SnackBar
import com.khue.joliecafejp.presentation.common.SnackBarContentUI
import com.khue.joliecafejp.presentation.components.CartProductGroup
import com.khue.joliecafejp.presentation.screens.home.sub_screens.handlePagingResult
import com.khue.joliecafejp.presentation.viewmodels.CartViewModel
import com.khue.joliecafejp.presentation.viewmodels.UserSharedViewModel
import com.khue.joliecafejp.ui.theme.*
import com.khue.joliecafejp.utils.ApiResult
import com.khue.joliecafejp.utils.Constants
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun CartScreen(
    cartViewModel: CartViewModel = hiltViewModel(),
    paddingValues: PaddingValues
) {

    val userToken by cartViewModel.userToken.collectAsState(initial = "")
    val getCartItemResponse by cartViewModel.getCartItemResponse.collectAsState()

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

    val coroutineScope = rememberCoroutineScope()
    var isLoading by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit) {
        cartViewModel.getCartItems(token = userToken)
    }

    Scaffold(
        modifier = Modifier.padding(paddingValues),
        snackbarHost = {
            SnackBar(
                modifier = Modifier.padding(MEDIUM_PADDING),
                snackbarHostState = snackbarHostState,
                snackBarData = snackBarData
            )
        },
        backgroundColor = MaterialTheme.colors.greyPrimary
    ) {

        val result = handleGetCartItemResponse(getCartItemResponse = getCartItemResponse) {
            snackBarData = SnackBarData(
                iconId = R.drawable.ic_error,
                message = "Cart items could not be loaded",
                snackBarState = Constants.SNACK_BAR_STATUS_ERROR,
            )
            coroutineScope.launch {
                snackbarHostState.showSnackbar("")
            }
        }

        if (result) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(MaterialTheme.colors.greyPrimary)
                    .padding(
                        start = EXTRA_LARGE_PADDING,
                        end = EXTRA_LARGE_PADDING
                    ),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                stickyHeader {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colors.greyPrimary)
                            .padding(bottom = SMALL_PADDING, top = SMALL_PADDING),
                        text = stringResource(id = R.string.total_cart_product_number, 5),
                        fontSize = MaterialTheme.typography.h5.fontSize,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.titleTextColor,
                        fontFamily = montserratFontFamily,
                    )
                }

               items(getCartItemResponse.data!!) { item ->
                   CartProductGroup(
                       cartItemByCategory = item,
                       onSelectedGroup = {_,_ ->},
                       isSelectedGroup = false
                   )
               }
            }
        }
    }
}

@Composable
fun handleGetCartItemResponse(
    getCartItemResponse: ApiResult<List<CartItemByCategory>>,
    showMessage: (String) -> Unit
): Boolean {
    return when (getCartItemResponse) {
        is ApiResult.Loading -> {
            LoadingBody()
            false
        }
        is ApiResult.Success -> {
            true
        }
        is ApiResult.Error -> {
            showMessage("Cart items could not be loaded")
            false
        }
        else -> {
            false
        }
    }
}
