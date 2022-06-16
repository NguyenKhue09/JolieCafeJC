package com.khue.joliecafejp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khue.joliecafejp.domain.model.CartItemByCategory
import com.khue.joliecafejp.presentation.common.CardCustom
import com.khue.joliecafejp.ui.theme.EXTRA_LARGE_PADDING
import com.khue.joliecafejp.ui.theme.raleway
import com.khue.joliecafejp.ui.theme.textColor
import com.khue.joliecafejp.ui.theme.titleTextColor

@Composable
fun CartProductGroup(
    cartItemByCategory: CartItemByCategory,
    isSelectedGroup: Boolean,
    onSelectedGroup: (Boolean, String) -> Unit,
) {
    CardCustom(
        paddingValues = PaddingValues(
            top = EXTRA_LARGE_PADDING,
            start = 0.dp,
            end = 0.dp,
            bottom = 0.dp
        ),
        onClick = null
    ) {
        Column(
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Checkbox(
                    checked = isSelectedGroup,
                    onCheckedChange = {
                        onSelectedGroup(it, "")
                    },
                    colors = CheckboxDefaults.colors(
                        uncheckedColor = MaterialTheme.colors.textColor,
                        checkedColor = MaterialTheme.colors.titleTextColor,
                        disabledColor = MaterialTheme.colors.titleTextColor
                    )
                )
                Text(
                    modifier = Modifier
                        .wrapContentSize(align = Alignment.CenterStart),
                    text = cartItemByCategory.type,
                    fontFamily = raleway,
                    color = MaterialTheme.colors.textColor,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
            }
            repeat(cartItemByCategory.products.size) { index ->
                CartProductItem(
                    cartItem = cartItemByCategory.products[index]
                ) {}

            }
        }
    }
}

@Preview
@Composable
fun CartProductGroupPrev() {
    CartProductGroup(
        cartItemByCategory = CartItemByCategory(
            type = "Coffee",
            products = emptyList()
        ),
        onSelectedGroup = { isSelected, group ->

        },
        isSelectedGroup = false
    )
}