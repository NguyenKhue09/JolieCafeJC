package com.khue.joliecafejp.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.*
import com.google.accompanist.flowlayout.SizeMode.Wrap
import com.khue.joliecafejp.domain.model.CategoryButtonItem
import com.khue.joliecafejp.presentation.components.CategoryButton

@Composable
fun CategoriesButtonGroup(
    categories: List<CategoryButtonItem>,
    selectedButton: String?,
    onButtonClicked: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
            LazyVerticalGrid(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .height(170.dp),
                columns = GridCells.Fixed(4),
                contentPadding = PaddingValues(0.dp)
            ) {
                items(categories) {
                    CategoryButton(
                        title = it.title,
                        iconId = it.iconId,
                        isSelected = selectedButton == it.title
                    ) {
                        onButtonClicked(it.title)
                    }
                }
            }
//        categories.chunked(4).forEach { subList ->
//            FlowRow(
//                modifier = Modifier
//                    .wrapContentHeight()
//                    .fillMaxWidth(),
//                mainAxisSize = Wrap,
//                crossAxisAlignment = FlowCrossAxisAlignment.Center,
//                mainAxisAlignment = MainAxisAlignment.SpaceBetween,
//            ) {
//                subList.map {
//                    CategoryButton(
//                        title = it.title,
//                        iconId = it.iconId,
//                        isSelected = selectedButton == it.title
//                    ) {
//                        onButtonClicked(it.title)
//                    }
//                }
//            }
//        }
    }
}