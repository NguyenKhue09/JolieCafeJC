package com.khue.joliecafejp.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.khue.joliecafejp.ui.theme.greyPrimary


@Composable
fun HomeScreen(navController: NavHostController) {

    LaunchedEffect(key1 = true) {

    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.greyPrimary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Home",
            fontSize = MaterialTheme.typography.h3.fontSize,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}