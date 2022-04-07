package com.khue.joliecafejp.presentation.screens.favorite

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.khue.joliecafejp.presentation.components.FavoriteItem

@Composable
fun FavoriteBody(
    title: String
) {

    LazyColumn() {
        repeat(10) {
            item {
                FavoriteItem()
            }
        }
    }
}