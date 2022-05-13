package com.khue.joliecafejp.presentation.screens.home.sub_screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.khue.joliecafejp.R
import com.khue.joliecafejp.domain.model.CategoryButtonItem
import com.khue.joliecafejp.navigation.nav_screen.HomeSubScreen
import com.khue.joliecafejp.presentation.common.CategoriesButtonGroup
import com.khue.joliecafejp.presentation.common.SearchBar
import com.khue.joliecafejp.presentation.common.TopBar
import com.khue.joliecafejp.presentation.common.VerticalProductItem
import com.khue.joliecafejp.presentation.components.FavoriteItem
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
                            onItemClicked = {},
                            onFavClicked = {}
                        )
                    }
                }
            }
        }
    }
}