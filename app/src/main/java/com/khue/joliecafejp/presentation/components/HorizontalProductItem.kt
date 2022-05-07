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
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.khue.joliecafejp.R
import com.khue.joliecafejp.presentation.common.CardCustom
import com.khue.joliecafejp.ui.theme.*

@Composable
fun HorizontalProductItem(
    modifier: Modifier = Modifier,
    name: String = "",
    type: String = "",
    favorites: Int = 0,
    price: Double = 0.0,
    image: String = "",
    onAdd: () -> Unit = {},
    onClick: () -> Unit = {}
) {

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .placeholder(R.drawable.image_logo)
            .error(R.drawable.image_logo)
            .data(image)
            .crossfade(200)
            .build()
    )
    
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

            Image(
                modifier = modifier
                    .height(IMAGE_PRODUCT_SIZE)
                    .width(IMAGE_PRODUCT_SIZE)
                    .clip(MaterialTheme.shapes.medium),
                painter = painter,
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
                        text = name,
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
                        text = favorites.toString(),
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
                    text = type,
                    fontFamily = raleway,
                    color = MaterialTheme.colors.textColor,
                    fontSize = MaterialTheme.typography.caption.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    modifier = modifier.wrapContentSize(),
                    text = stringResource(id = R.string.product_price, price.toString()),
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
    HorizontalProductItem()
}