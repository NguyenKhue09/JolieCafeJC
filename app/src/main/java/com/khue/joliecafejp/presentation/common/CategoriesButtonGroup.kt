package com.khue.joliecafejp.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.flowlayout.*
import com.google.accompanist.flowlayout.SizeMode.Wrap
import com.khue.joliecafejp.domain.model.CategoryButtonItem
import com.khue.joliecafejp.presentation.components.CategoryButton

@Composable
fun CategoriesButtonGroup(
    categories: List<CategoryButtonItem>,
    onButtonClicked: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        categories.chunked(4).forEach { subList ->
            FlowRow(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                mainAxisSize = Wrap,
                crossAxisAlignment = FlowCrossAxisAlignment.Center,
                mainAxisAlignment = MainAxisAlignment.SpaceBetween,
            ) {
                subList.map {
                    CategoryButton(
                        title = it.title,
                        iconId = it.iconId
                    ) {
                        onButtonClicked(it.title)
                    }
                }
            }
        }
    }
}