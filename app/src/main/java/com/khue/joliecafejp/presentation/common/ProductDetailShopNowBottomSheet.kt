package com.khue.joliecafejp.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.khue.joliecafejp.R
import com.khue.joliecafejp.domain.model.Product
import com.khue.joliecafejp.domain.model.ProductTopping
import com.khue.joliecafejp.presentation.viewmodels.HomeViewModel
import com.khue.joliecafejp.presentation.viewmodels.ProductDetailViewModel
import com.khue.joliecafejp.ui.theme.*
import com.khue.joliecafejp.utils.AddProductToCartEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductDetailShopNowBottomSheet(
    paddingValues: PaddingValues,
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    product: Product?,
    productDetailViewModel: ProductDetailViewModel,
    onAddProductToCart: (Map<String, String>) -> Unit,
    onPurchaseProduct: () -> Unit
) {

    val addProductToCartState by productDetailViewModel.addProductToCartState.collectAsState()

    val size: List<String> = listOf("S", "M", "L")
    val percent: List<Int> = listOf(0, 25, 50, 100)
    val topping: List<ProductTopping> = listOf(
        ProductTopping(
            name = "Cream cheese",
            price = 10000
        ),
        ProductTopping(
            name = "Milk",
            price = 15000
        ),
        ProductTopping(
            name = "Black pearl",
            price = 20000
        ),
        ProductTopping(
            name = "Jelly",
            price = 10000
        ),
        ProductTopping(
            name = "Bubble",
            price = 10000
        ),
        ProductTopping(
            name = "Peach",
            price = 10000
        ),
        ProductTopping(
            name = "Crunch",
            price = 15000
        ),
        ProductTopping(
            name = "Lychee",
            price = 10000
        ),
    )

    LaunchedEffect(key1 = product) {
        productDetailViewModel.productEvent.collect { event ->
            when (event) {
                is ProductDetailViewModel.ProductEvent.AddToCart -> {
                    product?.let {
                        val data = mapOf(
                            "productId" to product.id,
                            "size" to addProductToCartState.size,
                            "quantity" to "1",
                            "price" to product.originPrice.toString(),
                        )
                        onAddProductToCart(data)
                    }
                }
                is ProductDetailViewModel.ProductEvent.Purchase -> {
                    onPurchaseProduct()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colors.greyOpacity60Primary)
            .padding(paddingValues)
            .padding(start = EXTRA_LARGE_PADDING, top = EXTRA_LARGE_PADDING),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = SMALL_PADDING),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {


            AsyncImage(
                modifier = Modifier
                    .height(IMAGE_PRODUCT_SIZE)
                    .width(IMAGE_PRODUCT_SIZE)
                    .clip(MaterialTheme.shapes.medium),
                model = ImageRequest.Builder(LocalContext.current)
                    .placeholder(R.drawable.image_logo)
                    .error(R.drawable.image_logo)
                    .data(product?.thumbnail ?: R.drawable.image_logo)
                    .crossfade(200)
                    .build(),
                contentDescription = stringResource(id = R.string.product_image),
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
                    modifier = Modifier
                        .wrapContentSize(align = Alignment.CenterStart),
                    text = product?.name ?: "Unknown",
                    fontFamily = ralewayMedium,
                    color = MaterialTheme.colors.textColor,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(SMALL_PADDING))
                Text(
                    modifier = Modifier
                        .wrapContentSize(align = Alignment.CenterStart),
                    text = product?.type ?: "Unknown",
                    fontFamily = ralewayMedium,
                    color = MaterialTheme.colors.textColor,
                    fontSize = MaterialTheme.typography.caption.fontSize,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

            }

            IconButton(
                onClick = {
                    coroutineScope.launch { modalBottomSheetState.hide() }
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_clear),
                    contentDescription = stringResource(
                        R.string.close
                    ),
                    tint = MaterialTheme.colors.textColor
                )
            }
        }
        Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, fill = false)
                .verticalScroll(state = rememberScrollState())
        ) {
            ProductOptionSize(
                size = size,
                selectedOptionSize = addProductToCartState.size,
                onOptionSizeSelected = { size ->
                    productDetailViewModel.onAddProductToCart(event = AddProductToCartEvent.SizeChanged(size = size))
                }
            )
            Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
            ProductOptionIngredient(
                title = stringResource(R.string.sugar),
                options = percent,
                selectedOption = addProductToCartState.sugar,
                onOptionSelected = { sugar ->
                    productDetailViewModel.onAddProductToCart(
                        event = AddProductToCartEvent.SugarChanged(
                            sugar = sugar
                        )
                    )
                }
            )
            Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
            ProductOptionIngredient(
                title = stringResource(R.string.ice),
                options = percent,
                selectedOption = addProductToCartState.ice,
                onOptionSelected = { ice ->
                    productDetailViewModel.onAddProductToCart(event = AddProductToCartEvent.IceChanged(ice = ice))
                }
            )
            product?.let {
                if (product.type == "Milk shake" || product.type == "Milk tea") {
                    Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
                    ProductToppingOption(
                        title = stringResource(R.string.topping),
                        toppingOptions = topping,
                        selectedToppingOption = addProductToCartState.toppings.toList()
                    ) { topping, isAdd ->
                        if (isAdd) {
                            productDetailViewModel.onAddProductToCart(
                                event = AddProductToCartEvent.AddTopping(
                                    topping = topping
                                )
                            )
                        } else {
                            productDetailViewModel.onAddProductToCart(
                                event = AddProductToCartEvent.RemoveTopping(
                                    topping = topping
                                )
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
            ProductNote(
                noteTextState = addProductToCartState.note,
                onProductNoteTextChange = { note ->
                    productDetailViewModel.onAddProductToCart(
                        event = AddProductToCartEvent.OnNoteChanged(
                            note = note
                        )
                    )
                }
            )
            Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
            Text(
                text = stringResource(R.string.price),
                color = MaterialTheme.colors.textColor,
                fontSize = MaterialTheme.typography.subtitle2.fontSize,
                fontFamily = ralewayMedium
            )
            Text(
                text = stringResource(id = R.string.product_price, NumberFormat.getNumberInstance(
                    Locale.US
                ).format(90000)),
                color = MaterialTheme.colors.textColor,
                fontSize = MaterialTheme.typography.subtitle1.fontSize,
                fontFamily = montserratFontFamily
            )
        }
        ButtonAction(
            onAddToCartClicked = {
                productDetailViewModel.onAddProductToCart(event = AddProductToCartEvent.AddToCart)
            },
            onPurchaseClicked = {
                productDetailViewModel.onAddProductToCart(event = AddProductToCartEvent.Purchase)
            }
        )
    }
}