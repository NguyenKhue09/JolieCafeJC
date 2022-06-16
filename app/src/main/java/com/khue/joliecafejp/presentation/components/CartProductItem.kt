package com.khue.joliecafejp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.khue.joliecafejp.R
import com.khue.joliecafejp.domain.model.CartItem
import com.khue.joliecafejp.domain.model.Product
import com.khue.joliecafejp.presentation.common.CardCustom
import com.khue.joliecafejp.presentation.common.CartProductCount
import com.khue.joliecafejp.ui.theme.*

@Composable
fun CartProductItem(
    modifier: Modifier = Modifier,
    cartItem: CartItem,
    isSelected: Boolean = false,
    onChecked: (Boolean) -> Unit,
) {

    var quantity by rememberSaveable { mutableStateOf(cartItem.quantity) }

    CardCustom(
        paddingValues = PaddingValues(
            top = ZERO_PADDING,
            start = ZERO_PADDING,
            end = ZERO_PADDING,
            bottom = EXTRA_LARGE_PADDING
        ),
        onClick = null
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    start = ZERO_PADDING,
                    top = ZERO_PADDING,
                    end = MEDIUM_SMALL_PADDING,
                    bottom = ZERO_PADDING
                ),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Checkbox(
                checked = isSelected,
                onCheckedChange = onChecked,
                colors = CheckboxDefaults.colors(
                    uncheckedColor = MaterialTheme.colors.textColor,
                    checkedColor = MaterialTheme.colors.titleTextColor,
                    disabledColor = MaterialTheme.colors.titleTextColor
                )
            )

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .placeholder(R.drawable.image_logo)
                    .error(R.drawable.image_logo)
                    .data(cartItem.productDetail.thumbnail)
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
                    .weight(weight = 1f, fill = true)
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    modifier = modifier
                        .wrapContentSize(align = Alignment.CenterStart),
                    text = cartItem.productDetail.name,
                    fontFamily = raleway,
                    color = MaterialTheme.colors.textColor,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    modifier = modifier
                        .wrapContentSize()
                        .padding(vertical = EXTRA_EXTRA_SMALL_PADDING),
                    text = cartItem.size,
                    fontFamily = raleway,
                    color = MaterialTheme.colors.textColor,
                    fontSize = MaterialTheme.typography.caption.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    modifier = modifier.wrapContentSize(),
                    text = stringResource(id = R.string.product_price, cartItem.price.toString()),
                    fontFamily = montserratFontFamily,
                    color = MaterialTheme.colors.textColor,
                    fontSize = MaterialTheme.typography.caption.fontSize,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

            }

            CartProductCount(
                currentValue = quantity,
                onIncrease = {
                    if (quantity < 50) {
                        quantity++
                    }
                },
                onDecrease = {
                    if (quantity > 1) {
                        quantity--
                    }
                },
            )
        }
    }
}

@Preview
@Composable
fun CartProductPrev() {
    CartProductItem(
        cartItem = CartItem(
            id = "1",
            price = 100.0,
            productDetail = Product(
                id = "1",
                name = "Product 1",
                type = "Type 1",
                thumbnail = "https://images.unsplash.com/photo-1518791841217-8f162f1e1131?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80",
                originPrice = 100.0,
                avgRating = 4,
                description = "Description 1",
                isDeleted = false,
                status = "Status 1",
            ),
            productId = "1",
            quantity = 1,
            size = "Size 1",
        )
    ) {}
}