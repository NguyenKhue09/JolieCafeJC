package com.khue.joliecafejp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.khue.joliecafejp.presentation.common.CardCustom
import com.khue.joliecafejp.ui.theme.*

@Composable
fun NotificationItem(
    title: String = "Order #0326",
    time: String = "22/02/2022",
    content: String = "Your order has been arrived!"
) {
    CardCustom(
        onClick = {},
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = MEDIUM_SMALL_PADDING),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = title,
                    color = MaterialTheme.colors.textColor,
                    fontFamily = montserratFontFamily,
                    fontSize = MaterialTheme.typography.caption.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = time,
                    color = MaterialTheme.colors.textColor,
                    fontFamily = montserratFontFamily,
                    fontSize = MaterialTheme.typography.caption.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(MEDIUM_SMALL_PADDING))
            Text(
                text = content,
                color = MaterialTheme.colors.textColor,
                fontFamily = ralewayMedium,
                fontSize = MaterialTheme.typography.subtitle2.fontSize
            )
        }
    }
}

@Preview
@Composable
fun NotificationItemPrev() {
    NotificationItem()
}