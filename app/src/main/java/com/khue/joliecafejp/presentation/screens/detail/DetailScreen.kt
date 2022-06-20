package com.khue.joliecafejp.presentation.screens.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.khue.joliecafejp.R
import com.khue.joliecafejp.domain.model.Comment
import com.khue.joliecafejp.domain.model.Product
import com.khue.joliecafejp.domain.model.SnackBarData
import com.khue.joliecafejp.presentation.common.CommentBottomSheet
import com.khue.joliecafejp.presentation.common.ProductDetailShopNowBottomSheet
import com.khue.joliecafejp.presentation.common.SnackBar
import com.khue.joliecafejp.presentation.common.VerticalProductItem
import com.khue.joliecafejp.presentation.components.CommentItem
import com.khue.joliecafejp.presentation.viewmodels.ProductDetailViewModel
import com.khue.joliecafejp.ui.theme.*
import com.khue.joliecafejp.utils.ApiResult
import com.khue.joliecafejp.utils.Constants
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailScreen(
    navController: NavController,
    productDetailViewModel: ProductDetailViewModel = hiltViewModel(),
    productId: String?
) {


    val snackbarHostState = remember { SnackbarHostState() }
    var snackBarData by remember {
        mutableStateOf(
            SnackBarData(
                iconId = R.drawable.ic_success,
                message = "",
                snackBarState = Constants.SNACK_BAR_STATUS_SUCCESS,
            )
        )
    }

    val state = rememberLazyListState()

    val (selectedRating, onSelectedRating) = remember {
        mutableStateOf(R.string.all)
    }

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val coroutineScope = rememberCoroutineScope()

    val userToken by productDetailViewModel.userToken.collectAsState(initial = "")
    val isFav by productDetailViewModel.isFav
    val getProductDetailResponse by productDetailViewModel.getProductDetailResponse.collectAsState()
    val getProductCommentsResponse by productDetailViewModel.getCommentProductResponse.collectAsState()
    val moreProducts = productDetailViewModel.moreProductsWithFav.collectAsLazyPagingItems()

    var product by remember {
        mutableStateOf<Product?>(null)
    }

    var comments by remember {
        mutableStateOf<List<Comment>?>(null)
    }

    var isLoading by remember {
        mutableStateOf(true)
    }

    var seeMoreCommentOrNot by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(key1 = getProductDetailResponse) {
        when (getProductDetailResponse) {
            is ApiResult.Loading -> {
                isLoading = true
            }
            is ApiResult.Error -> {
                isLoading = false
                snackBarData = snackBarData.copy(
                    message = getProductDetailResponse.message ?: "Unknown error!",
                    iconId = R.drawable.ic_error,
                    snackBarState = Constants.SNACK_BAR_STATUS_ERROR
                )
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("")
                }
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

    LaunchedEffect(key1 = getProductCommentsResponse) {
        when (getProductCommentsResponse) {
            is ApiResult.Loading -> {
            }
            is ApiResult.Error -> {
                snackBarData = snackBarData.copy(
                    message = getProductCommentsResponse.message ?: "Unknown error!",
                    iconId = R.drawable.ic_error,
                    snackBarState = Constants.SNACK_BAR_STATUS_ERROR
                )
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("")
                }
            }
            is ApiResult.Success -> {
                comments = getProductCommentsResponse.data
            }
            else -> {
            }
        }
    }

    LaunchedEffect(key1 = true) {
        productDetailViewModel.removeUserFavResponse.collect { result ->
            when (result) {
                is ApiResult.NullDataSuccess -> {
                    snackBarData = snackBarData.copy(
                        message = "Remove ${product?.name} from favorite success",
                        iconId = R.drawable.ic_success,
                        snackBarState = Constants.SNACK_BAR_STATUS_SUCCESS
                    )
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("")
                    }
                }
                is ApiResult.Error -> {
                    println(result.message)
                    productDetailViewModel.setFavProductState(isFav = true)
                    productDetailViewModel.removeOrAddMoreProductToFavList(productId = product!!.id, isAdd = true)
                    snackBarData = snackBarData.copy(
                        message = "Remove ${product?.name} from favorite failed!",
                        iconId = R.drawable.ic_error,
                        snackBarState = Constants.SNACK_BAR_STATUS_ERROR
                    )
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("")
                    }
                }
                else -> {}
            }
        }
    }

    LaunchedEffect(key1 = true) {
        productDetailViewModel.addUserFavResponse.collect { result ->
            when (result) {
                is ApiResult.NullDataSuccess -> {
                    snackBarData = snackBarData.copy(
                        message = "Add ${product?.name} to favorite success",
                        iconId = R.drawable.ic_success,
                        snackBarState = Constants.SNACK_BAR_STATUS_SUCCESS
                    )
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("")
                    }
                }
                is ApiResult.Error -> {
                    println(result.message)
                    productDetailViewModel.setFavProductState(isFav = false)
                    productDetailViewModel.removeOrAddMoreProductToFavList(productId = product!!.id, isAdd = false)
                    snackBarData = snackBarData.copy(
                        message = "Add ${product?.name} to favorite failed!",
                        iconId = R.drawable.ic_error,
                        snackBarState = Constants.SNACK_BAR_STATUS_ERROR
                    )
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("")
                    }
                }
                else -> {}
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        productDetailViewModel.addProductToCartResponse.collect { result ->
            println(result)
            when (result) {
                is ApiResult.Loading -> {

                }
                is ApiResult.NullDataSuccess -> {
                    snackBarData = snackBarData.copy(
                        message = "Add ${getProductDetailResponse.data!!.name} to cart success",
                        iconId = R.drawable.ic_success,
                        snackBarState = Constants.SNACK_BAR_STATUS_SUCCESS
                    )
                    coroutineScope.launch {
                        bottomSheetState.hide()
                        snackbarHostState.showSnackbar("")
                    }
                }
                is ApiResult.Error -> {
                    snackBarData = snackBarData.copy(
                        message = "Add ${getProductDetailResponse.data!!.name} to cart failed",
                        iconId = R.drawable.ic_error,
                        snackBarState = Constants.SNACK_BAR_STATUS_ERROR
                    )
                    coroutineScope.launch {
                        bottomSheetState.hide()
                        snackbarHostState.showSnackbar("")
                    }
                }
                else -> {}
            }
        }
    }

    LaunchedEffect(key1 = true) {
        productDetailViewModel.getProductDetail(token = userToken, productId = productId!!)
        productDetailViewModel.getComment(token = userToken, productId = productId)
        productDetailViewModel.getProducts(productQuery = mapOf("type" to "All"), token = userToken)
        productDetailViewModel.getUserFavProductsId(token = userToken)
        productDetailViewModel.combineFavProductsIdWithMoreProducts()
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        modifier = Modifier.fillMaxSize(),
        sheetContent = {
            if(seeMoreCommentOrNot) {
                CommentBottomSheet(comments = comments)
            } else {
                ProductDetailShopNowBottomSheet(
                    productDetailViewModel = productDetailViewModel,
                    paddingValues = PaddingValues(bottom = EXTRA_LARGE_PADDING),
                    product = product,
                    coroutineScope = coroutineScope,
                    modalBottomSheetState = bottomSheetState,
                    onAddProductToCart = { data ->
                        productDetailViewModel.addProductToCart(
                            data = data,
                            token = userToken
                        )
                    },
                    onPurchaseProduct = {}
                )
            }
        },
        sheetShape = MaterialTheme.shapes.large.copy(
            bottomEnd = CornerSize(0),
            bottomStart = CornerSize(0)
        )
    ) {
        Scaffold(
            backgroundColor = MaterialTheme.colors.greyPrimary,
            snackbarHost = {
                SnackBar(
                    modifier = Modifier.padding(MEDIUM_PADDING),
                    snackbarHostState = snackbarHostState,
                    snackBarData = snackBarData
                )
            }
        ) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
            ) {

                AnimatedVisibility(
                    visible = getProductDetailResponse.data != null,
                    modifier = Modifier.fillMaxSize(),
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    getProductDetailResponse.data?.let { productDetail ->
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
                                    name = productDetail.name,
                                    isFav = isFav
                                ) {
                                    product = productDetail
                                    if (isFav) {
                                        productDetailViewModel.setFavProductState(isFav = false)
                                        productDetailViewModel.removeUserFavProduct(
                                            token = userToken,
                                            productId = productDetail.id
                                        )
                                    } else {
                                        productDetailViewModel.setFavProductState(isFav = true)
                                        productDetailViewModel.addUserFavProduct(
                                            token = userToken,
                                            productId = productDetail.id
                                        )
                                    }
                                }
                            }

                            item {
                                PriceAndRatingSection(
                                    price = productDetail.originPrice,
                                    rating = productDetail.avgRating
                                )
                            }

                            item {
                                DescriptionSection(description = productDetail.description)
                            }

                            if(!comments.isNullOrEmpty()) {
                                item {
                                    RatingSection(
                                        isSeeMoreComment = comments!!.size > 3,
                                        selectedRating = selectedRating,
                                        onSelectedRating = onSelectedRating,
                                    ) {
                                        seeMoreCommentOrNot = true
                                        coroutineScope.launch { bottomSheetState.show() }
                                    }
                                }

                                item {
                                    CommentSection(
                                        if (comments!!.size > 3) comments!!.subList(0, 3) else comments!!,
                                    )
                                }
                            }

                            item {
                                val result  = handlePagingResult(moreProducts = moreProducts) { message ->
                                    snackBarData = snackBarData.copy(
                                        message = message,
                                        iconId = R.drawable.ic_error,
                                        snackBarState = Constants.SNACK_BAR_STATUS_ERROR
                                    )
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("")
                                    }
                                }
                                if(result) {
                                    MoreProductSection(
                                        moreProducts = moreProducts,
                                        userToken = userToken,
                                        productDetailViewModel = productDetailViewModel,
                                    ) { chooseProduct ->
                                        product = chooseProduct
                                    }
                                }
                            }

                        }
                    }
                }

                DetailScreenTopBar(navController = navController)

                AnimatedVisibility(
                    visible = getProductDetailResponse.data != null,
                    modifier = Modifier.align(Alignment.BottomEnd),
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    BottomButtonAction(
                        modifier = Modifier.padding(end = MEDIUM_PADDING, bottom = MEDIUM_PADDING),
                        onClicked = {
                            seeMoreCommentOrNot = false
                            coroutineScope.launch { bottomSheetState.show() }
                        }
                    )
                }

                AnimatedVisibility(
                    visible = isLoading,
                    modifier = Modifier.align(Alignment.Center),
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
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

    AsyncImage(
        modifier = Modifier
            .graphicsLayer {
                scrolledY += state.firstVisibleItemScrollOffset - previousOffset
                translationY = scrolledY * 0.1f
                previousOffset = state.firstVisibleItemScrollOffset
            }
            .fillMaxWidth()
            .height(PRODUCT_IMAGE_HEIGHT),
        contentScale = ContentScale.Crop,
        model = ImageRequest.Builder(LocalContext.current)
            .data(image)
            .crossfade(200)
            .placeholder(R.drawable.image_logo)
            .error(R.drawable.image_logo)
            .build(),
        contentDescription = ""
    )
}

@Composable
fun ProductNameSection(
    name: String = "Sweet Cappuccino",
    isFav: Boolean,
    onFavClicked: () -> Unit,
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
            fontFamily = ralewayMedium,
            fontSize = MaterialTheme.typography.h6.fontSize
        )
        IconButton(onClick = onFavClicked) {
            Icon(
                painter = painterResource(id = if (isFav) R.drawable.ic_heart_fill else R.drawable.ic_favorite),
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
            text = stringResource(
                id = R.string.product_price, NumberFormat.getNumberInstance(
                    Locale.US
                ).format(price)
            ),
            color = MaterialTheme.colors.textColor,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontFamily = montserratFontFamily,
            fontSize = MaterialTheme.typography.subtitle2.fontSize
        )
        if (rating > 0) {
            Text(
                modifier = Modifier.padding(horizontal = EXTRA_EXTRA_SMALL_PADDING),
                text = "•",
                fontFamily = ralewayMedium,
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
            fontFamily = ralewayMedium,
            color = MaterialTheme.colors.textColor,
            fontSize = MaterialTheme.typography.body1.fontSize,
        )
        Spacer(modifier = Modifier.height(SMALL_PADDING))
        Text(
            text = description,
            fontFamily = ralewayMedium,
            color = MaterialTheme.colors.textColor,
            fontSize = MaterialTheme.typography.caption.fontSize,
            maxLines = 5,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun MoreProductSection(
    moreProducts: LazyPagingItems<Product>,
    userToken: String,
    productDetailViewModel: ProductDetailViewModel,
    updateChooseProduct: (Product) -> Unit
) {
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
            fontFamily = ralewayMedium,
            color = MaterialTheme.colors.textColor,
            fontSize = MaterialTheme.typography.body1.fontSize,
        )
        LazyRow(
            contentPadding = PaddingValues(
                start = EXTRA_LARGE_PADDING,
                top = EXTRA_LARGE_PADDING,
                end = EXTRA_LARGE_PADDING
            ),
            horizontalArrangement = Arrangement.spacedBy(EXTRA_LARGE_PADDING)
        ) {
            items(moreProducts) { product ->
                product?.let {
                    VerticalProductItem(
                        onItemClicked = {
                            productDetailViewModel.getProductDetail(token = userToken, productId = product.id)
                            updateChooseProduct(product)
                            productDetailViewModel.getComment(token = userToken, productId = product.id)
                        },
                        onFavClicked = {
                            updateChooseProduct(product)
                            productDetailViewModel.removeOrAddMoreProductToFav(token = userToken, productId = product.id, isFav = product.isFavorite)
                            productDetailViewModel.removeOrAddMoreProductToFavList(productId = product.id, isAdd = !product.isFavorite)
                        },
                        product = product,
                        isFav = product.isFavorite
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
    }
}

@Composable
fun RatingSection(
    avgRating: Int = 5,
    isSeeMoreComment: Boolean,
    selectedRating: Int,
    onSelectedRating: (Int) -> Unit,
    onSeeMoreComment: () -> Unit
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
                fontFamily = ralewayMedium,
                color = MaterialTheme.colors.textColor,
                fontSize = MaterialTheme.typography.body1.fontSize,
            )
            if (isSeeMoreComment) {
                Text(
                    modifier = Modifier.clickable {
                        onSeeMoreComment()
                    },
                    text = "Xem chi tiết...",
                    fontFamily = ralewayMedium,
                    color = MaterialTheme.colors.textColor2,
                    fontSize = MaterialTheme.typography.caption.fontSize,
                )
            }
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
fun CommentSection(
    comments: List<Comment>,
) {
    Column(
        modifier = Modifier.padding(horizontal = EXTRA_LARGE_PADDING),
        verticalArrangement = Arrangement.spacedBy(EXTRA_LARGE_PADDING),
    ) {
        comments.forEach {
            CommentItem(it)
        }
    }
}

@Composable
fun BottomButtonAction(
    modifier: Modifier,
    onClicked: () -> Unit
) {

    IconButton(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                shape = CircleShape
            )
            .clip(shape = CircleShape)
            .background(MaterialTheme.colors.textColor2),
        onClick = onClicked
    ) {
        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = stringResource(R.string.shop_now),
            tint = MaterialTheme.colors.textColor
        )
    }

//    Row(
//        modifier = modifier
//            .fillMaxWidth()
//            .background(
//                brush = Brush.verticalGradient(
//                    colors = listOf(
//                        Color.Transparent,
//                        MaterialTheme.colors.greyPrimary,
//                        MaterialTheme.colors.greyPrimary,
//                    ),
//                )
//            )
//            .padding(vertical = EXTRA_LARGE_PADDING),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceAround
//    ) {
//        Spacer(modifier = Modifier.width(EXTRA_LARGE_PADDING))
//        ButtonCustom(
//            modifier = Modifier.weight(1f),
//            buttonContent = stringResource(id = R.string.add_to_cart),
//            backgroundColor = MaterialTheme.colors.textColor,
//            textColor = MaterialTheme.colors.greyPrimary,
//            onClick = onAddToCardClicked,
//            paddingValues = PaddingValues(ZERO_PADDING),
//            contentPadding = PaddingValues(
//                horizontal = EXTRA_LARGE_PADDING,
//                vertical = MEDIUM_SMALL_PADDING
//            ),
//            buttonElevation = null,
//            shapes = RoundedCornerShape(26.dp)
//        )
//        Spacer(modifier = Modifier.width(EXTRA_LARGE_PADDING))
//        ButtonCustom(
//            modifier = Modifier.weight(1f),
//            buttonContent = stringResource(R.string.buy_now),
//            backgroundColor = MaterialTheme.colors.textColor2,
//            textColor = MaterialTheme.colors.textColor,
//            onClick = onButNowClicked,
//            paddingValues = PaddingValues(ZERO_PADDING),
//            contentPadding = PaddingValues(
//                horizontal = EXTRA_LARGE_PADDING,
//                vertical = MEDIUM_SMALL_PADDING
//            ),
//            buttonElevation = null,
//            shapes = RoundedCornerShape(26.dp)
//        )
//        Spacer(modifier = Modifier.width(EXTRA_LARGE_PADDING))
//    }
}

@Composable
fun handlePagingResult(
    moreProducts: LazyPagingItems<Product>,
    showMessage: (String) -> Unit
): Boolean {
    moreProducts.apply {
        val error = when {
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            else -> null
        }

        return when {
            loadState.refresh is LoadState.Loading -> {
                false
            }
            error != null -> {
                showMessage(error.error.message.orEmpty())
                false
            }
            moreProducts.itemCount < 1 -> {
                false
            }
            else -> {
                true
            }
        }
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