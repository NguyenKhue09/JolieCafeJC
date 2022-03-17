package com.khue.joliecafejp.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.khue.joliecafejp.R


@Composable
fun FacebookSignInButton(
    onClicked: () -> Unit
) {

    IconButton(
        content = {
            Icon(
                modifier = Modifier
                    .height(35.dp)
                    .width(35.dp),
                painter = painterResource(id = R.drawable.ic_facebook_logo),
                contentDescription = "Facebook",
                tint = Color.Unspecified
            )
        },
        onClick = onClicked
    )
}