package com.khue.joliecafejp.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.khue.joliecafejp.ui.theme.EXTRA_LARGE_PADDING
import com.khue.joliecafejp.ui.theme.Shapes
import com.khue.joliecafejp.ui.theme.greyOpacity60Primary

@Composable
fun CardCustom(
    modifier: Modifier = Modifier,
    shape: CornerBasedShape = MaterialTheme.shapes.medium,
    paddingValues: PaddingValues = PaddingValues(
        top = EXTRA_LARGE_PADDING,
        start = EXTRA_LARGE_PADDING,
        end = EXTRA_LARGE_PADDING,
        bottom = 0.dp
    ),
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = modifier
            .padding(paddingValues)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onClick()
            },
        shape = shape,
        backgroundColor = MaterialTheme.colors.greyOpacity60Primary
    ) {
        content()
    }
}