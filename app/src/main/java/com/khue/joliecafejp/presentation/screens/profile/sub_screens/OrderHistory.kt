package com.khue.joliecafejp.presentation.screens.profile.sub_screens

import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.khue.joliecafejp.ui.theme.EXTRA_LARGE_PADDING
import com.khue.joliecafejp.ui.theme.greyPrimary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OrderHistory(
    navController: NavHostController
) {

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val scrollToPosition = remember { mutableStateOf(0F) }

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
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(EXTRA_LARGE_PADDING),
            contentPadding = PaddingValues(
                all = EXTRA_LARGE_PADDING,
            )
        ) {
            repeat(5) {
                item {
                    OrderHistoryItem(
                        scrollToPosition = scrollToPosition,
                        onExpanded = {
                            coroutineScope.launch {
                                delay(500L)
                                scrollState.animateScrollBy(scrollToPosition.value)
                            }
                        },
                        onReviewClicked = {}
                    )
                }
            }
        }
    }
}