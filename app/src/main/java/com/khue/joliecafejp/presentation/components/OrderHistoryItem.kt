package com.khue.joliecafejp.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khue.joliecafejp.R
import com.khue.joliecafejp.ui.theme.*

@Composable
fun OrderHistoryItem() {

    var isExpanded by remember {
        mutableStateOf(false)
    }

    val angle: Float by animateFloatAsState(
        targetValue = if (!isExpanded) 180f else 0f
    )

    CardCustom(onClick = {}) {
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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "27/02/2022",
                    fontFamily = montserratFontFamily,
                    color = MaterialTheme.colors.greySecondary,
                    fontSize = MaterialTheme.typography.caption.fontSize,
                    overflow = TextOverflow.Ellipsis,
                )

                IconButton(
                    onClick = {
                        isExpanded = !isExpanded
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

            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {

                    LazyColumn(
                        modifier = Modifier.height(150.dp)
                    ) {
                        repeat(5) {
                            item {
                                ProductOrderHistoryItem()
                            }
                        }
                    }

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
    OrderHistoryItem()
}