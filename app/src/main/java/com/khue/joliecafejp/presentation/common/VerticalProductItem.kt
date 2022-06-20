package com.khue.joliecafejp.presentation.common

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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.khue.joliecafejp.R
import com.khue.joliecafejp.domain.model.Product
import com.khue.joliecafejp.ui.theme.*
import java.text.NumberFormat
import java.util.*

@Composable
fun VerticalProductItem(
    product: Product,
    isFav: Boolean,
    onItemClicked: (String) -> Unit,
    onFavClicked: (String) -> Unit
) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val maxWith = (screenWidth/2) - EXTRA_LARGE_PADDING * 2


    Column(
        modifier = Modifier
            .clip(shape = MaterialTheme.shapes.large)
            .clickable {
                onItemClicked(product.id)
            }
            .background(color = MaterialTheme.colors.greyOpacity60Primary)
            .padding(all = SMALL_PADDING)
            .width(intrinsicSize = IntrinsicSize.Min),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .placeholder(R.drawable.image_logo)
                .error(R.drawable.image_logo)
                .data(product.thumbnail)
                .crossfade(true)
                .build(),
            modifier = Modifier
                .height(120.dp)
                .width(maxWith)
                .clip(MaterialTheme.shapes.medium),
            contentDescription = stringResource(R.string.profile_logo),
            contentScale = ContentScale.Crop,
        )

        Spacer(modifier = Modifier.height(SMALL_PADDING))
        Row(
            modifier = Modifier
                .onGloballyPositioned {
                    //maxWith = (it.size.width / density).dp
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = product.name,
                fontSize = MaterialTheme.typography.subtitle2.fontSize,
                fontFamily = ralewayMedium,
                color = MaterialTheme.colors.textColor,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            IconButton(
                onClick = {
                    onFavClicked(product.id)
                },
            ) {
                Icon(
                    painter = painterResource(id = if (isFav) R.drawable.ic_heart_fill else R.drawable.ic_favorite),
                    contentDescription = stringResource(id = R.string.favorite),
                    tint = MaterialTheme.colors.titleTextColor
                )
            }
        }
        Text(
            text = product.type,
            fontSize = MaterialTheme.typography.caption.fontSize,
            fontFamily = ralewayMedium,
            color = MaterialTheme.colors.textColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(SMALL_PADDING))
        Text(
            text = stringResource(id = R.string.product_price, NumberFormat.getNumberInstance(Locale.US).format(product.originPrice)),
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
        product = Product(
            discountPercent = 0,
            startDateDiscount = "2022-06-09T03:43:49.731Z",
            endDateDiscount = "2022-06-09T03:43:49.731Z",
            id = "6267f76e02095fbefdd3cbae",
            name = "Molasses",
            status = "Available",
            description = "Tea, sugar cane",
            thumbnail = "https://firebasestorage.googleapis.com/v0/b/joliecafe-799f7.appspot.com/o/molasses.jpg?alt=media&token=1d2f1aee-31bb-4307-8e49-66b0134b628e",
            comments = emptyList(),
            originPrice = 46000.0,
            avgRating = 3,
            isDeleted = false,
            type = "Tea",
            updatedAt = null,
            createdAt = null
        ),
        isFav = true,
        onFavClicked = {},
        onItemClicked = {}
    )
}