package com.khue.joliecafejp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.khue.joliecafejp.R
import com.khue.joliecafejp.presentation.common.CustomImage
import com.khue.joliecafejp.presentation.common.TextCustom
import com.khue.joliecafejp.ui.theme.*

@Composable
fun HomeTopBar(
    userImage: String = "",
    userName: String,
    userCoins: Int,
    onNotificationClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CustomImage(
            paddingValues = PaddingValues(
                horizontal = EXTRA_LARGE_PADDING,
                vertical = LARGE_PADDING
            ),
            height = IMAGE_USER_HOME_SIZE,
            width = IMAGE_USER_HOME_SIZE,
            image = userImage
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            TextCustom(
                content = stringResource(R.string.user_name, userName),
                modifier = Modifier,
                color = MaterialTheme.colors.textColor2,
                fontFamily = ralewayMedium
            )
            Text(
                text = stringResource(R.string.user_coins, userCoins),
                color = MaterialTheme.colors.textColor,
                fontFamily = montserratFontFamily,
                fontSize = MaterialTheme.typography.caption.fontSize,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        IconButton(
            onClick = onNotificationClick,
            modifier = Modifier.padding(end = SMALL_PADDING)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_notification),
                contentDescription = stringResource(R.string.notifications),
                tint = MaterialTheme.colors.textColor
            )
        }
    }
}

@Preview
@Composable
fun HomeTopBarPrev() {
    HomeTopBar(userName = "Khue", userCoins = 300) {
    }
}