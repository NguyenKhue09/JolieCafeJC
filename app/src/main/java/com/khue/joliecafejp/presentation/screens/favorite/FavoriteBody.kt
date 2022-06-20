package com.khue.joliecafejp.presentation.screens.favorite

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.khue.joliecafejp.ui.theme.EXTRA_LARGE_PADDING
import com.khue.joliecafejp.ui.theme.darkTextColor
import com.khue.joliecafejp.ui.theme.ralewayMedium
import com.khue.joliecafejp.ui.theme.textColor

@Composable
fun FavoriteBody(
    favoriteProducts: LazyPagingItems<FavoriteProduct>,
    onFavClicked: (String) -> Unit,
    onItemClicked: (String) -> Unit,
    showMessage: (String) -> Unit
) {
    println("FavoriteBody recomposed")
    val result = handlePagingResult(favoriteProducts = favoriteProducts, showMessage = showMessage)

    AnimatedVisibility(
        visible = result,
        exit = fadeOut(),
        enter = fadeIn()
    ) {
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
    favoriteProducts: LazyPagingItems<FavoriteProduct>,
    showMessage: (String) -> Unit
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
                showMessage(error.error.message.orEmpty())
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