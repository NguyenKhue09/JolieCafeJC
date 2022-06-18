package com.khue.joliecafejp.presentation.screens.home.sub_screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.khue.joliecafejp.R
import com.khue.joliecafejp.domain.model.CategoryButtonItem
import com.khue.joliecafejp.domain.model.Product
import com.khue.joliecafejp.domain.model.SnackBarData
import com.khue.joliecafejp.navigation.nav_screen.HomeSubScreen
import com.khue.joliecafejp.presentation.common.*
import com.khue.joliecafejp.presentation.viewmodels.CategoryViewModel
import com.khue.joliecafejp.ui.theme.*
import com.khue.joliecafejp.utils.ApiResult
import com.khue.joliecafejp.utils.Constants.Companion.SNACK_BAR_STATUS_ERROR
import com.khue.joliecafejp.utils.Constants.Companion.SNACK_BAR_STATUS_SUCCESS
import com.khue.joliecafejp.utils.extensions.items
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CategoriesScreen(
    navController: NavHostController,
    categoryViewModel: CategoryViewModel = hiltViewModel()
) {

    val snackbarHostState = remember { SnackbarHostState() }

    val searchTextState by categoryViewModel.searchTextState
    val selectedCategory by categoryViewModel.selectedCategory

    val userToken by categoryViewModel.userToken.collectAsState(initial = "")
    val userFavProductsId by categoryViewModel.favProductsId.collectAsState()

    val categoryProducts = categoryViewModel.categoryProduct.collectAsLazyPagingItems()

    var snackBarData by remember {
       mutableStateOf( SnackBarData(
           iconId = R.drawable.ic_success,
           message = "",
           snackBarState = SNACK_BAR_STATUS_SUCCESS,
       ))
    }

    LaunchedEffect(key1 = true) {
        categoryViewModel.initData(token = userToken)
        categoryViewModel.getUserFavProductsId(token = userToken)
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
    )

    var clickedProduct by remember {
        mutableStateOf<Product?>(null)
    }

    LaunchedEffect(key1 = true) {
        launch(Dispatchers.IO) {
            categoryViewModel.removeUserFavResponse.collect { result ->
                when (result) {
                    is ApiResult.NullDataSuccess -> {
                        snackBarData = snackBarData.copy(
                            message = "Remove ${clickedProduct?.name} from favorite success",
                            iconId = R.drawable.ic_success,
                            snackBarState = SNACK_BAR_STATUS_SUCCESS
                        )
                        snackbarHostState.showSnackbar("")
                    }
                    is ApiResult.Error -> {
                        snackBarData = snackBarData.copy(
                            message = "Remove ${clickedProduct?.name} from favorite failed!",
                            iconId = R.drawable.ic_error,
                            snackBarState = SNACK_BAR_STATUS_ERROR
                        )
                        snackbarHostState.showSnackbar("")
                        userFavProductsId.data?.add(clickedProduct!!.id)
                    }
                    else -> {}
                }

            }
        }
        launch(Dispatchers.IO) {
            categoryViewModel.addUserFavResponse.collect { result ->
                when (result) {
                    is ApiResult.NullDataSuccess -> {
                        snackBarData = snackBarData.copy(
                            message = "Add ${clickedProduct?.name} to favorite success",
                            iconId = R.drawable.ic_success,
                            snackBarState = SNACK_BAR_STATUS_SUCCESS
                        )
                        snackbarHostState.showSnackbar("")
                    }
                    is ApiResult.Error -> {
                        snackBarData = snackBarData.copy(
                            message = "Add ${clickedProduct?.name} to favorite failed!",
                            iconId = R.drawable.ic_error,
                            snackBarState = SNACK_BAR_STATUS_ERROR
                        )
                        snackbarHostState.showSnackbar("")
                        userFavProductsId.data?.remove(clickedProduct!!.id)
                    }
                    else -> {}
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.greyPrimary,
        topBar = {
            TopBar(
                titleId = HomeSubScreen.Categories.titleId,
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
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = EXTRA_LARGE_PADDING)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
            SearchBar(
                text = searchTextState,
                onCloseClicked = {
                    categoryViewModel.updateSearchTextState(newValue = "")
                },
                onSearchClicked = { searchString ->

                },
                onTextChange = { newValue ->
                    categoryViewModel.updateSearchTextState(newValue = newValue)
                }
            )
            Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
            CategoriesButtonGroup(
                categories = categories,
                selectedButton = selectedCategory
            ) { category ->
                categoryViewModel.getCategoriesProducts(
                    productQuery = mapOf("type" to category),
                    token = userToken
                )
                categoryViewModel.updateSelectedCategory(newValue = category)
            }
            Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))

            val result = handlePagingResult(categoryProducts = categoryProducts)

            AnimatedVisibility(visible = result) {
                LazyVerticalGrid(
                    modifier = Modifier.weight(1f),
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(EXTRA_LARGE_PADDING),
                    horizontalArrangement = Arrangement.spacedBy(EXTRA_LARGE_PADDING),
                    contentPadding = PaddingValues(bottom = EXTRA_LARGE_PADDING)
                ) {

                    items(
                        lazyPagingItems = categoryProducts
                    ) { product ->
                        product?.let {

                            println(product.id)
                            var isFav = false

                            userFavProductsId.data?.let { ids ->
                                isFav = ids.contains(product.id)
                            }

                            VerticalProductItem(
                                product = product,
                                isFav = isFav,
                                onItemClicked = { productId ->
                                    navController.navigate("detail/${productId}?isFav=${isFav}")
                                },
                                onFavClicked = { id ->
                                    clickedProduct = product
                                    if (isFav) {
                                        userFavProductsId.data?.remove(id)
                                        categoryViewModel.removeUserFavProduct(
                                            token = userToken,
                                            productId = id
                                        )
                                    } else {
                                        userFavProductsId.data?.add(id)
                                        categoryViewModel.addUserFavProduct(
                                            token = userToken,
                                            productId = id
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            }

        }
    }
}

@Composable
internal fun handlePagingResult(
    categoryProducts: LazyPagingItems<Product>
): Boolean {
    categoryProducts.apply {
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
                Toast.makeText(LocalContext.current, error.error.message, Toast.LENGTH_SHORT).show()
                false
            }
            categoryProducts.itemCount < 1 -> {
                false
            }
            else -> {
                true
            }
        }
    }
}
