package com.khue.joliecafejp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.khue.joliecafejp.R
import com.khue.joliecafejp.ui.theme.EXTRA_EXTRA_LARGE_PADDING
import com.khue.joliecafejp.ui.theme.IMAGE_LOGO_SIZE

@Composable
fun CustomImage(
    shape: Shape = CircleShape,
    height: Dp = IMAGE_LOGO_SIZE,
    width: Dp = IMAGE_LOGO_SIZE,
    image: Int = R.drawable.image_logo,
    contentDescription: Int = R.string.profile_logo,
    paddingValues: PaddingValues = PaddingValues(top = EXTRA_EXTRA_LARGE_PADDING)
) {
    Image(
        modifier = Modifier
            .padding(paddingValues)
            .height(height)
            .width(width)
            .clip(shape),
        painter = painterResource(id = image),
        contentDescription = stringResource(contentDescription),
        contentScale = ContentScale.Crop,
    )
}