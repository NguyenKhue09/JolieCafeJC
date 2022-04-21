package com.khue.joliecafejp.presentation.screens.detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.khue.joliecafejp.R
import com.khue.joliecafejp.presentation.common.TopBar
import com.khue.joliecafejp.presentation.common.VerticalProductItem
import com.khue.joliecafejp.ui.theme.*

@Composable
fun DetailScreen(
    navController: NavController
) {

    val state = rememberScrollState()
    var scrolledY = 0f
    var previousOffset = 0
    var density = LocalDensity.current.density

    Scaffold(
        backgroundColor = MaterialTheme.colors.greyPrimary,
        bottomBar = {
            BottomAppBar() {

            }
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = state)
            ) {

                ProductImageSection(state = state)

                ProductNameSection() {}

                PriceAndRatingSection()

                DescriptionSection()

                MoreProductSection()
            }
            DetailScreenTopBar(navController = navController)
        }
    }
}


@Composable
fun DetailScreenTopBar(navController: NavController) {
    TopAppBar(
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        contentPadding = PaddingValues(
            top = EXTRA_LARGE_PADDING,
            start = EXTRA_LARGE_PADDING
        )
    ) {
        IconButton(
            modifier = Modifier
                .clip(shape = CircleShape)
                .background(MaterialTheme.colors.greyOpacity60Primary),
            onClick = { navController.popBackStack() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "",
                tint = MaterialTheme.colors.greyPrimary
            )
        }
    }
}

@Composable
fun ProductImageSection(state: ScrollState) {
    Image(
        modifier = Modifier
            .graphicsLayer {
                //scrolledY += state.value - previousOffset
                translationY -= (state.value / density)
                //previousOffset = state.value
                println(state.value)
            }
            .fillMaxWidth()
            .height(PRODUCT_IMAGE_HEIGHT),
        contentScale = ContentScale.Crop,
        painter = painterResource(id = R.drawable.image_logo), contentDescription = ""
    )
}

@Composable
fun ProductNameSection(
    name: String = "Sweet Cappuccino",
    onFavClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = EXTRA_LARGE_PADDING, start = EXTRA_LARGE_PADDING, end = SMALL_PADDING),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.weight(1f, fill = true),
            text = name,
            color = MaterialTheme.colors.textColor2,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontFamily = raleway,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.h6.fontSize
        )
        IconButton(onClick = onFavClicked) {
            Icon(
                painter = painterResource(id = R.drawable.ic_favorite),
                contentDescription = stringResource(
                    id = R.string.favorite
                ),
                tint = MaterialTheme.colors.textColor
            )
        }
    }
}

@Composable
fun PriceAndRatingSection(
    price: Int = 90000,
    rating: Int = 5
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = EXTRA_LARGE_PADDING),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            modifier = Modifier.weight(1f, fill = false),
            text = stringResource(id = R.string.product_price, price),
            color = MaterialTheme.colors.textColor,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontFamily = montserratFontFamily,
            fontSize = MaterialTheme.typography.subtitle2.fontSize
        )
        Text(
            modifier = Modifier.padding(horizontal = EXTRA_EXTRA_SMALL_PADDING),
            text = "•",
            fontFamily = raleway,
            color = MaterialTheme.colors.textColor,
            fontSize = MaterialTheme.typography.subtitle2.fontSize,
        )
        Text(
            text = rating.toString(),
            color = MaterialTheme.colors.textColor,
            fontFamily = montserratFontFamily,
            fontSize = MaterialTheme.typography.subtitle2.fontSize,
        )
        repeat(rating) {
            Spacer(modifier = Modifier.width(EXTRA_EXTRA_SMALL_PADDING))
            Icon(
                modifier = Modifier.size(PRODUCT_RATING_SIZE),
                painter = painterResource(id = R.drawable.ic_favorite),
                contentDescription = stringResource(id = R.string.favorite),
                tint = MaterialTheme.colors.textColor
            )
        }
    }
}

@Composable
fun DescriptionSection(
    description: String = "Typically composed of a single espresso shot and hot milk, with the surface topped with foamed milk. The top third of the drink consists of milk foam; this foam can be decorated with artistic drawings made..."
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = EXTRA_LARGE_PADDING),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.description),
            fontFamily = raleway,
            color = MaterialTheme.colors.textColor,
            fontSize = MaterialTheme.typography.subtitle2.fontSize,
        )
        Spacer(modifier = Modifier.height(SMALL_PADDING))
        Text(
            text = description,
            fontFamily = raleway,
            color = MaterialTheme.colors.textColor,
            fontSize = MaterialTheme.typography.caption.fontSize,
            maxLines = 5,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun MoreProductSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            modifier = Modifier.padding(start = EXTRA_LARGE_PADDING),
            text = stringResource(R.string.more_products),
            fontFamily = raleway,
            color = MaterialTheme.colors.textColor,
            fontSize = MaterialTheme.typography.subtitle2.fontSize,
        )
        LazyRow(
            contentPadding = PaddingValues(
                start = EXTRA_LARGE_PADDING,
                top = EXTRA_LARGE_PADDING,
                end = EXTRA_LARGE_PADDING
            ),
            horizontalArrangement = Arrangement.spacedBy(EXTRA_LARGE_PADDING)
        ) {
            repeat(10) {
                item {
                    VerticalProductItem(onItemClicked = {}, onFavClicked = {})
                }
            }
        }
    }
}


@Preview
@Composable
fun DetailScreenPrev() {
    DetailScreen(navController = rememberNavController())
}