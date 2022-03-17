package com.khue.joliecafejp.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khue.joliecafejp.ui.theme.raleway
import com.khue.joliecafejp.ui.theme.textColor
import com.khue.joliecafejp.ui.theme.titleTextColor

@Composable
fun TextFieldCustom(
    modifier: Modifier,
    textFieldValue: MutableState<TextFieldValue>,
    keyBoardType: KeyboardType,
    trailingIcon: @Composable() (() -> Unit)?,
    placeHolder: String,
    visualTransformation: VisualTransformation,
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp),
        value = textFieldValue.value,
        onValueChange = {
            textFieldValue.value = it
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = MaterialTheme.colors.titleTextColor,
            cursorColor = MaterialTheme.colors.textColor,
            textColor = MaterialTheme.colors.textColor,
        ),
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyBoardType,
            imeAction = ImeAction.Next
        ),
        trailingIcon = trailingIcon,
        placeholder = {
            Text(
                text = placeHolder,
                fontFamily = raleway,
                color = MaterialTheme.colors.textColor,
                fontSize = 13.sp,
                textAlign = TextAlign.Left,
            )
        },
        visualTransformation = visualTransformation
    )
}