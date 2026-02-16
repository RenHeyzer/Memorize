package dev.renheyzer.memorize.core.ui.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import dev.renheyzer.memorize.ui.theme.MemorizeTheme

@Composable
fun OutlinedErrorTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    errorText: String,
    isError: Boolean = errorText.isNotBlank(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource? = null,
    shape: Shape = OutlinedTextFieldDefaults.shape,
) {
    Column(modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .widthIn(min = 280.dp, max = 488.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            isError = isError,
            label = label,
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            prefix = prefix,
            suffix = suffix,
            supportingText = supportingText,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            interactionSource = interactionSource,
            shape = shape,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MemorizeTheme.colors.borderColor,
                focusedBorderColor = MemorizeTheme.colors.borderColor,
                unfocusedLabelColor = MemorizeTheme.colors.secondaryText,
                focusedLabelColor = MemorizeTheme.colors.primaryText,
                errorBorderColor = MemorizeTheme.colors.errorColor,
                errorLabelColor = MemorizeTheme.colors.errorColor,
            )
        )
        if (isError) {
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = errorText,
                color = MemorizeTheme.colors.errorColor,
                style = MemorizeTheme.typography.body
            )
        }
    }
}