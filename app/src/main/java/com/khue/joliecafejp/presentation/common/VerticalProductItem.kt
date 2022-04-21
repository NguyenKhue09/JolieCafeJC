package com.khue.joliecafejp.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khue.joliecafejp.R
import com.khue.joliecafejp.ui.theme.*

@Composable
fun VerticalProductItem(
    onItemClicked: (String) -> Unit,
    onFavClicked: (String) -> Unit
) {

    val density = LocalDensity.current.density
    var maxWith by remember {
        mutableStateOf(0.dp)
    }

    Column(
        modifier = Modifier
            .clip(shape = MaterialTheme.shapes.large)
            .clickable {
                onItemClicked("")
            }
            .background(color = MaterialTheme.colors.greyOpacity60Primary)
            .padding(all = SMALL_PADDING)
            .width(intrinsicSize = IntrinsicSize.Min),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Image(
            modifier = Modifier
                .height(120.dp)
                .width(maxWith)
                .clip(MaterialTheme.shapes.medium),
            painter = painterResource(id = R.drawable.image_logo),
            contentDescription = stringResource(R.string.profile_logo),
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.height(SMALL_PADDING))
        Row(
            modifier = Modifier
                .onGloballyPositioned {
                    maxWith = (it.size.width / density).dp
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Sweet Cappuccino",
                fontSize = MaterialTheme.typography.subtitle2.fontSize,
                fontFamily = raleway,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.textColor,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            IconButton(
                onClick = {
                    onFavClicked("")
                },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_favorite),
                    contentDescription = stringResource(id = R.string.favorite),
                    tint = MaterialTheme.colors.titleTextColor
                )
            }
        }
        Spacer(modifier = Modifier.height(SMALL_PADDING))
        Text(
            text = "Coffee",
            fontSize = MaterialTheme.typography.caption.fontSize,
            fontFamily = raleway,
            color = MaterialTheme.colors.textColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(SMALL_PADDING))
        Text(
            text = stringResource(id = R.string.product_price, 90000),
            fontSize = MaterialTheme.typography.caption.fontSize,
            fontFamily = montserratFontFamily,
            color = MaterialTheme.colors.textColor2,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
fun ProductItemPrev() {
    VerticalProductItem(
        onFavClicked = {},
        onItemClicked = {}
    )
}