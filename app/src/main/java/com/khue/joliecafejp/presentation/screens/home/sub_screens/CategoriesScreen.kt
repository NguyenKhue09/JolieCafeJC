package com.khue.joliecafejp.presentation.screens.home.sub_screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.khue.joliecafejp.navigation.nav_screen.HomeSubScreen
import com.khue.joliecafejp.presentation.common.*
import com.khue.joliecafejp.presentation.viewmodels.CategoryViewModel
import com.khue.joliecafejp.ui.theme.EXTRA_LARGE_PADDING
import com.khue.joliecafejp.ui.theme.greyPrimary
import com.khue.joliecafejp.utils.extensions.items

@Composable
fun CategoriesScreen(
    navController: NavHostController,
    categoryViewModel: CategoryViewModel = hiltViewModel()
) {

    val searchTextState by categoryViewModel.searchTextState
    val selectedCategory by categoryViewModel.selectedCategory

    val userToken by categoryViewModel.userToken.collectAsState(initial = "")

    val categoryProducts = categoryViewModel.categoryProduct.collectAsLazyPagingItems()

    categoryViewModel.initData(token = userToken)

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
            title = "Pastry",
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

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.greyPrimary,
        topBar = {
            TopBar(
                titleId = HomeSubScreen.Categories.titleId,
                navController = navController
            )
        },
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
                categoryViewModel.getCategoriesProducts(productQuery = mapOf("type" to category), token = userToken)
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
                            VerticalProductItem(
                                product = product,
                                onItemClicked = { productId ->
                                    navController.navigate("detail/${productId}")
                                },
                                onFavClicked = {}
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun handlePagingResult(
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
