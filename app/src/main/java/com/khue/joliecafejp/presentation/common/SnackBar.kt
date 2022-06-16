package com.khue.joliecafejp.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.khue.joliecafejp.R
import com.khue.joliecafejp.domain.model.SnackBarData
import com.khue.joliecafejp.ui.theme.*
import com.khue.joliecafejp.utils.Constants.Companion.SNACK_BAR_STATUS_DISABLE
import com.khue.joliecafejp.utils.Constants.Companion.SNACK_BAR_STATUS_ERROR
import com.khue.joliecafejp.utils.Constants.Companion.SNACK_BAR_STATUS_SUCCESS

@Composable
fun SnackBar(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    snackBarData: SnackBarData
) {
    val onSurfaceColor = when (snackBarData.snackBarState) {
        SNACK_BAR_STATUS_SUCCESS -> MaterialTheme.colors.textColor2
        SNACK_BAR_STATUS_DISABLE -> MaterialTheme.colors.darkTextColor
        SNACK_BAR_STATUS_ERROR -> MaterialTheme.colors.error
        else -> MaterialTheme.colors.error
    }
    
    SnackbarHost(
        modifier = modifier,
        hostState = snackbarHostState,
        snackbar = {
            Snackbar(
                backgroundColor = MaterialTheme.colors.textColor,
                action = {
                    TextButton(onClick = { snackbarHostState.currentSnackbarData?.dismiss() }) {
                        Text(
                            text = "OK",
                            style = MaterialTheme.typography.button,
                            color = MaterialTheme.colors.greyPrimary
                        )
                    }
                },
            ) {
                SnackBarContentUI(
                    iconId = snackBarData.iconId,
                    message = snackBarData.message,
                    onSurfaceColor = onSurfaceColor,
                )
            }

        }
    )
}

@Composable
fun SnackBarContentUI(
    modifier: Modifier = Modifier,
    onSurfaceColor: Color,
    message: String,
    iconId: Int,
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = onSurfaceColor
        )
        Spacer(modifier = Modifier.width(EXTRA_EXTRA_SMALL_PADDING))
        Text(
            modifier = modifier.wrapContentHeight(),
            text = message,
            fontFamily = ralewayMedium,
            color = onSurfaceColor,
            fontSize = MaterialTheme.typography.body2.fontSize,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
fun SnackBarContentUIPreview() {
    SnackBarContentUI(
        modifier = Modifier,
        iconId =  R.drawable.ic_success,
        message = "This is a snackbar",
        onSurfaceColor = MaterialTheme.colors.textColor2,
    )
}