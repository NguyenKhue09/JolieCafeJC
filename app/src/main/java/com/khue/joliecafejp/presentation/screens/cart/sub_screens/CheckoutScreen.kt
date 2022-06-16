package com.khue.joliecafejp.presentation.screens.cart.sub_screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.khue.joliecafejp.R
import com.khue.joliecafejp.domain.model.Address
import com.khue.joliecafejp.domain.model.CartItem
import com.khue.joliecafejp.domain.model.Product
import com.khue.joliecafejp.presentation.common.CardCustom
import com.khue.joliecafejp.presentation.common.TopBar
import com.khue.joliecafejp.presentation.components.ProductOrderHistoryItem
import com.khue.joliecafejp.ui.theme.*

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
                    .padding(all = EXTRA_LARGE_PADDING),
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
                    fontFamily = raleway,
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
            text = stringResource(R.string.vouchers),
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
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_cash),
                        contentDescription = stringResource(R.string.cod_payment)
                    )
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
        }

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
        paymentMethodSelected = "COD",
        onSelectedPaymentMethod = {}
    )
}

@Preview
@Composable
fun CheckoutScreenPrev() {
    CheckoutScreen(navController = rememberNavController())
}