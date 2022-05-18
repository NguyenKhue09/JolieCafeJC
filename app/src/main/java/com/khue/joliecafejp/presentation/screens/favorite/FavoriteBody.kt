package com.khue.joliecafejp.presentation.screens.favorite

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.layout.LazyLayout
import androidx.compose.foundation.lazy.layout.LazyLayoutItemProvider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.khue.joliecafejp.R
import com.khue.joliecafejp.domain.model.FavoriteProduct
import com.khue.joliecafejp.presentation.common.LoadingBody
import com.khue.joliecafejp.presentation.components.FavoriteItem
import com.khue.joliecafejp.ui.theme.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteBody(
    favoriteProducts: LazyPagingItems<FavoriteProduct>,
    onFavClicked: (String) -> Unit,
    onItemClicked: (String) -> Unit
) {
    val result = handlePagingResult(favoriteProducts = favoriteProducts)

    if(result) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(
                items = favoriteProducts,
                key = { product ->
                    product.id
                }
            ) { product ->
                product?.let { productFav ->
                    FavoriteItem(
                        productFav = productFav,
                        onFavClick = {
                            onFavClicked(product.id)
                        }
                    ) {
                        onItemClicked(product.product.id)
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
            }
        }
    }
}

@Composable
fun handlePagingResult(
    favoriteProducts: LazyPagingItems<FavoriteProduct>
): Boolean {
    favoriteProducts.apply {
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
            favoriteProducts.itemCount < 1 -> {
                EmptyFavBody()
                false
            }
            else -> {
                true
            }
        }
    }
}

@Composable
fun EmptyFavBody() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(EXTRA_LARGE_PADDING),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_refreshing_rafiki),
            contentDescription = stringResource(R.string.empty_fav_products)
        )
        Text(
            text = stringResource(R.string.your_list_is_empty),
            color = MaterialTheme.colors.textColor,
            fontFamily = ralewayMedium,
            fontSize = MaterialTheme.typography.h6.fontSize
        )
        Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
        Text(
            text = stringResource(R.string.empty_list_sub_title),
            color = MaterialTheme.colors.darkTextColor,
            fontFamily = ralewayMedium,
            fontSize = MaterialTheme.typography.caption.fontSize
        )
    }
}



@Preview
@Composable
fun EmptyFavBodyPrev() {
    EmptyFavBody()
}