package com.khue.joliecafejp.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.khue.joliecafejp.R
import com.khue.joliecafejp.domain.model.ProductTopping
import com.khue.joliecafejp.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductOptionsBottomSheet(
    paddingValues: PaddingValues,
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
) {

    val size: List<String> = listOf("S", "M", "L")
    val percent: List<String> = listOf("0", "25", "50", "100")
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
    )

    val (selectedOptionSize, onOptionSizeSelected) = remember { mutableStateOf(size[0]) }
    val (selectedOptionSugar, onOptionSugarSelected) = remember { mutableStateOf(percent[0]) }
    val (selectedOptionIce, onOptionIceSelected) = remember { mutableStateOf(percent[0]) }
    val (selectedToppingOption, onToppingOptionSelected) = remember { mutableStateOf<ProductTopping?>(null) }

    val productNoteTextState = remember { mutableStateOf(TextFieldValue("")) }

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
                    modifier = Modifier
                        .wrapContentSize(align = Alignment.CenterStart),
                    text = "Latte",
                    fontFamily = raleway,
                    color = MaterialTheme.colors.textColor,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(SMALL_PADDING))
                Text(
                    modifier = Modifier
                        .wrapContentSize(align = Alignment.CenterStart),
                    text = "Coffee",
                    fontFamily = raleway,
                    color = MaterialTheme.colors.textColor,
                    fontSize = MaterialTheme.typography.caption.fontSize,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
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
                .weight(1f, fill = true)
                .verticalScroll(state = rememberScrollState())
        ) {
            ProductOptionSize(
                size = size,
                selectedOptionSize = selectedOptionSize,
                onOptionSizeSelected = onOptionSizeSelected
            )
            Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
            ProductOptionIngredient(
                title = stringResource(R.string.sugar),
                options = percent,
                selectedOption = selectedOptionSugar,
                onOptionSelected = onOptionSugarSelected
            )
            Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
            ProductOptionIngredient(
                title = stringResource(R.string.ice),
                options = percent,
                selectedOption = selectedOptionIce,
                onOptionSelected = onOptionIceSelected
            )
            Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
            ProductToppingOption(
                title = stringResource(R.string.topping),
                toppingOptions = topping,
                selectedToppingOption = selectedToppingOption,
                onToppingOptionSelected = onToppingOptionSelected
            )
            Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
            ProductNote(noteTextState = productNoteTextState)
            Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
            Text(
                text = stringResource(R.string.price),
                color = MaterialTheme.colors.textColor,
                fontSize = MaterialTheme.typography.subtitle2.fontSize,
                fontFamily = raleway
            )
            Text(
                text = stringResource(id = R.string.product_price, 90000),
                color = MaterialTheme.colors.textColor,
                fontSize = MaterialTheme.typography.subtitle1.fontSize,
                fontFamily = montserratFontFamily
            )
        }
        ButtonAction(
            onAddToCartClicked = {},
            onPurchaseClicked = {}
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
            fontFamily = raleway
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
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
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
            fontFamily = raleway
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
    selectedToppingOption: ProductTopping?,
    onToppingOptionSelected: (ProductTopping?) -> Unit
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
            fontFamily = raleway
        )
        Spacer(modifier = Modifier.height(SMALL_PADDING))

        toppingOptions.forEach { option ->
            Row(
                modifier = Modifier.padding(end = EXTRA_LARGE_PADDING),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = option == selectedToppingOption,
                    onCheckedChange = { checked ->
                        if (checked) onToppingOptionSelected(option)
                        else onToppingOptionSelected(null)
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
                    text = stringResource(id = R.string.product_price, option.price),
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
    noteTextState: MutableState<TextFieldValue>
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
            fontFamily = raleway
        )
        Spacer(modifier = Modifier.height(SMALL_PADDING))
        TextFieldCustom(
            textFieldValue = noteTextState,
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