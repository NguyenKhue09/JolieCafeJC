package com.khue.joliecafejp.utils.extensions

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabPosition
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Dp
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

fun Modifier.haveClickable(isClickable: Boolean, onClick: (() -> Unit)?) = composed {
    if (isClickable) {
        this.clickable{
            if(onClick != null) {
                onClick()
            }
        }
    } else {
        this
    }
}

fun Modifier.customTabIndicatorOffset(
    currentTabPosition: TabPosition,
    tabWidth: Dp
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "customTabIndicatorOffset"
        value = currentTabPosition
    }
) {
    val currentTabWidth by animateDpAsState(
        targetValue = tabWidth,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
    )
    val indicatorOffset by animateDpAsState(
        targetValue = ((currentTabPosition.left + currentTabPosition.right - tabWidth) / 2),
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset(x = indicatorOffset)
        .width(currentTabWidth)
}