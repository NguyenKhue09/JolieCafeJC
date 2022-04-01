package com.khue.joliecafejp.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.khue.joliecafejp.navigation.nav_graph.AUTHENTICATION_ROUTE
import com.khue.joliecafejp.presentation.screens.login.LoginViewModel
import com.khue.joliecafejp.ui.theme.greyPrimary
import com.khue.joliecafejp.viewmodels.HomeViewModel


@Composable
fun HomeScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val user by loginViewModel.user.collectAsState()

    LaunchedEffect(user){
        println("Home $user")
        if (user == null) {
            navController.navigate(AUTHENTICATION_ROUTE) {
                launchSingleTop = true
            }
        }
    }

    val isEdit = homeViewModel.isEdit.collectAsState()

    val count = loginViewModel.count.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.greyPrimary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.clickable {
                loginViewModel.increaseCount()
            },
            text = count.value.toString(),
            fontSize = MaterialTheme.typography.h3.fontSize,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }

}