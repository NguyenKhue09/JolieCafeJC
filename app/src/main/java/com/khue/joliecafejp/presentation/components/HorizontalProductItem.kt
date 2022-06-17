package com.khue.joliecafejp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
import com.khue.joliecafejp.domain.model.Product
import com.khue.joliecafejp.presentation.common.CardCustom
import com.khue.joliecafejp.ui.theme.*
import java.text.NumberFormat
import java.util.*

@Composable
fun HorizontalProductItem(
    modifier: Modifier = Modifier,
    product: Product,
    onAdd: () -> Unit = {},
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
                    .data(product.thumbnail)
                    .crossfade(200)
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
                        text = product.name,
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
                        text = product.avgRating.toString(),
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
                    modifier = modifier
                        .wrapContentSize()
                        .padding(vertical = EXTRA_EXTRA_SMALL_PADDING),
                    text = product.type,
                    fontFamily = raleway,
                    color = MaterialTheme.colors.textColor,
                    fontSize = MaterialTheme.typography.caption.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    modifier = modifier.wrapContentSize(),
                    text = stringResource(
                        id = R.string.product_price, NumberFormat.getNumberInstance(
                            Locale.US
                        ).format(product.originPrice)
                    ),
                    fontFamily = montserratFontFamily,
                    color = MaterialTheme.colors.textColor,
                    fontSize = MaterialTheme.typography.caption.fontSize,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

            }

            Button(
                onClick = onAdd,
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colors.textColor,
                    backgroundColor = MaterialTheme.colors.textColor2
                ),
                shape = MaterialTheme.shapes.medium,
                contentPadding = PaddingValues(all = SMALL_PADDING),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_cart),
                    contentDescription = stringResource(id = R.string.favorite),
                    tint = MaterialTheme.colors.textColor
                )
                Spacer(modifier = Modifier.width(SMALL_PADDING))
                Text(
                    text = stringResource(R.string.add),
                    fontSize = MaterialTheme.typography.subtitle2.fontSize,
                    fontFamily = ralewayMedium,
                )
            }
        }
    }
}

@Preview
@Composable
fun ProductItemPrev() {
    HorizontalProductItem(
        product = Product(
            id = "1",
            name = "Product 1",
            type = "Type 1",
            thumbnail = "https://images.unsplash.com/photo-1518791841217-8f162f1e1131?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80",
            originPrice = 1000.0,
            avgRating = 4,
            description = "Description 1",
            isDeleted = false,
            status = "Status 1"
        )
    )
}