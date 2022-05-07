package com.khue.joliecafejp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.khue.joliecafejp.R
import com.khue.joliecafejp.ui.theme.*

@Composable
fun ProductOrderHistoryItem(
    modifier: Modifier = Modifier,
    name: String = "Black Tea",
    price: Int = 90000,
    quantities: Int = 5,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = MEDIUM_SMALL_PADDING,
            )
            .height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            modifier = modifier
                .height(IMAGE_PRODUCT_SIZE)
                .width(IMAGE_PRODUCT_SIZE)
                .clip(MaterialTheme.shapes.medium),
            painter = painterResource(id = R.drawable.image_logo),
            contentDescription = stringResource(R.string.profile_logo),
            contentScale = ContentScale.Crop,
        )

        Column(
            modifier = modifier
                .padding(horizontal = SMALL_PADDING)
                .weight(weight = 1f, fill = true)
                .wrapContentHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = modifier.wrapContentHeight(),
                text = "$name",
                fontFamily = raleway,
                color = MaterialTheme.colors.titleTextColor,
                fontSize = MaterialTheme.typography.subtitle2.fontSize,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                modifier = modifier.wrapContentHeight(),
                text = "Quantities: $quantities",
                fontFamily = raleway,
                color = MaterialTheme.colors.textColor,
                fontSize = MaterialTheme.typography.caption.fontSize,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }

        Text(
            modifier = modifier
                .padding(end = SMALL_PADDING)
                .weight(weight = 1f, fill = true)
                .wrapContentHeight(),
            text = "$price VND",
            fontFamily = montserratFontFamily,
            color = MaterialTheme.colors.textColor,
            fontSize = MaterialTheme.typography.subtitle2.fontSize,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.End
        )
    }
}

@Preview()
@Composable
fun ProductOrderHistoryItemPrev() {
    ProductOrderHistoryItem()
}