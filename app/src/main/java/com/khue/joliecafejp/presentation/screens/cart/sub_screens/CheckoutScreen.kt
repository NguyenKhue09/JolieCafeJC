package com.khue.joliecafejp.presentation.screens.cart.sub_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.khue.joliecafejp.R
import com.khue.joliecafejp.domain.model.Address
import com.khue.joliecafejp.domain.model.CartItem
import com.khue.joliecafejp.domain.model.Product
import com.khue.joliecafejp.domain.model.Voucher
import com.khue.joliecafejp.presentation.common.ButtonCustom
import com.khue.joliecafejp.presentation.common.CardCustom
import com.khue.joliecafejp.presentation.common.TopBar
import com.khue.joliecafejp.presentation.components.ProductOrderHistoryItem
import com.khue.joliecafejp.ui.theme.*
import com.khue.joliecafejp.utils.extensions.haveBorder

@Composable
fun CheckoutScreen(
    navController: NavHostController
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.greyPrimary,
        topBar = {
            TopBar(
                titleId = R.string.checkout,
                navController = navController,
                trailingButton = null
            )
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = EXTRA_LARGE_PADDING)
        ) {
            item {
                ShippingInfos(
                    address = Address(
                        id = "1",
                        userId = "1",
                        userName = "John Doe",
                        phone = "0123456789",
                        address = "123 Main St",
                        __v = 0,
                    ),
                    onChangeAddress = {}
                )
            }
            item {
                Products(
                    items = listOf(
                        CartItem(
                            id = "",
                            productId = "",
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
                        CartItem(
                            id = "",
                            productId = "",
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
                        CartItem(
                            id = "",
                            productId = "",
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
                    )
                )
            }
            item {
                Voucher(
                    onVoucherClicked = {}
                )
            }
            item {
                Payments(
                    paymentMethodSelected = "MoMo",
                    onSelectedPaymentMethod = { _ ->

                    }
                )
            }
            item {
                JolieCoin(
                    jolieCoin = 100,
                    isApplyCoin = true,
                    onApplyCoinClicked = { _ -> }
                )
            }
            item {
                Summary(
                    subTotal = 100000.0,
                    shippingFee = 10000.0,
                    jolieCoin = 100,
                    voucher = 10000.0,
                    total = 110000.0,
                )
            }
            item {
                FooterButtonAction(
                    onOrderClicked = {},
                    onCancelClicked = {}
                )
            }
        }
    }
}

@Composable
internal fun ShippingInfos(
    address: Address,
    onChangeAddress: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = EXTRA_LARGE_PADDING)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .wrapContentSize(align = Alignment.CenterStart),
                text = stringResource(R.string.shipping_infos),
                fontFamily = ralewayMedium,
                color = MaterialTheme.colors.textColor,
                fontSize = MaterialTheme.typography.h6.fontSize,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                modifier = Modifier
                    .clickable {
                        onChangeAddress()
                    }
                    .wrapContentSize(align = Alignment.CenterStart),
                text = stringResource(id = R.string.change),
                fontFamily = ralewayMedium,
                color = MaterialTheme.colors.textColor2,
                fontSize = MaterialTheme.typography.body1.fontSize,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
        CardCustom(
            onClick = null,
            paddingValues = PaddingValues(
                top = MEDIUM_PADDING
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MEDIUM_PADDING),
            ) {
                Text(
                    modifier = Modifier
                        .wrapContentSize(align = Alignment.CenterStart),
                    text = address.userName,
                    fontFamily = ralewayMedium,
                    color = MaterialTheme.colors.textColor,
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    modifier = Modifier
                        .wrapContentSize(align = Alignment.CenterStart),
                    text = address.phone,
                    fontFamily = montserratFontFamily,
                    color = MaterialTheme.colors.textColor,
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    modifier = Modifier
                        .wrapContentSize(align = Alignment.CenterStart),
                    text = address.address,
                    fontFamily = montserratFontFamily,
                    color = MaterialTheme.colors.textColor,
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Composable
internal fun Products(
    items: List<CartItem>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = EXTRA_LARGE_PADDING)
    ) {
        Text(
            modifier = Modifier
                .wrapContentSize(align = Alignment.CenterStart),
            text = stringResource(R.string.products),
            fontFamily = ralewayMedium,
            color = MaterialTheme.colors.textColor,
            fontSize = MaterialTheme.typography.h6.fontSize,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        CardCustom(
            onClick = null,
            paddingValues = PaddingValues(
                top = MEDIUM_PADDING
            )
        ) {
            Column {
                items.forEach { item ->
                    ProductOrderHistoryItem(
                        modifier = Modifier.padding(horizontal = MEDIUM_PADDING),
                        item = item
                    )
                }
                Spacer(modifier = Modifier.height(SMALL_PADDING))
            }
        }
    }

}

@Composable
internal fun Voucher(
    onVoucherClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = EXTRA_LARGE_PADDING)
    ) {
        Text(
            modifier = Modifier
                .wrapContentSize(align = Alignment.CenterStart),
            text = stringResource(R.string.vouchers),
            fontFamily = ralewayMedium,
            color = MaterialTheme.colors.textColor,
            fontSize = MaterialTheme.typography.h6.fontSize,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        CardCustom(
            onClick = onVoucherClicked,
            paddingValues = PaddingValues(
                top = MEDIUM_PADDING
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = EXTRA_LARGE_PADDING, vertical = SMALL_PADDING),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.weight(1f),
                    painter = painterResource(id = R.drawable.ic_voucher),
                    contentDescription = stringResource(R.string.vouchers),
                    tint = MaterialTheme.colors.textColor
                )
                Text(
                    modifier = Modifier
                        .padding(
                            start = SMALL_PADDING
                        )
                        .weight(8f),
                    text = stringResource(R.string.choose_a_voucher),
                    fontFamily = ralewayMedium,
                    color = MaterialTheme.colors.textColor,
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}

@Composable
internal fun Payments(
    paymentMethodSelected: String,
    onSelectedPaymentMethod: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = EXTRA_LARGE_PADDING)
    ) {
        Text(
            modifier = Modifier
                .wrapContentSize(align = Alignment.CenterStart),
            text = stringResource(R.string.payments),
            fontFamily = ralewayMedium,
            color = MaterialTheme.colors.textColor,
            fontSize = MaterialTheme.typography.h6.fontSize,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(EXTRA_LARGE_PADDING)
        ) {
            CardCustom(
                onClick = {
                    onSelectedPaymentMethod("COD")
                },
                paddingValues = PaddingValues(
                    top = MEDIUM_PADDING
                ),
                haveBorder = paymentMethodSelected == "COD"
            ) {
                Column(
                    modifier = Modifier.size(
                        width = 100.dp,
                        height = 80.dp
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.size(
                            width = 24.dp,
                            height = 24.dp
                        ),
                        painter = painterResource(id = R.drawable.ic_cash),
                        contentDescription = stringResource(R.string.cod_payment),
                        tint = MaterialTheme.colors.textColor
                    )
                    Spacer(modifier = Modifier.height(SMALL_PADDING))
                    Text(
                        modifier = Modifier
                            .wrapContentSize(align = Alignment.CenterStart),
                        text = stringResource(R.string.cash),
                        fontFamily = ralewayMedium,
                        color = MaterialTheme.colors.textColor,
                        fontSize = MaterialTheme.typography.body1.fontSize,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
            CardCustom(
                onClick = {
                    onSelectedPaymentMethod("Visa card")
                },
                paddingValues = PaddingValues(
                    top = MEDIUM_PADDING
                ),
                haveBorder = paymentMethodSelected == "Visa card"
            ) {
                Column(
                    modifier = Modifier.size(
                        width = 100.dp,
                        height = 80.dp
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_credit_card),
                        contentDescription = stringResource(R.string.credit_card),
                        tint = MaterialTheme.colors.textColor
                    )
                    Spacer(modifier = Modifier.height(SMALL_PADDING))
                    Text(
                        modifier = Modifier
                            .wrapContentSize(align = Alignment.CenterStart),
                        text = stringResource(R.string.credit_card),
                        fontFamily = ralewayMedium,
                        color = MaterialTheme.colors.textColor,
                        fontSize = MaterialTheme.typography.body1.fontSize,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            Image(
                modifier = Modifier
                    .padding(top = MEDIUM_PADDING)
                    .size(
                        width = 100.dp,
                        height = 80.dp
                    )
                    .clip(MaterialTheme.shapes.medium)
                    .clickable {
                        onSelectedPaymentMethod("MoMo")
                    }
                    .haveBorder(paymentMethodSelected == "MoMo"),
                painter = painterResource(id = R.drawable.momo),
                contentDescription = stringResource(R.string.momo),
                contentScale = ContentScale.FillBounds
            )
        }

    }
}

@Composable
internal fun JolieCoin(
    jolieCoin: Int,
    isApplyCoin: Boolean,
    onApplyCoinClicked: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = EXTRA_LARGE_PADDING)
    ) {
        Text(
            modifier = Modifier
                .wrapContentSize(align = Alignment.CenterStart),
            text = stringResource(R.string.jolie_coin),
            fontFamily = ralewayMedium,
            color = MaterialTheme.colors.textColor,
            fontSize = MaterialTheme.typography.h6.fontSize,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        CardCustom(
            onClick = null,
            paddingValues = PaddingValues(
                top = MEDIUM_PADDING
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = EXTRA_LARGE_PADDING, vertical = EXTRA_EXTRA_SMALL_PADDING),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = stringResource(R.string.use_jolie_coin, jolieCoin),
                    fontFamily = ralewayMedium,
                    color = MaterialTheme.colors.textColor,
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = stringResource(R.string.apply_coin, if (isApplyCoin) jolieCoin else 0),
                    fontFamily = ralewayMedium,
                    color = MaterialTheme.colors.textColor,
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    textAlign = TextAlign.Start
                )
                Switch(
                    checked = isApplyCoin,
                    onCheckedChange = onApplyCoinClicked,
                    colors = SwitchDefaults.colors(
                        uncheckedThumbColor = MaterialTheme.colors.textColor,
                        checkedThumbColor = MaterialTheme.colors.titleTextColor
                    )
                )
            }
        }
    }
}

@Composable
internal fun Summary(
    subTotal: Double,
    shippingFee: Double?,
    jolieCoin: Int?,
    voucher: Double?,
    total: Double,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = EXTRA_LARGE_PADDING)
    ) {
        Text(
            modifier = Modifier
                .wrapContentSize(align = Alignment.CenterStart),
            text = stringResource(R.string.summary),
            fontFamily = ralewayMedium,
            color = MaterialTheme.colors.textColor,
            fontSize = MaterialTheme.typography.h6.fontSize,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        CardCustom(
            onClick = null,
            paddingValues = PaddingValues(
                top = MEDIUM_PADDING
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = EXTRA_LARGE_PADDING),
                verticalArrangement = Arrangement.spacedBy(SMALL_PADDING),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.subtotal),
                        fontFamily = ralewayMedium,
                        color = MaterialTheme.colors.textColor,
                        fontSize = MaterialTheme.typography.body2.fontSize,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )

                    Text(
                        modifier = Modifier
                            .padding(
                                start = SMALL_PADDING
                            ),
                        text = stringResource(R.string.money, subTotal.toString()),
                        fontFamily = montserratFontFamily,
                        color = MaterialTheme.colors.textColor,
                        fontSize = MaterialTheme.typography.body2.fontSize,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )
                }

                shippingFee?.let {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.shipping_fee),
                            fontFamily = ralewayMedium,
                            color = MaterialTheme.colors.textColor,
                            fontSize = MaterialTheme.typography.body2.fontSize,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2
                        )

                        Text(
                            modifier = Modifier
                                .padding(
                                    start = SMALL_PADDING
                                ),
                            text = stringResource(id = R.string.money, shippingFee.toString()),
                            fontFamily = montserratFontFamily,
                            color = MaterialTheme.colors.textColor,
                            fontSize = MaterialTheme.typography.body2.fontSize,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2
                        )
                    }
                }

                jolieCoin?.let {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.jolie_coin),
                            fontFamily = ralewayMedium,
                            color = MaterialTheme.colors.textColor,
                            fontSize = MaterialTheme.typography.body2.fontSize,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2
                        )

                        Text(
                            modifier = Modifier
                                .padding(
                                    start = SMALL_PADDING
                                ),
                            text = stringResource(id = R.string.money, "-$jolieCoin"),
                            fontFamily = montserratFontFamily,
                            color = MaterialTheme.colors.textColor,
                            fontSize = MaterialTheme.typography.body2.fontSize,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2
                        )
                    }
                }

                voucher?.let {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.voucher_apply),
                            fontFamily = ralewayMedium,
                            color = MaterialTheme.colors.textColor,
                            fontSize = MaterialTheme.typography.body2.fontSize,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2
                        )

                        Text(
                            modifier = Modifier
                                .padding(
                                    start = SMALL_PADDING
                                ),
                            text = stringResource(id = R.string.money, "-$voucher"),
                            fontFamily = montserratFontFamily,
                            color = MaterialTheme.colors.textColor,
                            fontSize = MaterialTheme.typography.body2.fontSize,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2
                        )
                    }
                }

                Divider(color = MaterialTheme.colors.textColor, thickness = 1.dp)

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.total),
                        fontFamily = ralewayMedium,
                        color = MaterialTheme.colors.titleTextColor,
                        fontSize = MaterialTheme.typography.body2.fontSize,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )

                    Text(
                        modifier = Modifier
                            .padding(
                                start = SMALL_PADDING
                            ),
                        text = stringResource(id = R.string.money, total.toString()),
                        fontFamily = montserratFontFamily,
                        color = MaterialTheme.colors.titleTextColor,
                        fontSize = MaterialTheme.typography.body2.fontSize,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )
                }
            }
        }
    }
}

@Composable
internal fun FooterButtonAction(
    onCancelClicked: () -> Unit,
    onOrderClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = EXTRA_LARGE_PADDING),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        ButtonCustom(
            buttonContent = stringResource(id = R.string.cancel),
            backgroundColor = Color.Transparent,
            textColor = MaterialTheme.colors.textColor2,
            onClick = onCancelClicked,
            paddingValues = PaddingValues(top = EXTRA_LARGE_PADDING),
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
            modifier = Modifier.size(height = 65.dp,width = 150.dp),
            buttonContent = stringResource(R.string.order),
            backgroundColor = MaterialTheme.colors.titleTextColor,
            textColor = MaterialTheme.colors.textColor,
            onClick = onOrderClicked,
            paddingValues = PaddingValues(
                start = EXTRA_LARGE_PADDING,
                top = EXTRA_LARGE_PADDING
            ),
            contentPadding = PaddingValues(
                horizontal = EXTRA_LARGE_PADDING,
                vertical = MEDIUM_SMALL_PADDING
            ),
            buttonElevation = null
        )
    }
}

@Preview
@Composable
fun ShippingInfosPrev() {
    ShippingInfos(
        address = Address(
            id = "1",
            userId = "1",
            userName = "John Doe",
            phone = "0123456789",
            address = "123 Main St",
            __v = 0,
        ),
        onChangeAddress = {}
    )
}

@Preview
@Composable
fun ProductsPrev() {
    Products(
        items = listOf(
            CartItem(
                id = "",
                productId = "",
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
            CartItem(
                id = "",
                productId = "",
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
            CartItem(
                id = "",
                productId = "",
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
        )
    )
}

@Preview
@Composable
fun VoucherPrev() {
    Voucher(
        onVoucherClicked = {}
    )
}

@Preview
@Composable
fun PaymentsPrev() {
    Payments(
        paymentMethodSelected = "MoMo",
        onSelectedPaymentMethod = { _ ->

        }
    )
}

@Preview
@Composable
fun JolieCoinPrev() {
    JolieCoin(
        jolieCoin = 100,
        isApplyCoin = true,
        onApplyCoinClicked = { _ -> }
    )
}

@Preview
@Composable
fun SummaryPrev() {
    Summary(
        subTotal = 100000.0,
        shippingFee = 10000.0,
        jolieCoin = 100,
        voucher = 10000.0,
        total = 110000.0,
    )
}

@Preview
@Composable
fun FooterButtonActionPrev() {
    FooterButtonAction(
        onOrderClicked = {},
        onCancelClicked = {}
    )
}

@Preview
@Composable
fun CheckoutScreenPrev() {
    CheckoutScreen(navController = rememberNavController())
}