package com.khue.joliecafejp.presentation.common

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.khue.joliecafejp.ui.theme.raleway
import com.khue.joliecafejp.ui.theme.ralewayMedium

@Composable
fun TextCustom(
    modifier: Modifier,
    content: String,
    color: Color,
    fontFamily: FontFamily = ralewayMedium,
    fontSize: TextUnit = 16.sp
) {

    Text(
        modifier = modifier,
        text = content,
        fontFamily = fontFamily,
        color = color,
        fontSize = fontSize,
        textAlign = TextAlign.Left,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}