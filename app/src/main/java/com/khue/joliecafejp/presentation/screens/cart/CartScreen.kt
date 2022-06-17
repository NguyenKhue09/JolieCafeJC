package com.khue.joliecafejp.presentation.screens.cart

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.khue.joliecafejp.R
import com.khue.joliecafejp.domain.model.CartItemByCategory
import com.khue.joliecafejp.domain.model.SnackBarData
import com.khue.joliecafejp.domain.model.TestItem
import com.khue.joliecafejp.navigation.nav_screen.CartSubScreen
import com.khue.joliecafejp.navigation.nav_screen.HomeSubScreen
import com.khue.joliecafejp.presentation.common.ButtonCustom
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
import com.khue.joliecafejp.utils.Constants.Companion.CARTS
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun CartScreen(
    cartViewModel: CartViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    navController: NavHostController
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

    println("CartScreen: $userToken")

    LaunchedEffect(key1 = userToken) {
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
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
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
                        Column(
                            modifier = Modifier.background(color = MaterialTheme.colors.greyPrimary)
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = SMALL_PADDING, top = SMALL_PADDING),
                                text = stringResource(id = R.string.total_cart_product_number, 5),
                                fontSize = MaterialTheme.typography.h5.fontSize,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colors.titleTextColor,
                                fontFamily = montserratFontFamily,
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Checkbox(
                                    checked = false,
                                    onCheckedChange = {},
                                    colors = CheckboxDefaults.colors(
                                        uncheckedColor = MaterialTheme.colors.textColor,
                                        checkedColor = MaterialTheme.colors.titleTextColor,
                                        disabledColor = MaterialTheme.colors.titleTextColor
                                    )
                                )
                                Text(
                                    modifier = Modifier
                                        .wrapContentSize(align = Alignment.CenterStart),
                                    text = "Check all",
                                    fontFamily = raleway,
                                    color = MaterialTheme.colors.textColor,
                                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    getCartItemResponse.data?.let {
                        items(it) { item ->
                            CartProductGroup(
                                cartItemByCategory = item,
                                onSelectedGroup = { _, _ -> },
                                isSelectedGroup = false
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(58.dp))
                    }
                }

                BottomButtonAction(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    totalCost = 1000000.0,
                    onCheckoutClicked = {
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            key = CARTS,
                            value = getCartItemResponse.data!![0].products
                        )
                        navController.navigate(CartSubScreen.Checkout.route)
                    }
                )
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

@Composable
fun BottomButtonAction(
    modifier: Modifier = Modifier,
    totalCost: Double,
    onCheckoutClicked: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        MaterialTheme.colors.greyPrimary,
                        MaterialTheme.colors.greyPrimary,
                        MaterialTheme.colors.greyPrimary,
                    ),
                )
            )
            .padding(top = EXTRA_LARGE_PADDING, bottom = MEDIUM_PADDING),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Spacer(modifier = Modifier.width(EXTRA_LARGE_PADDING))
        Text(
            modifier = Modifier
                .weight(1.2f)
                .wrapContentSize(align = Alignment.CenterStart),
            text = stringResource(
                R.string.total_cost, NumberFormat.getNumberInstance(
                    Locale.US
                ).format(totalCost)
            ),
            fontFamily = montserratFontFamily,
            color = MaterialTheme.colors.textColor,
            fontSize = MaterialTheme.typography.body1.fontSize,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.width(EXTRA_LARGE_PADDING))
        ButtonCustom(
            modifier = Modifier.weight(0.8f),
            buttonContent = stringResource(R.string.checkout),
            backgroundColor = MaterialTheme.colors.textColor2,
            textColor = MaterialTheme.colors.textColor,
            onClick = onCheckoutClicked,
            paddingValues = PaddingValues(ZERO_PADDING),
            contentPadding = PaddingValues(
                horizontal = EXTRA_LARGE_PADDING,
                vertical = MEDIUM_SMALL_PADDING
            ),
            buttonElevation = null,
            shapes = MaterialTheme.shapes.medium
        )
        Spacer(modifier = Modifier.width(EXTRA_LARGE_PADDING))
    }
}

@Preview
@Composable
fun BottomButtonActionPrev() {
    BottomButtonAction(
        totalCost = 100000.0,
        onCheckoutClicked = {}
    )
}
