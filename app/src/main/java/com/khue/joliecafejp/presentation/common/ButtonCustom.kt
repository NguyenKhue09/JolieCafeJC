package com.khue.joliecafejp.presentation.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.khue.joliecafejp.ui.theme.ralewayMedium

@Composable
fun ButtonCustom(
    modifier: Modifier = Modifier,
    buttonContent: String,
    backgroundColor: Color,
    textColor: Color,
    paddingValues: PaddingValues,
    contentPadding: PaddingValues,
    buttonElevation: ButtonElevation?,
    shapes: CornerBasedShape = MaterialTheme.shapes.medium,
    onClick: () -> Unit,
) {

    TextButton(
        modifier = modifier
            .padding(paddingValues),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = textColor
        ),
        elevation = buttonElevation,
        contentPadding = contentPadding,
        shape = shapes
    ) {
        Text(
            text = buttonContent,
            fontSize = MaterialTheme.typography.subtitle2.fontSize,
            fontFamily = ralewayMedium,
        )
    }
}