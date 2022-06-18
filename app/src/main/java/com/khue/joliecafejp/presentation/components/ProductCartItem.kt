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
import com.khue.joliecafejp.domain.model.CartItem
import com.khue.joliecafejp.domain.model.Product
import com.khue.joliecafejp.ui.theme.*
import java.text.NumberFormat
import java.util.*

@Composable
fun ProductCartItem(
    modifier: Modifier = Modifier,
    item: CartItem = CartItem(
        id = "1",
        productId = "1",
        price = 100000.0,
        quantity = 5,
        size = "M",
        productDetail = Product(
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
        )
    ),
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
            modifier = Modifier
                .height(IMAGE_PRODUCT_SIZE)
                .width(IMAGE_PRODUCT_SIZE)
                .clip(MaterialTheme.shapes.medium),
            painter = painterResource(id = R.drawable.image_logo),
            contentDescription = stringResource(R.string.profile_logo),
            contentScale = ContentScale.Crop,
        )

        Column(
            modifier = Modifier
                .padding(horizontal = SMALL_PADDING)
                .weight(weight = 1f, fill = true)
                .wrapContentHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier.wrapContentHeight(),
                text = item.productDetail.name,
                fontFamily = ralewayMedium,
                color = MaterialTheme.colors.titleTextColor,
                fontSize = MaterialTheme.typography.subtitle2.fontSize,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                modifier = Modifier.wrapContentHeight(),
                text = stringResource(R.string.quantities, item.quantity),
                fontFamily = ralewayMedium,
                color = MaterialTheme.colors.textColor,
                fontSize = MaterialTheme.typography.caption.fontSize,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                modifier = Modifier.wrapContentHeight(),
                text = stringResource(R.string.product_size, item.size),
                fontFamily = ralewayMedium,
                color = MaterialTheme.colors.textColor,
                fontSize = MaterialTheme.typography.caption.fontSize,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }

        Text(
            modifier = Modifier
                .weight(weight = 1f, fill = true)
                .wrapContentHeight(),
            text = stringResource(
                id = R.string.product_price, NumberFormat.getNumberInstance(
                    Locale.US
                ).format(item.price)
            ),
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
fun ProductCartItemPrev() {
    ProductCartItem()
}