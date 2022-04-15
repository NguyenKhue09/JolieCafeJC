package com.khue.joliecafejp.presentation.screens.profile.sub_screens

import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.khue.joliecafejp.navigation.nav_screen.ProfileSubScreen
import com.khue.joliecafejp.presentation.components.OrderHistoryItem
import com.khue.joliecafejp.presentation.common.TopBar
import com.khue.joliecafejp.ui.theme.greyPrimary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OrderHistory(
    navController: NavHostController
) {

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val scrollToPosition  = remember { mutableStateOf(0F) }

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
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(state = scrollState)
        ) {
            repeat(5) {
                OrderHistoryItem(
                    scrollToPosition = scrollToPosition
                ) {
                    coroutineScope.launch {
                        delay(500L)
                        scrollState.animateScrollBy(scrollToPosition.value)
                    }
                }
            }
        }
    }
}