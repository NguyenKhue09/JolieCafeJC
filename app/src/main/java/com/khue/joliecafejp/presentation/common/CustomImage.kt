package com.khue.joliecafejp.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.khue.joliecafejp.R
import com.khue.joliecafejp.ui.theme.EXTRA_EXTRA_LARGE_PADDING
import com.khue.joliecafejp.ui.theme.IMAGE_LOGO_SIZE

@Composable
fun CustomImage(
    shape: Shape = CircleShape,
    height: Dp = IMAGE_LOGO_SIZE,
    width: Dp = IMAGE_LOGO_SIZE,
    image: String = "https://example.com/image.jpg",
    contentDescription: Int = R.string.profile_logo,
    paddingValues: PaddingValues = PaddingValues(top = EXTRA_EXTRA_LARGE_PADDING)
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(image)
            .crossfade(200)
            .placeholder(R.drawable.image_logo)
            .error(R.drawable.image_logo)
            .build()
    )


    Image(
        modifier = Modifier
            .padding(paddingValues)
            .height(height)
            .width(width)
            .clip(shape),
        painter = painter,
        contentDescription = stringResource(contentDescription),
        contentScale = ContentScale.Crop,
    )
}