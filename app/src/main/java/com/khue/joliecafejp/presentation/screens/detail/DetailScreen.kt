package com.khue.joliecafejp.presentation.screens.detail

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.khue.joliecafejp.R
import com.khue.joliecafejp.domain.model.Product
import com.khue.joliecafejp.presentation.common.ButtonCustom
import com.khue.joliecafejp.presentation.common.CommentBottomSheet
import com.khue.joliecafejp.presentation.common.VerticalProductItem
import com.khue.joliecafejp.presentation.components.CommentItem
import com.khue.joliecafejp.presentation.viewmodels.ProductDetailViewModel
import com.khue.joliecafejp.ui.theme.*
import com.khue.joliecafejp.utils.ApiResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailScreen(
    navController: NavController,
    productDetailViewModel: ProductDetailViewModel = hiltViewModel(),
    productId: String?
) {

    val state = rememberLazyListState()
    val context = LocalContext.current

    val (selectedRating, onSelectedRating) = remember {
        mutableStateOf(R.string.all)
    }

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val coroutineScope = rememberCoroutineScope()

    val userToken by productDetailViewModel.userToken.collectAsState(initial = "")
    val getProductDetailResponse by productDetailViewModel.getProductDetailResponse.collectAsState()

    var product by remember {
        mutableStateOf<Product?>(null)
    }

    var isLoading by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(key1 = getProductDetailResponse) {
        when(getProductDetailResponse) {
            is ApiResult.Loading -> {
                isLoading = true
            }
            is ApiResult.Error -> {
                isLoading = false
                Toast.makeText(context, getProductDetailResponse.message ?: "Unknown error!", Toast.LENGTH_SHORT).show()
            }
            is ApiResult.Success -> {
                isLoading = false
                product = getProductDetailResponse.data
            }
            else -> {
                isLoading = false
            }
        }
    }

    LaunchedEffect(key1 = true) {
        productDetailViewModel.getProductDetail(token = userToken, productId = productId!!)
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        modifier = Modifier.fillMaxSize(),
        sheetContent = {
            CommentBottomSheet()
        }
    ) {
        Scaffold(
            backgroundColor = MaterialTheme.colors.greyPrimary,
        ) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
            ) {

                AnimatedVisibility(visible = product != null, modifier = Modifier.fillMaxSize(),) {
                    product?.let { productDetail ->
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize(),
                            state = state
                        ) {

                            item {
                                ProductImageSection(
                                    state = state,
                                    image = productDetail.thumbnail
                                )
                            }

                            item {
                                ProductNameSection(
                                    name = productDetail.name
                                ) {}
                            }

                            item {
                                PriceAndRatingSection(
                                    price = productDetail.originPrice,
                                    rating = productDetail.avgRating
                                )
                            }

                            item {
                                DescriptionSection()
                            }

                            item {
                                RatingSection(
                                    selectedRating = selectedRating,
                                    onSelectedRating = onSelectedRating,
                                    coroutineScope = coroutineScope,
                                    bottomSheetState = bottomSheetState
                                )
                            }

                            item {
                                CommentSection()
                            }

                            item {
                                MoreProductSection()
                            }
                        }
                    }
                }

                DetailScreenTopBar(navController = navController)

                AnimatedVisibility(visible = product != null, modifier = Modifier.align(Alignment.BottomCenter),) {
                    BottomButtonAction(
                        modifier = Modifier,
                        onAddToCardClicked = {},
                        onButNowClicked = {}
                    )
                }

                AnimatedVisibility(visible = isLoading, modifier = Modifier.align(Alignment.Center)) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.textColor2
                    )
                }

            }
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
                .shadow(
                    elevation = 4.dp,
                    shape = CircleShape
                )
                .clip(shape = CircleShape)
                .background(MaterialTheme.colors.textColor),
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
fun ProductImageSection(
    state: LazyListState,
    image: String
) {
    var scrolledY = 0f
    var previousOffset = 0

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(image)
            .crossfade(200)
            .placeholder(R.drawable.image_logo)
            .error(R.drawable.image_logo)
            .build()
    )

    Image(
        modifier = Modifier
            .graphicsLayer {
                scrolledY += state.firstVisibleItemScrollOffset - previousOffset
                translationY = scrolledY * 0.1f
                previousOffset = state.firstVisibleItemScrollOffset
            }
            .fillMaxWidth()
            .height(PRODUCT_IMAGE_HEIGHT),
        contentScale = ContentScale.Crop,
        painter = painter,
        contentDescription = ""
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
    price: Double = 90000.0,
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
            text = stringResource(id = R.string.product_price, price.toString()),
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
            fontWeight = FontWeight.Bold
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
        Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
        Text(
            modifier = Modifier.padding(start = EXTRA_LARGE_PADDING),
            text = stringResource(R.string.more_products),
            fontFamily = raleway,
            color = MaterialTheme.colors.textColor,
            fontSize = MaterialTheme.typography.subtitle2.fontSize,
            fontWeight = FontWeight.Bold
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
        Spacer(modifier = Modifier.height(BOTTOM_NAV_HEIGHT))
        Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RatingSection(
    avgRating: Int = 5,
    selectedRating: Int,
    onSelectedRating: (Int) -> Unit,
    coroutineScope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = EXTRA_LARGE_PADDING),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.review),
                fontFamily = raleway,
                color = MaterialTheme.colors.textColor,
                fontSize = MaterialTheme.typography.subtitle2.fontSize,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.clickable {
                    coroutineScope.launch { bottomSheetState.show() }
                },
                text = "Xem chi tiết...",
                fontFamily = raleway,
                color = MaterialTheme.colors.textColor2,
                fontSize = MaterialTheme.typography.caption.fontSize,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(SMALL_PADDING))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(REVIEW_ICON_SIZE),
                    painter = painterResource(id = R.drawable.ic_heart_fill),
                    contentDescription = stringResource(id = R.string.review),
                    tint = MaterialTheme.colors.textColor2
                )
                Spacer(modifier = Modifier.height(SMALL_PADDING))
                Text(
                    text = stringResource(R.string.avgRating, avgRating),
                    fontFamily = montserratFontFamily,
                    color = MaterialTheme.colors.textColor,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                )
            }
            Spacer(modifier = Modifier.width(EXTRA_LARGE_PADDING))
            FlowRow(
                modifier = Modifier.weight(1f, fill = true),
                mainAxisSpacing = MEDIUM_PADDING,
                crossAxisAlignment = FlowCrossAxisAlignment.Center
            ) {
                ReviewFilterButton(
                    text = stringResource(id = R.string.all),
                    icon = null,
                    isSelected = selectedRating == R.string.all
                ) {
                    onSelectedRating(R.string.all)
                }
                repeat(6) {
                    ReviewFilterButton(
                        text = (6 - (it + 1)).toString(),
                        icon = R.drawable.ic_favorite,
                        isSelected = selectedRating == (6 - (it + 1))
                    ) {
                        onSelectedRating((6 - (it + 1)))
                    }
                }
            }
        }
    }
}

@Composable
fun ReviewFilterButton(
    text: String,
    icon: Int?,
    isSelected: Boolean,
    onClicked: () -> Unit,
) {
    TextButton(
        onClick = onClicked,
        contentPadding = PaddingValues(
            horizontal = MEDIUM_PADDING,
            vertical = EXTRA_EXTRA_SMALL_PADDING
        ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isSelected) MaterialTheme.colors.textColor2 else MaterialTheme.colors.greyOpacity60Primary
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            text = text,
            fontFamily = montserratFontFamily,
            color = MaterialTheme.colors.textColor,
            fontSize = MaterialTheme.typography.subtitle2.fontSize,
        )
        icon?.let {
            Icon(
                modifier = Modifier.size(PRODUCT_RATING_SIZE),
                painter = painterResource(id = icon),
                contentDescription = stringResource(id = R.string.review),
                tint = MaterialTheme.colors.textColor
            )
        }
    }
}

@Composable
fun CommentSection() {
    Column(
        modifier = Modifier.padding(horizontal = EXTRA_LARGE_PADDING),
        verticalArrangement = Arrangement.spacedBy(EXTRA_LARGE_PADDING),
    ) {
        repeat(3) {
                CommentItem()
        }
    }
}

@Composable
fun BottomButtonAction(
    modifier: Modifier,
    onAddToCardClicked: () -> Unit,
    onButNowClicked: () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        MaterialTheme.colors.greyPrimary,
                        MaterialTheme.colors.greyPrimary,
                    ),
                )
            )
            .padding(vertical = EXTRA_LARGE_PADDING),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Spacer(modifier = Modifier.width(EXTRA_LARGE_PADDING))
        ButtonCustom(
            modifier = Modifier.weight(1f),
            buttonContent = stringResource(id = R.string.add_to_cart),
            backgroundColor = MaterialTheme.colors.textColor,
            textColor = MaterialTheme.colors.greyPrimary,
            onClick = onAddToCardClicked,
            paddingValues = PaddingValues(ZERO_PADDING),
            contentPadding = PaddingValues(
                horizontal = EXTRA_LARGE_PADDING,
                vertical = MEDIUM_SMALL_PADDING
            ),
            buttonElevation = null,
            shapes = RoundedCornerShape(26.dp)
        )
        Spacer(modifier = Modifier.width(EXTRA_LARGE_PADDING))
        ButtonCustom(
            modifier = Modifier.weight(1f),
            buttonContent = stringResource(R.string.buy_now),
            backgroundColor = MaterialTheme.colors.textColor2,
            textColor = MaterialTheme.colors.textColor,
            onClick = onButNowClicked,
            paddingValues = PaddingValues(ZERO_PADDING),
            contentPadding = PaddingValues(
                horizontal = EXTRA_LARGE_PADDING,
                vertical = MEDIUM_SMALL_PADDING
            ),
            buttonElevation = null,
            shapes = RoundedCornerShape(26.dp)
        )
        Spacer(modifier = Modifier.width(EXTRA_LARGE_PADDING))
    }
}

@Preview
@Composable
fun DetailScreenPrev() {
    DetailScreen(
        navController = rememberNavController(),
        productId = ""
    )
}