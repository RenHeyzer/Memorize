package dev.renheyzer.memorize.feature.core.numbers.presentation.ui.recall.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import dev.renheyzer.memorize.ui.theme.MemorizeTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RecallItem(number: String, onNumberChanged: (String) -> Unit, enabled: Boolean, modifier: Modifier = Modifier) {
    val numberState = rememberTextFieldState(initialText = number)

    LaunchedEffect(numberState) {
        snapshotFlow { numberState.text }
            .collectLatest { newText ->
                val enteredNumber = newText.toString()

                if (enteredNumber != number) {
                    onNumberChanged(enteredNumber)
                }
            }
    }

    LaunchedEffect(number) {
        if (numberState.text.toString() != number) {
            numberState.setTextAndPlaceCursorAtEnd(number)
        }
    }

    Card(
        modifier = modifier.aspectRatio(1f),
        shape = MemorizeTheme.shape.card,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MemorizeTheme.colors.secondaryBackground),
    ) {
        BasicTextField(
            state = numberState,
            inputTransformation = {
                if (!asCharSequence().isDigitsOnly()) {
                    revertAllChanges()
                }
            },
            textStyle = MemorizeTheme.typography.display.copy(textAlign = TextAlign.Center),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            lineLimits = TextFieldLineLimits.SingleLine,
            decorator = { innerTextField ->
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    innerTextField()
                }
            },
            enabled = enabled
        )
    }
}

@Preview
@Composable
fun PreviewRecallItem() {
    MemorizeTheme {
        RecallItem(number = "23", onNumberChanged = { }, enabled = true)
    }
}