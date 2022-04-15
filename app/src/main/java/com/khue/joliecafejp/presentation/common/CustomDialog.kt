package com.khue.joliecafejp.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.khue.joliecafejp.R
import com.khue.joliecafejp.ui.theme.*

@Composable
fun CustomDialog(
    title: String,
    content: String,
    onDismiss: () -> Unit,
    onNegativeClick: () -> Unit,
    onPositiveClick: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        CardCustom(
            onClick = {},
            paddingValues = PaddingValues(all = 0.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = EXTRA_LARGE_PADDING, vertical = SMALL_PADDING),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = title,
                        fontFamily = raleway,
                        color = MaterialTheme.colors.titleTextColor,
                        fontSize = MaterialTheme.typography.subtitle2.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(
                        onClick = onDismiss
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_clear),
                            contentDescription = stringResource(
                                R.string.close
                            ),
                            tint = MaterialTheme.colors.titleTextColor
                        )
                    }
                }

                Divider(color = MaterialTheme.colors.textColor, thickness = 1.dp)

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = EXTRA_LARGE_PADDING, vertical = SMALL_PADDING),
                    text = content,
                    fontFamily = raleway,
                    color = MaterialTheme.colors.textColor,
                    fontSize = MaterialTheme.typography.subtitle2.fontSize,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = Modifier
                        .padding(start = EXTRA_LARGE_PADDING, end = EXTRA_LARGE_PADDING, bottom = EXTRA_LARGE_PADDING)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    ButtonCustom(
                        buttonContent = stringResource(id = R.string.delete),
                        backgroundColor = Color.Transparent,
                        textColor = MaterialTheme.colors.errorTextColor,
                        onClick = onNegativeClick,
                        paddingValues = PaddingValues(top = 0.dp),
                        contentPadding = PaddingValues(
                            horizontal = EXTRA_LARGE_PADDING,
                            vertical = MEDIUM_SMALL_PADDING
                        ),
                        buttonElevation = ButtonDefaults.elevation(
                            defaultElevation = 0.dp,
                            pressedElevation = 0.dp
                        )
                    )
                    ButtonCustom(
                        buttonContent = stringResource(id = R.string.cancel),
                        backgroundColor = MaterialTheme.colors.titleTextColor,
                        textColor = MaterialTheme.colors.textColor,
                        onClick = onPositiveClick,
                        paddingValues = PaddingValues(
                            start = EXTRA_LARGE_PADDING,
                            top = 0.dp
                        ),
                        contentPadding = PaddingValues(
                            horizontal = EXTRA_LARGE_PADDING,
                            vertical = MEDIUM_SMALL_PADDING
                        ),
                        buttonElevation = null
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CustomDialogPrev() {
    CustomDialog(
        "Delete address?",
        "Delete address? Delete address? Delete address?",
        {},
        {},
        {}
    )
}