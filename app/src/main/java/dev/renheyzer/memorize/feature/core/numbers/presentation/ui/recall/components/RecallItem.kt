package dev.renheyzer.memorize.feature.core.numbers.presentation.ui.recall.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.renheyzer.memorize.ui.theme.MemorizeTheme

@Composable
fun RecallItem(number: Int?, onDone: (number: Int) -> Unit, modifier: Modifier = Modifier) {
    var enteredNumber by rememberSaveable { mutableStateOf(number?.toString() ?: "") }

    Card(
        modifier = modifier.aspectRatio(1f),
        shape = MemorizeTheme.shape.card,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MemorizeTheme.colors.secondaryBackground),
    ) {
        BasicTextField(
            value = enteredNumber,
            onValueChange = {
                enteredNumber = it.take(3)
            },
            singleLine = true,
            textStyle = MemorizeTheme.typography.display.copy(textAlign = TextAlign.Center),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            keyboardActions = KeyboardActions(
                onDone = {
                    onDone(enteredNumber.toInt())
                }
            ),
            decorationBox = { innerTextField ->
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    innerTextField()
                }
            }
        )
    }
}

@Preview
@Composable
fun PreviewRecallItem() {
    MemorizeTheme {
        RecallItem(number = 23, onDone = {})
    }
}