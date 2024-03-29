package com.khue.joliecafejp.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.khue.joliecafejp.R
import com.khue.joliecafejp.domain.model.Product
import com.khue.joliecafejp.domain.model.ProductTopping
import com.khue.joliecafejp.presentation.viewmodels.HomeViewModel
import com.khue.joliecafejp.ui.theme.*
import com.khue.joliecafejp.utils.AddProductToCartEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductOptionsBottomSheet(
    paddingValues: PaddingValues,
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    product: Product?,
    homeViewModel: HomeViewModel,
    onAddProductToCart: (Map<String, String>) -> Unit,
    onPurchaseProduct: () -> Unit
) {

    val addProductToCartState by homeViewModel.addProductToCartState.collectAsState()

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
        homeViewModel.productEvent.collect { event ->
            when (event) {
                is HomeViewModel.ProductEvent.AddToCart -> {
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
                is HomeViewModel.ProductEvent.Purchase -> {
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
                    homeViewModel.onAddProductToCart(event = AddProductToCartEvent.SizeChanged(size = size))
                }
            )
            Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
            ProductOptionIngredient(
                title = stringResource(R.string.sugar),
                options = percent,
                selectedOption = addProductToCartState.sugar,
                onOptionSelected = { sugar ->
                    homeViewModel.onAddProductToCart(
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
                    homeViewModel.onAddProductToCart(event = AddProductToCartEvent.IceChanged(ice = ice))
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
                            homeViewModel.onAddProductToCart(
                                event = AddProductToCartEvent.AddTopping(
                                    topping = topping
                                )
                            )
                        } else {
                            homeViewModel.onAddProductToCart(
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
                    homeViewModel.onAddProductToCart(
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
                homeViewModel.onAddProductToCart(event = AddProductToCartEvent.AddToCart)
            },
            onPurchaseClicked = {
                homeViewModel.onAddProductToCart(event = AddProductToCartEvent.Purchase)
            }
        )
    }
}

@Composable
fun ProductOptionSize(
    size: List<String>,
    selectedOptionSize: String,
    onOptionSizeSelected: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = stringResource(R.string.size),
            color = MaterialTheme.colors.textColor,
            fontSize = MaterialTheme.typography.subtitle2.fontSize,
            fontFamily = ralewayMedium
        )
        Spacer(modifier = Modifier.height(SMALL_PADDING))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            size.forEach { text ->
                Box(
                    modifier = Modifier
                        .padding(end = EXTRA_LARGE_PADDING)
                        .width(PRODUCT_SIZE_BUTTON_SIZE)
                        .clip(MaterialTheme.shapes.medium)
                        .selectable(
                            selected = (text == selectedOptionSize),
                            onClick = { onOptionSizeSelected(text) }
                        )
                        .background(
                            color = if (text == selectedOptionSize)
                                MaterialTheme.colors.textColor2
                            else MaterialTheme.colors.textColor
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = SMALL_PADDING),
                        text = text,
                        color = if (text == selectedOptionSize) MaterialTheme.colors.textColor else MaterialTheme.colors.greyPrimary,
                        fontSize = MaterialTheme.typography.subtitle1.fontSize
                    )
                }
            }
        }
    }
}

@Composable
fun ProductOptionIngredient(
    title: String,
    options: List<Int>,
    selectedOption: Int,
    onOptionSelected: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = title,
            color = MaterialTheme.colors.textColor,
            fontSize = MaterialTheme.typography.subtitle2.fontSize,
            fontFamily = ralewayMedium
        )
        Spacer(modifier = Modifier.height(SMALL_PADDING))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = EXTRA_LARGE_PADDING),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            options.forEach { text ->
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = MaterialTheme.colors.textColor2,
                            unselectedColor = MaterialTheme.colors.textColor
                        )
                    )
                    Text(
                        text = "$text%",
                        color = MaterialTheme.colors.textColor,
                        fontSize = MaterialTheme.typography.caption.fontSize
                    )
                }
            }
        }
    }
}

@Composable
fun ProductToppingOption(
    title: String,
    toppingOptions: List<ProductTopping>,
    selectedToppingOption: List<ProductTopping>,
    onToppingOptionSelected: (ProductTopping, Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = title,
            color = MaterialTheme.colors.textColor,
            fontSize = MaterialTheme.typography.subtitle2.fontSize,
            fontFamily = ralewayMedium
        )
        Spacer(modifier = Modifier.height(SMALL_PADDING))

        toppingOptions.forEach { option ->
            Row(
                modifier = Modifier.padding(end = EXTRA_LARGE_PADDING),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = selectedToppingOption.contains(option),
                    onCheckedChange = { checked ->
                        if (checked) onToppingOptionSelected(option, true)
                        else onToppingOptionSelected(option, false)
                    },
                    colors = CheckboxDefaults.colors(
                        uncheckedColor = MaterialTheme.colors.textColor,
                        checkedColor = MaterialTheme.colors.titleTextColor
                    )
                )
                Text(
                    modifier = Modifier.weight(1f, fill = true),
                    text = option.name,
                    fontFamily = raleway,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.textColor,
                    fontSize = MaterialTheme.typography.caption.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = stringResource(id = R.string.product_price, NumberFormat.getNumberInstance(
                        Locale.US
                    ).format(option.price)),
                    color = MaterialTheme.colors.textColor,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    fontFamily = montserratFontFamily,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun ProductNote(
    noteTextState: String,
    onProductNoteTextChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = EXTRA_LARGE_PADDING),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "+ Note for mechanism",
            color = MaterialTheme.colors.textColor2,
            fontSize = MaterialTheme.typography.subtitle2.fontSize,
            fontFamily = ralewayMedium
        )
        Spacer(modifier = Modifier.height(SMALL_PADDING))
        TextFieldCustom(
            textFieldValue = noteTextState,
            onTextChange = {
                onProductNoteTextChange(it)
            },
            keyBoardType = KeyboardType.Text,
            trailingIcon = null,
            placeHolder = "Enter some note for product here",
            padding = ZERO_PADDING
        )
    }
}

@Composable
fun ButtonAction(
    onAddToCartClicked: () -> Unit,
    onPurchaseClicked: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        ButtonCustom(
            buttonContent = stringResource(R.string.add_to_cart),
            backgroundColor = Color.Transparent,
            textColor = MaterialTheme.colors.textColor,
            onClick = onAddToCartClicked,
            paddingValues = PaddingValues(top = SMALL_PADDING),
            contentPadding = PaddingValues(
                horizontal = EXTRA_LARGE_PADDING,
                vertical = MEDIUM_SMALL_PADDING
            ),
            buttonElevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp
            )
        )
        ButtonCustom(
            buttonContent = stringResource(R.string.purchase),
            backgroundColor = MaterialTheme.colors.titleTextColor,
            textColor = MaterialTheme.colors.textColor,
            onClick = onPurchaseClicked,
            paddingValues = PaddingValues(
                start = EXTRA_LARGE_PADDING,
                top = SMALL_PADDING,
                end = EXTRA_LARGE_PADDING
            ),
            contentPadding = PaddingValues(
                horizontal = EXTRA_LARGE_PADDING,
                vertical = MEDIUM_SMALL_PADDING
            ),
            buttonElevation = null
        )
    }
}