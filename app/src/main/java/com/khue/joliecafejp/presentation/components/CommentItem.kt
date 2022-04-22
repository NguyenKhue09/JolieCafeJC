package com.khue.joliecafejp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.khue.joliecafejp.R
import com.khue.joliecafejp.presentation.common.CustomImage
import com.khue.joliecafejp.ui.theme.*

@Composable
fun CommentItem(
    name: String = "Latte",
    rating: Int = 5,
    content: String = "Typically composed of a single espresso shot and hot milk, with the surface topped with foamed..."
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            CustomImage(
                height = IMAGE_LOGO_COMMENT_SIZE,
                width = IMAGE_LOGO_COMMENT_SIZE,
                paddingValues = PaddingValues(all = ZERO_PADDING)
            )
            Spacer(modifier = Modifier.width(SMALL_PADDING))
            Text(
                modifier = Modifier.weight(1f, fill = false),
                text = name,
                fontFamily = raleway,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.textColor,
                fontSize = MaterialTheme.typography.subtitle2.fontSize,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier.padding(horizontal = EXTRA_EXTRA_SMALL_PADDING),
                text = "•",
                fontFamily = raleway,
                color = MaterialTheme.colors.textColor,
                fontSize = MaterialTheme.typography.subtitle2.fontSize,
            )
            Text(
                text = rating.toString(),
                color = MaterialTheme.colors.textColor,
                fontFamily = montserratFontFamily,
                fontSize = MaterialTheme.typography.subtitle2.fontSize,
            )
            Spacer(modifier = Modifier.width(EXTRA_EXTRA_SMALL_PADDING))
            Icon(
                modifier = Modifier.size(PRODUCT_RATING_SIZE),
                painter = painterResource(id = R.drawable.ic_favorite),
                contentDescription = stringResource(id = R.string.favorite),
                tint = MaterialTheme.colors.textColor
            )
        }
        Spacer(modifier = Modifier.height(MEDIUM_SMALL_PADDING))
        Text(
            text = content,
            fontFamily = raleway,
            color = MaterialTheme.colors.textColor,
            fontSize = MaterialTheme.typography.caption.fontSize
        )
    }
}

@Preview
@Composable
fun CommentItemPrev() {
    CommentItem()
}