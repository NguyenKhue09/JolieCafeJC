package com.khue.joliecafejp.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.khue.joliecafejp.R
import com.khue.joliecafejp.domain.model.OrderHistory
import com.khue.joliecafejp.domain.model.OrderHistoryUserInfos
import com.khue.joliecafejp.presentation.common.CardCustom
import com.khue.joliecafejp.ui.theme.*
import com.khue.joliecafejp.utils.extensions.formatTo
import com.khue.joliecafejp.utils.extensions.toDate
import java.text.NumberFormat
import java.util.*

@Composable
fun OrderHistoryItem(
    modifier: Modifier = Modifier,
    scrollToPosition: MutableState<Float>,
    onExpanded: () -> Unit,
    onReviewClicked: () -> Unit,
    bill: OrderHistory,
    isExpanded: Boolean,
) {
    val angle: Float by animateFloatAsState(
        targetValue = if (!isExpanded) 180f else 0f
    )

    CardCustom(
        modifier = modifier.onGloballyPositioned { coordinates ->
            scrollToPosition.value = coordinates.positionInParent().y
        },
        onClick = null,
        paddingValues = PaddingValues(all = ZERO_PADDING)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = EXTRA_LARGE_PADDING,
                    end = EXTRA_LARGE_PADDING,
                    bottom = EXTRA_LARGE_PADDING
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text ="${bill.orderDate.toDate()!!.formatTo()} - ${bill.status}",
                    fontFamily = montserratFontFamily,
                    color = MaterialTheme.colors.greySecondary,
                    fontSize = MaterialTheme.typography.caption.fontSize,
                    overflow = TextOverflow.Ellipsis,
                )

                if(bill.paid && !bill.isRated) {
                    IconButton(onClick = onReviewClicked) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_review),
                            contentDescription = stringResource(id = R.string.review),
                            tint = MaterialTheme.colors.textColor2
                        )
                    }
                }

                IconButton(
                    onClick = {
                        onExpanded()
                    }
                ) {
                    Icon(
                        modifier = Modifier.rotate(degrees = angle),
                        painter = painterResource(id = R.drawable.ic_chevron_up),
                        contentDescription = stringResource(R.string.close),
                        tint = MaterialTheme.colors.textColor
                    )
                }
            }

            Text(
                modifier = Modifier
                    .padding(
                        bottom = SMALL_PADDING
                    )
                    .align(Alignment.Start),
                text = stringResource(R.string.order_id, bill.orderId),
                fontFamily = montserratFontFamily,
                color = MaterialTheme.colors.greySecondary,
                fontSize = MaterialTheme.typography.subtitle2.fontSize,
                overflow = TextOverflow.Ellipsis,
            )

            AnimatedVisibility(
                visible = isExpanded,
                exit = fadeOut() + shrinkVertically(animationSpec = tween(durationMillis = 500))
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier.wrapContentHeight()
                    ) {
                        bill.products.forEach {
                            ProductOrderHistoryItem(item = it)
                        }
                    }

                    Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))

                    Text(
                        modifier = Modifier
                            .padding(
                                bottom = SMALL_PADDING
                            ),
                        text = bill.address.userName,
                        fontFamily = ralewayMedium,
                        color = MaterialTheme.colors.greySecondary,
                        fontSize = MaterialTheme.typography.subtitle2.fontSize,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        modifier = Modifier
                            .padding(
                                bottom = SMALL_PADDING
                            ),
                        text = bill.address.phone,
                        fontFamily = montserratFontFamily,
                        color = MaterialTheme.colors.textColor,
                        fontSize = MaterialTheme.typography.caption.fontSize,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        modifier = Modifier
                            .padding(
                                bottom = EXTRA_LARGE_PADDING
                            ),
                        text = bill.address.address,
                        fontFamily = montserratFontFamily,
                        color = MaterialTheme.colors.textColor,
                        fontSize = MaterialTheme.typography.caption.fontSize,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )

                    Text(
                        modifier = Modifier
                            .padding(
                                bottom = SMALL_PADDING
                            ),
                        text = stringResource(R.string.summary),
                        fontFamily = montserratFontFamily,
                        color = MaterialTheme.colors.greySecondary,
                        fontSize = MaterialTheme.typography.subtitle2.fontSize,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                bottom = SMALL_PADDING
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.subtotal),
                            fontFamily = ralewayMedium,
                            color = MaterialTheme.colors.textColor,
                            fontSize = MaterialTheme.typography.caption.fontSize,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2
                        )

                        Text(
                            modifier = Modifier
                                .padding(
                                    start = SMALL_PADDING
                                ),
                            text = stringResource(
                                id = R.string.money, NumberFormat.getNumberInstance(
                                    Locale.US
                                ).format(bill.totalCost - bill.shippingFee - bill.discountCost - bill.scoreApply)
                            ),
                            fontFamily = montserratFontFamily,
                            color = MaterialTheme.colors.textColor,
                            fontSize = MaterialTheme.typography.caption.fontSize,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                bottom = SMALL_PADDING
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.shipping_fee),
                            fontFamily = ralewayMedium,
                            color = MaterialTheme.colors.textColor,
                            fontSize = MaterialTheme.typography.caption.fontSize,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2
                        )

                        Text(
                            modifier = Modifier
                                .padding(
                                    start = SMALL_PADDING
                                ),
                            text = stringResource(
                                id = R.string.money, NumberFormat.getNumberInstance(
                                    Locale.US
                                ).format(bill.shippingFee)
                            ),
                            fontFamily = montserratFontFamily,
                            color = MaterialTheme.colors.textColor,
                            fontSize = MaterialTheme.typography.caption.fontSize,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                bottom = SMALL_PADDING
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.discount),
                            fontFamily = ralewayMedium,
                            color = MaterialTheme.colors.textColor,
                            fontSize = MaterialTheme.typography.caption.fontSize,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2
                        )

                        Text(
                            modifier = Modifier
                                .padding(
                                    start = SMALL_PADDING
                                ),
                            text = stringResource(
                                id = R.string.money, NumberFormat.getNumberInstance(
                                    Locale.US
                                ).format(-bill.discountCost)
                            ),
                            fontFamily = montserratFontFamily,
                            color = MaterialTheme.colors.textColor,
                            fontSize = MaterialTheme.typography.caption.fontSize,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                bottom = SMALL_PADDING
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Score applied",
                            fontFamily = ralewayMedium,
                            color = MaterialTheme.colors.textColor,
                            fontSize = MaterialTheme.typography.caption.fontSize,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2
                        )

                        Text(
                            modifier = Modifier
                                .padding(
                                    start = SMALL_PADDING
                                ),
                            text = stringResource(
                                id = R.string.money, NumberFormat.getNumberInstance(
                                    Locale.US
                                ).format(-bill.scoreApply)
                            ),
                            fontFamily = montserratFontFamily,
                            color = MaterialTheme.colors.textColor,
                            fontSize = MaterialTheme.typography.caption.fontSize,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2
                        )
                    }

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
                            fontSize = MaterialTheme.typography.caption.fontSize,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2
                        )

                        Text(
                            modifier = Modifier
                                .padding(
                                    start = SMALL_PADDING
                                ),
                            text = stringResource(
                                id = R.string.money, NumberFormat.getNumberInstance(
                                    Locale.US
                                ).format(bill.totalCost)
                            ),
                            fontFamily = montserratFontFamily,
                            color = MaterialTheme.colors.titleTextColor,
                            fontSize = MaterialTheme.typography.caption.fontSize,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.paid),
                            fontFamily = ralewayMedium,
                            color = MaterialTheme.colors.titleTextColor,
                            fontSize = MaterialTheme.typography.caption.fontSize,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2
                        )

                        Text(
                            modifier = Modifier
                                .padding(
                                    start = SMALL_PADDING
                                ),
                            text = if(bill.paid) "You paid this bill" else "You haven't paid this bill",
                            fontFamily = montserratFontFamily,
                            color = MaterialTheme.colors.titleTextColor,
                            fontSize = MaterialTheme.typography.caption.fontSize,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2
                        )
                    }

                }
            }

            AnimatedVisibility(visible = !isExpanded) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.items, bill.products.sumOf { it.quantity }),
                        fontFamily = montserratFontFamily,
                        color = MaterialTheme.colors.greySecondary,
                        fontSize = MaterialTheme.typography.subtitle2.fontSize,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = stringResource(
                            id = R.string.money, NumberFormat.getNumberInstance(
                                Locale.US
                            ).format(bill.totalCost)
                        ),
                        fontFamily = montserratFontFamily,
                        color = MaterialTheme.colors.greySecondary,
                        fontSize = MaterialTheme.typography.caption.fontSize,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun OrderHistoryItemPrev() {
    OrderHistoryItem(
        scrollToPosition = remember {
            mutableStateOf(0f)
        },
        onExpanded = {},
        onReviewClicked = {},
        bill = OrderHistory(
            id = "1",
            products = emptyList(),
            shippingFee = 100.0,
            discountCost = 50.0,
            totalCost = 300.0,
            paid = true,
            address = OrderHistoryUserInfos(
                userId = "1",
                userName = "John Doe",
                phone = "0912345678",
                address = "123 ABC Street, District 1, HCM City",
            ),
            orderDate = "2022-06-18T07:54:59.633Z",
            orderId = "1",
            status = "Pending",
            paymentMethod = "COD",
            scoreApply = 0,
        ),
        isExpanded = true
    )
}