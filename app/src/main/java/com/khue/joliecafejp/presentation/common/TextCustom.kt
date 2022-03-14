package com.khue.joliecafejp.presentation.common

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.khue.joliecafejp.ui.theme.raleway

@Composable
fun TextCustom(content: String, modifier: Modifier, color: Color) {

    Text(
        modifier = modifier,
        text = content,
        fontFamily = raleway,
        color = color,
        fontSize = 16.sp,
        textAlign = TextAlign.Left,
    )
}