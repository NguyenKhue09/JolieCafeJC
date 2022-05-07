package com.khue.joliecafejp.presentation.screens.favorite

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.khue.joliecafejp.domain.model.FavoriteProduct
import com.khue.joliecafejp.presentation.components.FavoriteItem
import com.khue.joliecafejp.presentation.viewmodels.FavoriteViewModel
import com.khue.joliecafejp.ui.theme.EXTRA_LARGE_PADDING
import kotlinx.coroutines.flow.Flow

@Composable
fun FavoriteBody(
    favoriteProducts: LazyPagingItems<FavoriteProduct>
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
            product?.let {
                FavoriteItem(
                    name = product.product.name,
                    favorites =  product.product.avgRating,
                    price =  product.product.originPrice,
                    image = product.product.thumbnail
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
        }
    }
}