package com.khue.joliecafejp.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.khue.joliecafejp.presentation.components.CommentItem
import com.khue.joliecafejp.ui.theme.EXTRA_LARGE_PADDING

@Composable
fun CommentBottomSheet() {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(EXTRA_LARGE_PADDING),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(all = EXTRA_LARGE_PADDING)
    ) {
        repeat(20) {
            item {
                CommentItem()
            }
        }
    }
}