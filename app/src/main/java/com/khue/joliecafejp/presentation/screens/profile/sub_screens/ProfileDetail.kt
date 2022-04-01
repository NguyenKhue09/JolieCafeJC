package com.khue.joliecafejp.presentation.screens.profile.sub_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.khue.joliecafejp.R
import com.khue.joliecafejp.navigation.nav_screen.ProfileSubScreen
import com.khue.joliecafejp.presentation.components.CircleImage
import com.khue.joliecafejp.ui.theme.*

@Composable
fun ProfileDetail(
    navController: NavHostController
) {
    Scaffold(
        backgroundColor = MaterialTheme.colors.greyPrimary,
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = stringResource(
                            id = ProfileSubScreen.ProfileDetail.titleId
                        )
                    )
                }
                Text(
                    text = stringResource(
                        id = ProfileSubScreen.ProfileDetail.titleId
                    ),
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    fontFamily = ralewayMedium,
                    color = MaterialTheme.colors.titleTextColor
                )
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.BottomEnd
            ) {
                CircleImage()
                IconButton(
                    modifier = Modifier
                        .offset(ICON_BUTTON_PROFILE_OFFSET, ICON_BUTTON_PROFILE_OFFSET)
                        .size(ICON_BUTTON_PROFILE_DETAIL_SIZE)
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colors.greySecondary),
                    onClick = { }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_camera),
                        contentDescription = stringResource(R.string.image_picker)
                    )
                }
            }
        }
    }
}