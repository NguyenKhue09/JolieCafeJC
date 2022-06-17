package com.khue.joliecafejp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.khue.joliecafejp.R
import com.khue.joliecafejp.domain.model.FavoriteProduct
import com.khue.joliecafejp.presentation.common.CardCustom
import com.khue.joliecafejp.ui.theme.*
import java.text.NumberFormat
import java.util.*

@Composable
fun FavoriteItem(
    modifier: Modifier = Modifier,
    productFav: FavoriteProduct,
    onFavClick: () -> Unit,
    onClick: () -> Unit = {}
) {

    CardCustom(
        paddingValues = PaddingValues(
            top = EXTRA_LARGE_PADDING,
            start = 0.dp,
            end = 0.dp,
            bottom = 0.dp
        ),
        onClick = onClick
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    all = MEDIUM_SMALL_PADDING
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .placeholder(R.drawable.image_logo)
                    .error(R.drawable.image_logo)
                    .data(productFav.product.thumbnail)
                    .crossfade(true)
                    .build(),
                modifier = modifier
                    .height(IMAGE_PRODUCT_SIZE)
                    .width(IMAGE_PRODUCT_SIZE)
                    .clip(MaterialTheme.shapes.medium),
                contentDescription = stringResource(R.string.profile_logo),
                contentScale = ContentScale.Crop,
            )

            Column(
                modifier = modifier
                    .padding(horizontal = SMALL_PADDING)
                    .weight(weight = 1f, fill = false)
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        modifier = modifier
                            .wrapContentSize(align = Alignment.CenterStart)
                            .weight(1f, false),
                        text = productFav.product.name,
                        fontFamily = raleway,
                        color = MaterialTheme.colors.textColor,
                        fontSize = MaterialTheme.typography.subtitle1.fontSize,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        modifier = Modifier.padding(horizontal = SMALL_PADDING),
                        text = "•",
                        fontFamily = raleway,
                        color = MaterialTheme.colors.textColor,
                        fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    )

                    Text(
                        modifier = modifier
                            .wrapContentSize(align = Alignment.CenterStart),
                        text = productFav.product.avgRating.toString(),
                        fontFamily = montserratFontFamily,
                        color = MaterialTheme.colors.textColor,
                        fontSize = MaterialTheme.typography.subtitle1.fontSize,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )

                    Spacer(modifier = Modifier.width(EXTRA_EXTRA_SMALL_PADDING))

                    Icon(
                        modifier = modifier
                            .size(15.dp)
                            .wrapContentSize(align = Alignment.CenterStart),
                        painter = painterResource(id = R.drawable.ic_favorite),
                        contentDescription = stringResource(id = R.string.favorite),
                        tint = MaterialTheme.colors.textColor
                    )
                }

                Text(
                    modifier = modifier.wrapContentSize(),
                    text = stringResource(
                        id = R.string.product_price, NumberFormat.getNumberInstance(
                            Locale.US
                        ).format(productFav.product.originPrice)
                    ),
                    fontFamily = montserratFontFamily,
                    color = MaterialTheme.colors.textColor,
                    fontSize = MaterialTheme.typography.caption.fontSize,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

            }

            IconButton(
                onClick = onFavClick,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_heart_fill),
                    contentDescription = stringResource(id = R.string.favorite),
                    tint = MaterialTheme.colors.titleTextColor
                )
            }
        }
    }
}

@Preview
@Composable
fun FavoriteItemPrev() {
    //FavoriteItem()
}