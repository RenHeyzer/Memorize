package dev.renheyzer.memorize.feature.core.numbers.presentation.ui.memorization.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.renheyzer.memorize.ui.theme.MemorizeTheme


@Composable
fun TimerContent(
    timerValueProvider: () -> String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = MemorizeTheme.shape.card,
        color = MemorizeTheme.colors.secondaryBackground,
        shadowElevation = 8.dp,
    ) {
        Text(
            text = timerValueProvider(),
            color = MemorizeTheme.colors.primaryText,
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center,
            style = MemorizeTheme.typography.body
        )
    }
}
