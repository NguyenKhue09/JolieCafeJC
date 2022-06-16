package com.khue.joliecafejp.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khue.joliecafejp.R
import com.khue.joliecafejp.ui.theme.*

@Composable
fun CartProductCount(
    currentValue: Int = 1,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Row(
        modifier = Modifier.background(
            color = MaterialTheme.colors.greyOpacity60Primary,
            shape = MaterialTheme.shapes.medium
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier.background(
                color = MaterialTheme.colors.textColor,
                shape = MaterialTheme.shapes.small
            ).size(24.dp),
            onClick = onDecrease,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_horizontal_rule),
                contentDescription = "Decrease",
                tint = MaterialTheme.colors.greyPrimary
            )
        }
        Spacer(modifier = Modifier.width(SMALL_PADDING))
        Text(
            text = currentValue.toString(),
            color = MaterialTheme.colors.textColor,
            fontFamily = montserratFontFamily,
            fontSize =  MaterialTheme.typography.body1.fontSize,
        )
        Spacer(modifier = Modifier.width(SMALL_PADDING))
        IconButton(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colors.textColor,
                    shape = MaterialTheme.shapes.small
                )
                .size(24.dp),
            onClick = onIncrease,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "Increase",
                tint = MaterialTheme.colors.greyPrimary
            )
        }
    }
}

@Preview
@Composable
fun CartProductCountPrev() {
    CartProductCount(
        onIncrease = { /*TODO*/ },
        onDecrease = { /*TODO*/ }
    )
}