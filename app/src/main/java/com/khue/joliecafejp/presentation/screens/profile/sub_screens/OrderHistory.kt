package com.khue.joliecafejp.presentation.screens.profile.sub_screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.khue.joliecafejp.R
import com.khue.joliecafejp.navigation.nav_screen.ProfileSubScreen
import com.khue.joliecafejp.presentation.components.OrderHistoryItem
import com.khue.joliecafejp.presentation.components.ProductOrderHistoryItem
import com.khue.joliecafejp.presentation.components.TopBar
import com.khue.joliecafejp.ui.theme.greyPrimary
import com.khue.joliecafejp.ui.theme.ralewayMedium
import com.khue.joliecafejp.ui.theme.textColor
import com.khue.joliecafejp.ui.theme.titleTextColor

@Composable
fun OrderHistory(
    navController: NavHostController
) {

    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.greyPrimary,
        topBar = {
            TopBar(
                titleId = ProfileSubScreen.OrderHistory.titleId,
                navController = navController
            )
        },
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(state = scrollState)
        ) {
            repeat(5) {
                OrderHistoryItem()
            }
        }
    }
}