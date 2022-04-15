package com.khue.joliecafejp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khue.joliecafejp.R
import com.khue.joliecafejp.ui.theme.*

@Composable
fun CategoryButton(
    title: String = "Juice",
    iconId: Int = R.drawable.ic_watermelon,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(SMALL_PADDING),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            modifier = Modifier
                .size(CATEGORY_BUTTON_SIZE)
                .clip(CircleShape)
                .background(color = MaterialTheme.colors.greyOpacity60Primary),
            onClick = onClick
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = iconId),
                contentDescription = title,
                tint = MaterialTheme.colors.textColor
            )
        }
        Spacer(modifier = Modifier.height(SMALL_PADDING))
        Text(
            text = title,
            fontSize = MaterialTheme.typography.caption.fontSize,
            fontFamily = raleway,
            color = MaterialTheme.colors.textColor
        )
    }
}

@Preview
@Composable
fun CategoryButtonPrev() {
    CategoryButton() {}
}