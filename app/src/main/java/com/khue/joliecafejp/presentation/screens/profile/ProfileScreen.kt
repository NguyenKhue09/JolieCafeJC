package com.khue.joliecafejp.presentation.screens.profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.khue.joliecafejp.R
import com.khue.joliecafejp.navigation.nav_graph.AUTHENTICATION_ROUTE
import com.khue.joliecafejp.navigation.nav_graph.NONE_ROUTE
import com.khue.joliecafejp.navigation.nav_screen.BottomBarScreen
import com.khue.joliecafejp.navigation.nav_screen.ProfileSubScreen
import com.khue.joliecafejp.presentation.common.CardCustom
import com.khue.joliecafejp.presentation.common.CustomImage
import com.khue.joliecafejp.presentation.viewmodels.LoginViewModel
import com.khue.joliecafejp.ui.theme.*

@Composable
fun ProfileScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel
) {

    val userLoginResponse = loginViewModel.userLoginResponse.collectAsState()
    val userToken by loginViewModel.userToken.collectAsState(initial = "")

    val profileBottomNavItem =
        ProfileSubScreen::class.sealedSubclasses.mapNotNull { it.objectInstance }


    LaunchedEffect(key1 = true) {
        if(userLoginResponse.value.data == null) loginViewModel.getUserInfos(token = userToken)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.greyPrimary),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CustomImage(
            image = userLoginResponse.value.data?.thumbnail ?: FirebaseAuth.getInstance().currentUser?.photoUrl.toString()
        )

        Text(
            text = userLoginResponse.value.data?.fullName ?: stringResource(R.string.sweet_latte),
            modifier = Modifier.padding(top = LARGE_PADDING, bottom = LARGE_PADDING),
            fontSize = MaterialTheme.typography.body1.fontSize,
            fontFamily = ralewayMedium,
            color = MaterialTheme.colors.textColor,
        )

        profileBottomNavItem.forEach { item ->
            CardCustom(
                onClick = {
                    if (item.route != NONE_ROUTE) {
                        navController.navigate(item.route) {
                            launchSingleTop = true
                        }
                    } else {
                        FirebaseAuth.getInstance().signOut()
                        loginViewModel.signOut()
                        navController.navigate(AUTHENTICATION_ROUTE) {
                            popUpTo(BottomBarScreen.Home.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        modifier = Modifier.padding(
                            vertical = MEDIUM_PADDING,
                            horizontal = EXTRA_LARGE_PADDING
                        ),
                        painter = painterResource(id = item.iconId),
                        contentDescription = stringResource(id = item.titleId),
                        tint = MaterialTheme.colors.textColor
                    )
                    Text(
                        text = stringResource(id = item.titleId),
                        color = MaterialTheme.colors.textColor,
                        fontFamily = ralewayMedium,
                        fontSize = MaterialTheme.typography.body1.fontSize
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ProfileScreenPrev() {
}