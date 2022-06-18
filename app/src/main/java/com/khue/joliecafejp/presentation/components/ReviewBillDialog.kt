package com.khue.joliecafejp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.khue.joliecafejp.R
import com.khue.joliecafejp.presentation.common.ButtonCustom
import com.khue.joliecafejp.presentation.common.CardCustom
import com.khue.joliecafejp.presentation.common.rating_bar.CustomRatingBar
import com.khue.joliecafejp.presentation.common.rating_bar.RatingBarConfig
import com.khue.joliecafejp.presentation.common.rating_bar.RatingBarStyle
import com.khue.joliecafejp.ui.theme.*

@Composable
fun ReviewBillDialog(
    onDismiss: () -> Unit,
    onNegativeClick: () -> Unit,
    onPositiveClick: () -> Unit
) {
    var rating: Float by rememberSaveable { mutableStateOf(4f) }

    Dialog(onDismissRequest = onDismiss) {
        CardCustom(
            onClick = null,
            paddingValues = PaddingValues(all = ZERO_PADDING)
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
                        text = "Review Bill",
                        fontFamily = ralewayMedium,
                        color = MaterialTheme.colors.titleTextColor,
                        fontSize = MaterialTheme.typography.h6.fontSize,
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
                    text = stringResource(R.string.rating),
                    fontFamily = ralewayMedium,
                    color = MaterialTheme.colors.textColor,
                    fontSize = MaterialTheme.typography.body1.fontSize,
                )

                CustomRatingBar(
                    value = rating,
                    onValueChange = {
                        rating = it
                    },
                    onRatingChanged = {},
                    config = RatingBarConfig()
                        .style(RatingBarStyle.HighLighted)
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = EXTRA_LARGE_PADDING, vertical = SMALL_PADDING),
                    text = stringResource(R.string.comment),
                    fontFamily = ralewayMedium,
                    color = MaterialTheme.colors.textColor,
                    fontSize = MaterialTheme.typography.body1.fontSize,
                )

                OutlinedTextField(
                    value = "",
                    onValueChange = {

                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = MaterialTheme.colors.titleTextColor,
                        disabledTextColor = MaterialTheme.colors.textColor,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.White,
                        cursorColor = MaterialTheme.colors.textColor,
                        textColor = MaterialTheme.colors.textColor,
                        placeholderColor = MaterialTheme.colors.textColor
                    ),
                    placeholder = {
                        Text(
                            text = "Leave a comment",
                            fontFamily = ralewayMedium,
                            color = MaterialTheme.colors.darkTextColor,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Left,
                        )
                    },
                )

                Spacer(modifier = Modifier.height(MEDIUM_PADDING))

                Row(
                    modifier = Modifier
                        .padding(
                            start = EXTRA_LARGE_PADDING,
                            end = EXTRA_LARGE_PADDING,
                            bottom = EXTRA_LARGE_PADDING
                        )
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    ButtonCustom(
                        buttonContent = stringResource(id = R.string.cancel),
                        backgroundColor = Color.Transparent,
                        textColor = MaterialTheme.colors.textColor2,
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
                        buttonContent = stringResource(R.string.post),
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
fun ReviewBillDialogPrev() {
    ReviewBillDialog(
        {},
        {},
        {}
    )
}