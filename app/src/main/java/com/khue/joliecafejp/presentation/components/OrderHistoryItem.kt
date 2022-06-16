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
import com.khue.joliecafejp.presentation.common.CardCustom
import com.khue.joliecafejp.ui.theme.*

@Composable
fun OrderHistoryItem(
    modifier: Modifier = Modifier,
    scrollToPosition: MutableState<Float>,
    onExpanded: () -> Unit,
    onReviewClicked: () -> Unit,
) {

    var isExpanded by remember {
        mutableStateOf(true)
    }

    val angle: Float by animateFloatAsState(
        targetValue = if (!isExpanded) 180f else 0f
    )

    CardCustom(
        modifier = modifier.onGloballyPositioned { coordinates ->
            scrollToPosition.value = coordinates.positionInParent().y
        },
        onClick = {}
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
                    text = "27/02/2022",
                    fontFamily = montserratFontFamily,
                    color = MaterialTheme.colors.greySecondary,
                    fontSize = MaterialTheme.typography.caption.fontSize,
                    overflow = TextOverflow.Ellipsis,
                )

                IconButton(onClick = onReviewClicked) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_review),
                        contentDescription = stringResource(id = R.string.review),
                        tint = MaterialTheme.colors.textColor2
                    )
                }

                IconButton(
                    onClick = {
                        isExpanded = !isExpanded
                        if (isExpanded) onExpanded()
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
                    ).align(Alignment.Start),
                text = "Order ID: 123456789",
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
                        repeat(5) {
                            ProductOrderHistoryItem()
                        }
                    }

                    Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))

                    Text(
                        modifier = Modifier
                            .padding(
                                bottom = SMALL_PADDING
                            ),
                        text = "Sweet Latte",
                        fontFamily = raleway,
                        color = MaterialTheme.colors.greySecondary,
                        fontSize = MaterialTheme.typography.subtitle2.fontSize,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        modifier = Modifier
                            .padding(
                                bottom = SMALL_PADDING
                            ),
                        text = "0365895412",
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
                        text = "12 Robusta Street, Frappe District, White City",
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
                            fontFamily = raleway,
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
                            text = "180.000 VND",
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
                            fontFamily = raleway,
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
                            text = "180.000 VND",
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
                            fontFamily = raleway,
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
                            text = "180.000 VND",
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
                            fontFamily = raleway,
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
                            text = "180.000 VND",
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
                            fontFamily = raleway,
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
                            text = "You paid this bill",
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
                        text = "5 items",
                        fontFamily = montserratFontFamily,
                        color = MaterialTheme.colors.greySecondary,
                        fontSize = MaterialTheme.typography.subtitle2.fontSize,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = "400.000 VND",
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
        onExpanded = {},
        scrollToPosition = remember {
            mutableStateOf(0f)
        },
        onReviewClicked = {}
    )
}