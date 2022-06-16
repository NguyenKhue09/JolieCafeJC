package com.khue.joliecafejp.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.khue.joliecafejp.ui.theme.EXTRA_LARGE_PADDING
import com.khue.joliecafejp.ui.theme.greyOpacity60Primary
import com.khue.joliecafejp.utils.extensions.haveBorder

@Composable
fun CardCustom(
    modifier: Modifier = Modifier,
    haveBorder: Boolean = false,
    shape: CornerBasedShape = MaterialTheme.shapes.medium,
    paddingValues: PaddingValues = PaddingValues(
        top = EXTRA_LARGE_PADDING,
        start = EXTRA_LARGE_PADDING,
        end = EXTRA_LARGE_PADDING,
        bottom = 0.dp
    ),
    backgroundColor: Color = MaterialTheme.colors.greyOpacity60Primary,
    onClick: (() -> Unit)?,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = modifier
            .padding(paddingValues)
            .clip(shape = shape)
            .clickable(
                enabled = onClick != null,
            ) {
                if (onClick != null) {
                    onClick()
                }
            }.haveBorder(haveBorder),
        shape = shape,
        backgroundColor = backgroundColor,
        elevation = 0.dp
    ) {
        content()
    }
}
