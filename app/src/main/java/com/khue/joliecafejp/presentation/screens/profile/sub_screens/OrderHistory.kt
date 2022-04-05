package com.khue.joliecafejp.presentation.screens.profile.sub_screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.khue.joliecafejp.R
import com.khue.joliecafejp.navigation.nav_screen.ProfileSubScreen
import com.khue.joliecafejp.ui.theme.greyPrimary
import com.khue.joliecafejp.ui.theme.ralewayMedium
import com.khue.joliecafejp.ui.theme.textColor
import com.khue.joliecafejp.ui.theme.titleTextColor

@Composable
fun OrderHistory(
    navController: NavHostController
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
                            id = ProfileSubScreen.OrderHistory.titleId
                        ),
                        tint = MaterialTheme.colors.textColor
                    )
                }
                Text(
                    text = stringResource(
                        id = ProfileSubScreen.AddressBook.titleId
                    ),
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    fontFamily = ralewayMedium,
                    color = MaterialTheme.colors.titleTextColor
                )
            }
        },
    ) {}
}