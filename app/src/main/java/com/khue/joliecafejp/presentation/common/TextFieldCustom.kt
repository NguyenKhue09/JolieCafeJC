package com.khue.joliecafejp.presentation.common

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.type.Fraction
import com.khue.joliecafejp.ui.theme.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TextFieldCustom(
    modifier: Modifier = Modifier,
    textFieldValue: String,
    onTextChange: (String) -> Unit,
    keyBoardType: KeyboardType,
    imeAction: ImeAction = ImeAction.Next,
    trailingIcon: @Composable() (() -> Unit)?,
    placeHolder: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    error: String = "",
    isError: Boolean = error.isNotEmpty(),
    padding: Dp = 20.dp,
    enabled: Boolean = true,
    fraction: Float  = 1f,
    maxLines: Int = 1,
    singleLine: Boolean = true
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(fraction = fraction)
            .padding(start = padding, end = padding)
    ) {

        val interactionSource = remember { MutableInteractionSource() }
        val colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = MaterialTheme.colors.titleTextColor,
            disabledTextColor = MaterialTheme.colors.textColor,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.White,
            cursorColor = MaterialTheme.colors.textColor,
            textColor = MaterialTheme.colors.textColor,
            placeholderColor = MaterialTheme.colors.textColor
        )

        BasicTextField(
            modifier = modifier
                .fillMaxWidth()
                .indicatorLine(
                    enabled = enabled,
                    isError = isError,
                    interactionSource = interactionSource,
                    colors = colors
                ),
            value = textFieldValue,
            onValueChange = {
                onTextChange(it)
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.body1.fontSize,
                fontFamily = montserratFontFamily,
                color = MaterialTheme.colors.textColor,
            ),
            cursorBrush = SolidColor(MaterialTheme.colors.textColor),
            maxLines = maxLines,
            singleLine = singleLine,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyBoardType,
                imeAction = imeAction
            ),
            keyboardActions = keyboardActions,
            interactionSource = interactionSource,
            visualTransformation = visualTransformation,
            enabled = enabled,
            decorationBox = @Composable { innerTextField ->
                TextFieldDefaults.TextFieldDecorationBox(
                    value = textFieldValue,
                    visualTransformation = visualTransformation,
                    innerTextField = innerTextField,
                    placeholder = {
                        Text(
                            text = placeHolder,
                            fontFamily = raleway,
                            color = MaterialTheme.colors.textColor,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Left,
                        )
                    },
                    singleLine = singleLine,
                    enabled = enabled,
                    interactionSource = interactionSource,
                    contentPadding = PaddingValues(0.dp),
                    isError = isError,
                    trailingIcon = trailingIcon,
                    colors = colors
                )
            }
        )

        if (error.isNotEmpty()) {
            Text(
                text = error,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp, top = 0.dp)
            )
        }

    }

}








//        TextField(
//            modifier = modifier
//                .fillMaxWidth(),
//            value = textFieldValue.value,
//            onValueChange = {
//                textFieldValue.value = it
//            },
//            enabled = enabled,
//            isError = isError,
//            colors = TextFieldDefaults.textFieldColors(
//                backgroundColor = Color.Transparent,
//                focusedIndicatorColor = MaterialTheme.colors.titleTextColor,
//                disabledTextColor = MaterialTheme.colors.textColor,
//                disabledIndicatorColor = Color.Transparent,
//                unfocusedIndicatorColor = Color.White,
//                cursorColor = MaterialTheme.colors.textColor,
//                textColor = MaterialTheme.colors.textColor,
//            ),
//            textStyle = TextStyle(
//                fontSize = MaterialTheme.typography.body1.fontSize,
//                fontFamily = montserratFontFamily
//            ),
//            maxLines = 1,
//            singleLine = true,
//            keyboardOptions = KeyboardOptions(
//                keyboardType = keyBoardType,
//                imeAction = ImeAction.Next
//            ),
//            trailingIcon = trailingIcon ,
//            placeholder = {
//                Text(
//                    text = placeHolder,
//                    fontFamily = raleway,
//                    color = MaterialTheme.colors.textColor,
//                    fontSize = 13.sp,
//                    textAlign = TextAlign.Left,
//                )
//            },
//            visualTransformation = visualTransformation
//        )