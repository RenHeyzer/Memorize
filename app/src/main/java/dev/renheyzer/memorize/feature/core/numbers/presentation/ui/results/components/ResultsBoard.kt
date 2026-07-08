package dev.renheyzer.memorize.feature.core.numbers.presentation.ui.results.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.core.ui.ext.toPercentString
import dev.renheyzer.memorize.ui.theme.MemorizeTheme
import dev.renheyzer.memorize.ui.theme.successColors

@Composable
fun ResultsBoard(
    correctCount: Int,
    totalCount: Int,
    scorePercentage: Float,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.width(360.dp),
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(
                    R.string.results_board_score_format,
                    correctCount,
                    totalCount
                ),
                color = MaterialTheme.successColors.success,
                style = MaterialTheme.typography.bodyMedium,
            )

            Text(
                text = buildAnnotatedString {
                    append(stringResource(R.string.results_board_percentage))
                    append(" ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(scorePercentage.toPercentString())
                    }
                },
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview
@Composable
fun PreviewResultsBoard() {
    MemorizeTheme {
        ResultsBoard(
            correctCount = 24,
            totalCount = 32,
            scorePercentage = 83.34633f
        )
    }
}