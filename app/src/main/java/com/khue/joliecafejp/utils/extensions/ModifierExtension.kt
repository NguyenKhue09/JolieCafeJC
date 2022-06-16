package com.khue.joliecafejp.utils.extensions

import androidx.compose.foundation.border
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.khue.joliecafejp.ui.theme.CARD_BORDER_WIDTH
import com.khue.joliecafejp.ui.theme.textColor2

fun Modifier.haveBorder(haveBorder: Boolean) = composed {
    if (haveBorder) {
        this.border(
            width = CARD_BORDER_WIDTH,
            color = MaterialTheme.colors.textColor2,
            shape = MaterialTheme.shapes.medium
        )
    } else {
        this
    }
}