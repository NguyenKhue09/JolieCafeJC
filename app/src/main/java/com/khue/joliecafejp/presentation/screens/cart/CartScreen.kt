package com.khue.joliecafejp.presentation.screens.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.khue.joliecafejp.presentation.viewmodels.CartViewModel
import com.khue.joliecafejp.presentation.viewmodels.UserSharedViewModel
import com.khue.joliecafejp.ui.theme.textColor
import com.khue.joliecafejp.ui.theme.titleTextColor

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CartScreen(
    userSharedViewModel: UserSharedViewModel,
    cartViewModel: CartViewModel = hiltViewModel()
) {

    var columnHigh by remember {
        mutableStateOf(0.dp)
    }

    var boxHigh by remember {
        mutableStateOf(0.dp)
    }

//    val transition = rememberInfiniteTransition()
//    val alphaAnim by transition.animateFloat(
//        initialValue = 1f,
//        targetValue = 0f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(
//                durationMillis = 500,
//                easing = FastOutLinearInEasing
//            ),
//            repeatMode = RepeatMode.Reverse
//        )
//    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned {
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

        //println(alphaAnim)
//        Surface(
//            modifier = Modifier
//                .alpha(alpha = alphaAnim)
//                .fillMaxWidth(0.4f)
//                .height(20.dp),
//            color = if (isSystemInDarkTheme())
//                MaterialTheme.colors.ShimmerDarkGray else MaterialTheme.colors.ShimmerMediumGray,
//            shape = MaterialTheme.shapes.medium
//        ) {}

        Text(text = "Column high $columnHigh", color = MaterialTheme.colors.textColor)
        Text(text = "Box high $boxHigh", color = MaterialTheme.colors.textColor)
        Text(text = "Density ${LocalDensity.current.density}", color = MaterialTheme.colors.textColor)
    }
}
