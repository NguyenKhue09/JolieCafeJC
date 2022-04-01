package com.khue.joliecafejp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.khue.joliecafejp.R
import com.khue.joliecafejp.ui.theme.EXTRA_EXTRA_LARGE_PADDING
import com.khue.joliecafejp.ui.theme.IMAGE_LOGO_SIZE

@Composable
fun CircleImage() {
    Image(
        modifier = Modifier
            .padding(top = EXTRA_EXTRA_LARGE_PADDING)
            .size(IMAGE_LOGO_SIZE)
            .clip(CircleShape),
        painter = painterResource(id = R.drawable.image_logo),
        contentDescription = stringResource(R.string.profile_logo),
        contentScale = ContentScale.Crop,
    )
}