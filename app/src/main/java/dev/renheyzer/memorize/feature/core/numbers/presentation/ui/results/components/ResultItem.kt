package dev.renheyzer.memorize.feature.core.numbers.presentation.ui.results.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.renheyzer.memorize.ui.theme.MemorizeTheme
import dev.renheyzer.memorize.ui.theme.successColors

@Composable
fun ResultItem(
    number: Int,
    answer: Int?,
    isCorrect: Boolean,
    modifier: Modifier = Modifier,
) {
    var isShowCorrect by remember { mutableStateOf(false) }

    val targetColor = when {
        isCorrect -> MaterialTheme.successColors.success
        isShowCorrect -> MaterialTheme.colorScheme.surfaceContainer
        else -> MaterialTheme.colorScheme.error
    }

    val containerColor by animateColorAsState(
        targetValue = targetColor,
        label = "ResultItemColor"
    )

    Card(
        modifier = modifier
            .aspectRatio(1f)
            .clickable(
                enabled = !isCorrect,
                onClick = {
                    isShowCorrect = !isShowCorrect
                }),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            val displayedText = if (isShowCorrect && !isCorrect) {
                number.toString()
            } else {
                answer?.toString() ?: ""
            }

            val textColor = if (isShowCorrect) {
                MaterialTheme.colorScheme.onSurface
            } else {
                MaterialTheme.colorScheme.onPrimary
            }

            Text(
                text = displayedText,
                color = textColor,
                style = MaterialTheme.typography.displayLarge
            )
        }
    }
}

@Preview
@Composable
fun PreviewResultItem() {
    MemorizeTheme {
        ResultItem(
            number = 12,
            answer = 13,
            isCorrect = false
        )
    }
}