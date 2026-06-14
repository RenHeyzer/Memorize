package dev.renheyzer.memorize.core.ui.component

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldLabelPosition
import androidx.compose.material3.TextFieldLabelScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import dev.renheyzer.memorize.core.ui.UiText
import dev.renheyzer.memorize.core.ui.asString
import dev.renheyzer.memorize.ui.theme.MemorizeTheme

@Composable
fun MemorizeOutlinedTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = MemorizeTheme.typography.body.copy(textAlign = TextAlign.Center),
    labelPosition: TextFieldLabelPosition = TextFieldLabelPosition.Attached(),
    label: @Composable (TextFieldLabelScope.() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    errorMessage: UiText? = null,
    inputTransformation: InputTransformation? = null,
    outputTransformation: OutputTransformation? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
    lineLimits: TextFieldLineLimits = TextFieldLineLimits.Default,
    onTextLayout: (Density.(getResult: () -> TextLayoutResult?) -> Unit)? = null,
    scrollState: ScrollState = rememberScrollState(),
    contentPadding: PaddingValues = OutlinedTextFieldDefaults.contentPadding(),
    interactionSource: MutableInteractionSource? = null,
) {
    OutlinedTextField(
        state = state,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        labelPosition = labelPosition,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        prefix = prefix,
        suffix = suffix,
        supportingText = {
            if (isError) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = errorMessage?.asString() ?: "",
                    color = MemorizeTheme.colors.errorColor,
                    style = MemorizeTheme.typography.body
                )
            }
        },
        isError = isError,
        inputTransformation = inputTransformation,
        outputTransformation = outputTransformation,
        keyboardOptions = keyboardOptions,
        onKeyboardAction = onKeyboardAction,
        lineLimits = lineLimits,
        onTextLayout = onTextLayout,
        scrollState = scrollState,
        shape = MemorizeTheme.shape.textField,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = MemorizeTheme.colors.borderColor,
            focusedBorderColor = MemorizeTheme.colors.accentColor,
            unfocusedLabelColor = MemorizeTheme.colors.primaryText,
            focusedLabelColor = MemorizeTheme.colors.accentColor,
            errorBorderColor = MemorizeTheme.colors.errorColor,
            errorLabelColor = MemorizeTheme.colors.errorColor,
            cursorColor = MemorizeTheme.colors.accentColor
        ),
        contentPadding = contentPadding,
        interactionSource = interactionSource
    )
}