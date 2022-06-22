package com.khue.joliecafejp.presentation.screens.home

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.pager.*
import com.khue.joliecafejp.R
import com.khue.joliecafejp.domain.model.CategoryButtonItem
import com.khue.joliecafejp.domain.model.Product
import com.khue.joliecafejp.domain.model.SnackBarData
import com.khue.joliecafejp.navigation.nav_graph.AUTHENTICATION_ROUTE
import com.khue.joliecafejp.navigation.nav_screen.BottomBarScreen
import com.khue.joliecafejp.navigation.nav_screen.HomeSubScreen
import com.khue.joliecafejp.presentation.common.*
import com.khue.joliecafejp.presentation.components.HomeTopBar
import com.khue.joliecafejp.presentation.components.HorizontalProductItem
import com.khue.joliecafejp.presentation.viewmodels.UserSharedViewModel
import com.khue.joliecafejp.presentation.viewmodels.HomeViewModel
import com.khue.joliecafejp.ui.theme.*
import com.khue.joliecafejp.utils.ApiResult
import com.khue.joliecafejp.utils.Constants
import com.khue.joliecafejp.utils.Constants.Companion.SNACK_BAR_STATUS_ERROR
import com.khue.joliecafejp.utils.Constants.Companion.SNACK_BAR_STATUS_SUCCESS
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue


@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    userSharedViewModel: UserSharedViewModel,
    paddingValues: PaddingValues,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val userLoginResponse by userSharedViewModel.userInfos.collectAsState()
    var userToken by remember {
        mutableStateOf("")
    }
    val searchTextState by homeViewModel.searchTextState
    val pagerState = rememberPagerState()

    val bestSellerProducts = homeViewModel.bestSellerProduct.collectAsLazyPagingItems()
    val userFavProductsId by homeViewModel.favProductsId.collectAsState()

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

    // Bottom Sheet
    val coroutineScope = rememberCoroutineScope()
    val state = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

    LaunchedEffect(key1 = true) {
        userSharedViewModel.userToken.collectLatest { token ->
            userToken = token
            if (userToken.isEmpty()) {
                navController.navigate(AUTHENTICATION_ROUTE) {
                    popUpTo(BottomBarScreen.Home.route) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }

            }
            launch {
                homeViewModel.getProducts(productQuery = mapOf("type" to "All"), token = userToken)
            }
            launch {
                if (userLoginResponse.data == null) userSharedViewModel.getUserInfos(token = userToken)
            }
            launch {
                homeViewModel.getUserFavProductsId(token = userToken)
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        homeViewModel.addProductToCartResponse.collect { result ->
            println(result)
            when (result) {
                is ApiResult.Loading -> {

                }
                is ApiResult.NullDataSuccess -> {
                    snackBarData = snackBarData.copy(
                        message = "Add product to cart success",
                        iconId = R.drawable.ic_success,
                        snackBarState = SNACK_BAR_STATUS_SUCCESS
                    )
                    coroutineScope.launch {
                        state.hide()
                        snackbarHostState.showSnackbar("")
                    }
                }
                is ApiResult.Error -> {
                    snackBarData = snackBarData.copy(
                        message = "Add product to cart failed",
                        iconId = R.drawable.ic_error,
                        snackBarState = SNACK_BAR_STATUS_ERROR
                    )
                    coroutineScope.launch {
                        state.hide()
                        snackbarHostState.showSnackbar("")
                    }
                }
                else -> {}
            }
        }
    }


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
            title = "Pasty",
            iconId = R.drawable.ic_croissant_svgrepo_com
        ),
        CategoryButtonItem(
            title = "Milk shake",
            iconId = R.drawable.ic_coffee
        ),
        CategoryButtonItem(
            title = "Milk tea",
            iconId = R.drawable.ic_coffee
        ),
        CategoryButtonItem(
            title = "More",
            iconId = R.drawable.ic_coffee
        ),
    )

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) {
                coroutineScope.launch {
                    state.hide()
                }
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            homeViewModel.resetAddProductToCartResponse()
        }
    }


    val isLoading = rememberSaveable {
        mutableStateOf(true)
    }

    var selectedProduct by remember {
        mutableStateOf<Product?>(null)
    }

    ModalBottomSheetLayout(
        sheetState = state,
        modifier = Modifier.fillMaxSize(),
        sheetContent = {

                ProductOptionsBottomSheet(
                    homeViewModel = homeViewModel,
                    paddingValues = paddingValues,
                    product = selectedProduct,
                    coroutineScope = coroutineScope,
                    modalBottomSheetState = state,
                    onAddProductToCart = { data ->
                        homeViewModel.addProductToCart(
                            data = data,
                            token = userToken
                        )
                    },
                    onPurchaseProduct = {}
                )

        }
    ) {
        Scaffold(
            modifier = Modifier.padding(paddingValues),
            topBar = {
                HomeTopBar(
                    userLoginResponse = userLoginResponse,
                ) {
                    navController.navigate(HomeSubScreen.Notifications.route) {
                        launchSingleTop = true
                    }
                }
            },
            backgroundColor = MaterialTheme.colors.greyPrimary,
            snackbarHost = {
                SnackBar(
                    modifier = Modifier.padding(MEDIUM_PADDING),
                    snackbarHostState = snackbarHostState,
                    snackBarData = snackBarData
                )
            }
        ) { paddingValues ->
            val padding = paddingValues.calculateBottomPadding()
            val result =
                handlePagingResult(bestSellerProducts = bestSellerProducts, isLoading = isLoading) { message ->
                    snackBarData = snackBarData.copy(
                        message = "Best seller products could not be loaded",
                        iconId = R.drawable.ic_error,
                        snackBarState = SNACK_BAR_STATUS_ERROR
                    )
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("")
                    }
                }

            Box(modifier = Modifier.fillMaxSize()) {
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
                                navController.navigate(HomeSubScreen.Categories.passCategory(category = categories[0].title, search = searchString)) {
                                    launchSingleTop = true
                                }
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
                            fontFamily = ralewayMedium,
                            fontSize = MaterialTheme.typography.h6.fontSize
                        )
                    }

                    item {
                        CategoriesButtonGroup(
                            categories = categories,
                            selectedButton = null
                        ) { category ->
                            navController.navigate(HomeSubScreen.Categories.passCategory(category = category, search = null)) {
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
                            fontFamily = ralewayMedium,
                            fontSize = MaterialTheme.typography.h6.fontSize
                        )
                    }

                    if (result) {
                        items(
                            items = bestSellerProducts,
                            key = { product ->
                                product.id
                            }
                        ) { product ->
                            product?.let {
                                var isFav = false

                                userFavProductsId.data?.let { ids ->
                                    isFav = ids.contains(product.id)
                                }

                                HorizontalProductItem(
                                    product = product,
                                    onAdd = {
                                        selectedProduct = product
                                        coroutineScope.launch {
                                            state.animateTo(
                                                ModalBottomSheetValue.Expanded
                                            )
                                        }
                                    },
                                    onClick = {
                                        navController.navigate("detail/${it.id}?isFav=${isFav}")
                                    }
                                )
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
                    }

                }
                AnimatedVisibility(
                    visible = isLoading.value, modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = EXTRA_EXTRA_LARGE_PADDING),
                    exit = fadeOut(),
                    enter = fadeIn()
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.textColor2
                    )
                }
            }
        }
    }
}

@Composable
fun handlePagingResult(
    bestSellerProducts: LazyPagingItems<Product>,
    isLoading: MutableState<Boolean>,
    showMessage: (message: String) -> Unit
): Boolean {
    bestSellerProducts.apply {
        val error = when {
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            else -> null
        }

        return when {
            loadState.refresh is LoadState.Loading -> {
                isLoading.value = true
                false
            }
            error != null -> {
                showMessage(error.error.message.orEmpty())
                isLoading.value = false
                false
            }
            bestSellerProducts.itemCount < 1 -> {
                isLoading.value = false
                false
            }
            else -> {
                isLoading.value = false
                true
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
                    }
                    .clip(MaterialTheme.shapes.medium),
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



