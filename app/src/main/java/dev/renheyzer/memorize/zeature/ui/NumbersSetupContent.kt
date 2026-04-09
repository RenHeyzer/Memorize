package dev.renheyzer.memorize.zeature.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.core.ui.component.MemorizeActionButton
import dev.renheyzer.memorize.ui.theme.MemorizeTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NumbersSetupContent(
    modifier: Modifier = Modifier,
    quantity: Int,
    rememberTimeMin: Int,
    rememberTimeSec: Int,
    isBinary: Boolean,
    isStartButtonEnabled: Boolean,
    onQuantityChanged: (Int) -> Unit,
    onRememberTimeChanged: (min: Int, sec: Int) -> Unit,
    onBinaryToggled: (Boolean) -> Unit,
    onStartClicked: () -> Unit,
) {
    val quantityState = rememberTextFieldState()
    val rememberTimeState =
        rememberTextFieldState(initialText = "$rememberTimeMin:$rememberTimeSec")

    LaunchedEffect(quantityState) {
        snapshotFlow { quantityState.text }
            .collectLatest { newText ->
                val enteredQuantity = newText.toString().ifBlank { "0" }

                if (enteredQuantity != quantity.toString()) {
                    onQuantityChanged(enteredQuantity.toInt())
                }
            }
    }

    LaunchedEffect(quantity) {
        if (quantity != 0 && quantityState.text.toString() != quantity.toString()) {
            quantityState.setTextAndPlaceCursorAtEnd(quantity.toString())
        }
    }

    LaunchedEffect(rememberTimeState) {
        snapshotFlow { rememberTimeState.text }
            .collectLatest { newText ->
                val enteredRememberTime = newText.toString().ifBlank { "00:00" }
                val timeMin = enteredRememberTime.take(2)
                val timeSec = enteredRememberTime.takeLast(2)

                if (enteredRememberTime.all { it.isDigit() }) {
                    if (timeMin != rememberTimeMin.toString() || timeSec != rememberTimeSec.toString()) {
                        onRememberTimeChanged(
                            timeMin.toInt(),
                            timeSec.toInt()
                        )
                    }
                }
            }
    }

    LaunchedEffect(rememberTimeMin) {
        if (rememberTimeState.text.take(2).toString() != rememberTimeMin.toString()) {
            rememberTimeState.setTextAndPlaceCursorAtEnd(rememberTimeMin.toString())
        }
    }

    LaunchedEffect(rememberTimeSec) {
        if (rememberTimeState.text.takeLast(2).toString() != rememberTimeSec.toString()) {
            rememberTimeState.setTextAndPlaceCursorAtEnd(rememberTimeSec.toString())
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier.widthIn(min = 60.dp, max = 100.dp),
            state = quantityState,
            textStyle = MemorizeTheme.typography.body.copy(textAlign = TextAlign.Center),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            modifier = Modifier.width(100.dp),
            state = rememberTimeState,
            placeholder = { Text("00:00") },
            textStyle = MemorizeTheme.typography.body.copy(textAlign = TextAlign.Center),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        Text(
            text = stringResource(R.string.numbers_setup_is_binary_label),
            color = MemorizeTheme.colors.primaryText,
            style = MemorizeTheme.typography.body
        )
        Switch(
            checked = isBinary,
            onCheckedChange = {
                onBinaryToggled(it)
            }
        )

        MemorizeActionButton(
            modifier = Modifier
                .widthIn(min = 130.dp, max = 250.dp)
                .height(70.dp),
            text = stringResource(R.string.numbers_setup_start_action),
            onClick = onStartClicked,
            defaultElevation = 8.dp,
            enabled = isStartButtonEnabled,
            addition = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_chevron_forward_24),
                    contentDescription = stringResource(R.string.numbers_setup_start_button_content_description),
                    tint = MemorizeTheme.colors.onAccentText,
                    modifier = Modifier.size(24.dp)
                )
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNumbersSetupContent() {
    MemorizeTheme {
        NumbersSetupContent(
            quantity = 100,
            rememberTimeMin = 23,
            rememberTimeSec = 30,
            isBinary = false,
            isStartButtonEnabled = true,
            onQuantityChanged = {},
            onRememberTimeChanged = { _, _ -> },
            onBinaryToggled = {},
            onStartClicked = {}
        )
    }
}