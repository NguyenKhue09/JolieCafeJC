package com.khue.joliecafejp.presentation.screens.favorite

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.khue.joliecafejp.presentation.components.FavoriteItem
import com.khue.joliecafejp.ui.theme.EXTRA_LARGE_PADDING

@Composable
fun FavoriteBody(
    title: String,
) {

    LazyColumn() {
        repeat(10) {
            item {
                FavoriteItem()
            }
        }
        item {
            Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
        }
    }
}