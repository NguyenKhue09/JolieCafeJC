package com.khue.joliecafejp.presentation.screens.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.khue.joliecafejp.presentation.viewmodels.LoginViewModel
import com.khue.joliecafejp.ui.theme.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardScreen(
    loginViewModel: LoginViewModel = hiltViewModel()
) {

    var columnHigh by remember {
        mutableStateOf(0.dp)
    }

    var boxHigh by remember {
        mutableStateOf(0.dp)
    }

    Column(
        modifier = Modifier.fillMaxSize().onGloballyPositioned {
            columnHigh = it.size.height.dp
        },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .onGloballyPositioned {
                        boxHigh = (it.size.height.dp)
                    }
                    .height(100.dp)
                    .width(392.dp)
                    .background(color = MaterialTheme.colors.titleTextColor),
            ) {
                Text(text = "Box high 100", color = MaterialTheme.colors.textColor)
            }

            Card(
                modifier = Modifier
                    .size(200.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(Color.Red),
            ) {

            }
        }


        Text(text = "Column high $columnHigh", color = MaterialTheme.colors.textColor)
        Text(text = "Box high $boxHigh", color = MaterialTheme.colors.textColor)
        Text(text = "Density ${LocalDensity.current.density}", color = MaterialTheme.colors.textColor)
    }
}
