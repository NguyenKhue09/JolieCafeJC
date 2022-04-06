package com.khue.joliecafejp.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.khue.joliecafejp.R
import com.khue.joliecafejp.navigation.nav_graph.NONE_ROUTE
import com.khue.joliecafejp.navigation.nav_screen.ProfileSubScreen
import com.khue.joliecafejp.presentation.components.CardCustom
import com.khue.joliecafejp.presentation.components.CustomImage
import com.khue.joliecafejp.ui.theme.*

@Composable
fun ProfileScreen(
    navController: NavHostController
) {

    val profileBottomNavItem = ProfileSubScreen::class.sealedSubclasses.mapNotNull { it.objectInstance }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.greyPrimary),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomImage()
        
        Text(
            text = stringResource(R.string.sweet_latte),
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