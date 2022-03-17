package com.khue.joliecafejp.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.khue.joliecafejp.R

@Composable
fun FaceOrGoogleLogin(faceAction: () -> Unit) {
    Row(
        modifier = Modifier.padding(top = 32.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FacebookSignInButton {
            faceAction()
        }
        GoogleSignInButton {

        }

    }
}