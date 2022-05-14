package com.khue.joliecafejp.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.khue.joliecafejp.R
import com.khue.joliecafejp.domain.model.User
import com.khue.joliecafejp.presentation.common.CustomImage
import com.khue.joliecafejp.presentation.common.TextCustom
import com.khue.joliecafejp.ui.theme.*
import com.khue.joliecafejp.utils.ApiResult

@Composable
fun HomeTopBar(
    userLoginResponse: ApiResult<User>,
    onNotificationClick: () -> Unit,
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CustomImage(
            paddingValues = PaddingValues(
                horizontal = EXTRA_LARGE_PADDING,
                vertical = LARGE_PADDING
            ),
            height = IMAGE_USER_HOME_SIZE,
            width = IMAGE_USER_HOME_SIZE,
            image = userLoginResponse.data?.thumbnail ?: FirebaseAuth.getInstance().currentUser?.photoUrl.toString()
        )

        AnimatedVisibility(visible = userLoginResponse !is ApiResult.Loading,  modifier = Modifier.weight(1f)) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                TextCustom(
                    content = stringResource(R.string.user_name, userLoginResponse.data?.fullName ?: "You"),
                    modifier = Modifier,
                    color = MaterialTheme.colors.textColor2,
                    fontFamily = ralewayMedium
                )
                Text(
                    text = stringResource(R.string.user_coins, userLoginResponse.data?.coins ?: 0),
                    color = MaterialTheme.colors.textColor,
                    fontFamily = montserratFontFamily,
                    fontSize = MaterialTheme.typography.caption.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        if(userLoginResponse is ApiResult.Loading) {
            ShimmerEffectProfile(modifier = Modifier.weight(1f))
        }

        IconButton(
            onClick = onNotificationClick,
            modifier = Modifier.padding(end = SMALL_PADDING)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_notification),
                contentDescription = stringResource(R.string.notifications),
                tint = MaterialTheme.colors.textColor
            )
        }
    }
}



@Composable
fun ShimmerEffectProfile(modifier: Modifier) {

    val transition = rememberInfiniteTransition()
    val alphaAnim by transition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 500,
                easing = FastOutLinearInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Surface(
            modifier = Modifier
                .alpha(alpha = alphaAnim)
                .fillMaxWidth(0.4f)
                .height(20.dp),
            color = if (isSystemInDarkTheme())
                MaterialTheme.colors.ShimmerDarkGray else MaterialTheme.colors.ShimmerMediumGray,
            shape = MaterialTheme.shapes.medium
        ) {}
        Spacer(modifier = Modifier.height(EXTRA_EXTRA_SMALL_PADDING))
        Surface(
            modifier = Modifier
                .alpha(alpha = alphaAnim)
                .fillMaxWidth(0.3f)
                .height(17.dp),
            color = if (isSystemInDarkTheme())
                MaterialTheme.colors.ShimmerDarkGray else MaterialTheme.colors.ShimmerMediumGray,
            shape = MaterialTheme.shapes.medium
        ) {}
    }
}


@Preview
@Composable
fun HomeTopBarPrev() {
    HomeTopBar(userLoginResponse = ApiResult.Loading()) {
    }
}