package com.khue.joliecafejp.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.khue.joliecafejp.ui.theme.ralewayMedium

@Composable
fun ButtonCustom(
    buttonContent: String,
    backgroundColor: Color,
    textColor: Color,
    onClick: () -> Unit,
    paddingValues: PaddingValues,
    contentPadding: PaddingValues,
    buttonElevation: ButtonElevation?
) {

    TextButton(
        modifier = Modifier
            .padding(paddingValues),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = textColor
        ),
        elevation = buttonElevation,
        contentPadding = contentPadding,
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            text = buttonContent,
            fontSize = MaterialTheme.typography.subtitle2.fontSize,
            fontFamily = ralewayMedium,
        )
    }
}