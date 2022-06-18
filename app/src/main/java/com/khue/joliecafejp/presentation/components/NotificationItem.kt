package com.khue.joliecafejp.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.khue.joliecafejp.R
import com.khue.joliecafejp.domain.model.Notification
import com.khue.joliecafejp.presentation.common.CardCustom
import com.khue.joliecafejp.ui.theme.*
import com.khue.joliecafejp.utils.extensions.formatTo
import com.khue.joliecafejp.utils.extensions.toDate

@Composable
fun NotificationItem(
    notification: Notification
) {
    CardCustom(
        onClick = null,
        paddingValues = PaddingValues(all = ZERO_PADDING)
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
                    text = notification.title,
                    color = MaterialTheme.colors.textColor2,
                    fontFamily = montserratFontFamily,
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = notification.createdAt.toDate()!!.formatTo(),
                    color = MaterialTheme.colors.textColor,
                    fontFamily = montserratFontFamily,
                    fontSize = MaterialTheme.typography.caption.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(MEDIUM_SMALL_PADDING))
            Text(
                text = notification.message,
                color = MaterialTheme.colors.textColor,
                fontFamily = ralewayMedium,
                fontSize = MaterialTheme.typography.body2.fontSize
            )
            AnimatedVisibility(visible = !notification.image.isNullOrEmpty()) {
                Column(Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(MEDIUM_SMALL_PADDING))
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .placeholder(R.drawable.image_logo)
                            .error(R.drawable.image_logo)
                            .data(notification.image)
                            .crossfade(true)
                            .build(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clip(MaterialTheme.shapes.medium),
                        contentDescription = stringResource(R.string.notification_image),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun NotificationItemPrev() {
    NotificationItem(
        notification = Notification(
            id = " 1",
            title = "Order #0326",
            createdAt = "2022-06-18T04:30:42.143Z",
            updatedAt = "2022-06-18T04:30:42.143Z",
            message = "Your order has been arrived!",
            type = "COMMON",
            image = "https://images.unsplash.com/photo-1518791841217-8f162f1e1131?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=800&q=60"
        )
    )
}