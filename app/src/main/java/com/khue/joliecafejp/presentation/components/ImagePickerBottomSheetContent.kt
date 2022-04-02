package com.khue.joliecafejp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.khue.joliecafejp.R
import com.khue.joliecafejp.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImagePickerBottomSheetContent(
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState
) {

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colors.greyOpacity60Primary),
        verticalArrangement = Arrangement.Top,
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
                text = stringResource(R.string.action),
                fontFamily = raleway,
                color = MaterialTheme.colors.textColor,
                fontSize = MaterialTheme.typography.subtitle2.fontSize,
                fontWeight = FontWeight.Bold
            )
            IconButton(
                onClick = {
                    coroutineScope.launch { modalBottomSheetState.hide() }
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_clear),
                    contentDescription = stringResource(
                        R.string.close
                    ),
                    tint = MaterialTheme.colors.textColor
                )
            }
        }

        Divider(color = MaterialTheme.colors.textColor, thickness = 1.dp)


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = EXTRA_LARGE_PADDING),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                modifier = Modifier.padding(
                    vertical = MEDIUM_PADDING,
                    horizontal = EXTRA_LARGE_PADDING
                ),
                painter = painterResource(id = R.drawable.ic_gallery),
                contentDescription = stringResource(R.string.gallery),
                tint = MaterialTheme.colors.textColor
            )
            Text(
                text = stringResource(R.string.choose_from_gallery),
                color = MaterialTheme.colors.textColor,
                fontFamily = ralewayMedium,
                fontSize = MaterialTheme.typography.body1.fontSize
            )
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = EXTRA_LARGE_PADDING, vertical = SMALL_PADDING),
            color = MaterialTheme.colors.textColor,
            thickness = 0.5.dp
        )

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
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = stringResource(R.string.camera),
                tint = MaterialTheme.colors.textColor
            )
            Text(
                text = stringResource(R.string.camera),
                color = MaterialTheme.colors.textColor,
                fontFamily = ralewayMedium,
                fontSize = MaterialTheme.typography.body1.fontSize
            )
        }

        Button(
            modifier = Modifier
                .padding(horizontal = EXTRA_LARGE_PADDING, vertical = EXTRA_LARGE_PADDING)
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.textColor
            ),
            contentPadding = PaddingValues(all = EXTRA_SMALL_PADDING),
            onClick = {
                coroutineScope.launch { modalBottomSheetState.hide() }
            }
        ) {
            Text(
                text = stringResource(R.string.cancel),
                fontFamily = ralewayMedium,
                fontSize = MaterialTheme.typography.subtitle1.fontSize,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.buttonTextColor
            )
        }
    }

}