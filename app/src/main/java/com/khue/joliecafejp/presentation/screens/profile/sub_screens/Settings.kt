package com.khue.joliecafejp.presentation.screens.profile.sub_screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.khue.joliecafejp.R
import com.khue.joliecafejp.navigation.nav_screen.ProfileSubScreen
import com.khue.joliecafejp.presentation.common.CardCustom
import com.khue.joliecafejp.presentation.common.TopBar
import com.khue.joliecafejp.ui.theme.*

@Composable
fun Settings(
    navHostController: NavHostController
) {

    val turnOffNotification = remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.greyPrimary,
        topBar = {
            TopBar(
                titleId = ProfileSubScreen.Settings.titleId,
                navController = navHostController
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NotificationCard(turnOffNotification = turnOffNotification)
            Spacer(modifier = Modifier.height(height = EXTRA_LARGE_PADDING))
            LanguageCard()
        }

    }
}

@Composable
fun NotificationCard(
    turnOffNotification: MutableState<Boolean>
) {
    CardCustom(onClick = null) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = EXTRA_LARGE_PADDING, end = EXTRA_LARGE_PADDING, top = SMALL_PADDING, bottom = SMALL_PADDING),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.weight(1f),
                painter = painterResource(id = R.drawable.ic_notification),
                contentDescription = stringResource(R.string.notification),
                tint = MaterialTheme.colors.textColor
            )
            Text(
                modifier = Modifier
                    .padding(
                        start = SMALL_PADDING
                    )
                    .weight(8f),
                text = stringResource(id = R.string.notification),
                fontFamily = ralewayMedium,
                color = MaterialTheme.colors.textColor,
                fontSize = MaterialTheme.typography.body1.fontSize,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                textAlign = TextAlign.Start
            )

            Switch(
                modifier = Modifier.weight(1f),
                checked = turnOffNotification.value,
                onCheckedChange = { turnOff ->
                    turnOffNotification.value = turnOff
                },
                colors = SwitchDefaults.colors(
                    uncheckedThumbColor = MaterialTheme.colors.textColor,
                    checkedThumbColor = MaterialTheme.colors.titleTextColor
                )
            )
        }
    }
}

@Composable
fun LanguageCard(
) {
    CardCustom(onClick = null) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = EXTRA_LARGE_PADDING),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.weight(1f),
                painter = painterResource(id = R.drawable.ic_globe),
                contentDescription = stringResource(R.string.notification),
                tint = MaterialTheme.colors.textColor
            )
            Text(
                modifier = Modifier
                    .padding(
                        start = SMALL_PADDING
                    )
                    .weight(8f),
                text = stringResource(R.string.language),
                fontFamily = ralewayMedium,
                color = MaterialTheme.colors.textColor,
                fontSize = MaterialTheme.typography.body1.fontSize,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                textAlign = TextAlign.Start
            )

            Text(
                modifier = Modifier
                    .weight(1f),
                text = "EN",
                fontFamily = ralewayMedium,
                color = MaterialTheme.colors.titleTextColor,
                fontSize = MaterialTheme.typography.body1.fontSize,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                textAlign = TextAlign.Start
            )

        }
    }
}

@Preview
@Composable
fun NotificationCardPrev() {
    val turnOffNotification = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NotificationCard(turnOffNotification = turnOffNotification)
        Spacer(modifier = Modifier.height(height = EXTRA_LARGE_PADDING))
        LanguageCard()
    }
}