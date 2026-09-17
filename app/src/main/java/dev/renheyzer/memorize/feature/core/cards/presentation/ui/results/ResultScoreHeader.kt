package dev.renheyzer.memorize.feature.core.cards.presentation.ui.results

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.renheyzer.memorize.core.ui.ext.toPercentString
import dev.renheyzer.memorize.ui.theme.MemorizeTheme
import dev.renheyzer.memorize.ui.theme.successColors

@Composable
fun ResultScoreHeader(
    correctCount: Int,
    totalCount: Int,
    scorePercentage: Float,
    modifier: Modifier = Modifier
) {
    val isSuccess = scorePercentage >= 80f
    val mainColor =
        if (isSuccess) MaterialTheme.successColors.success else MaterialTheme.colorScheme.error

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$correctCount / $totalCount",
            style = MaterialTheme.typography.displayLarge.copy(fontWeight = FontWeight.Bold),
            color = mainColor
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = scorePercentage.toPercentString(),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview
@Composable
private fun PreviewResultScoreHeader() {
    MemorizeTheme {
        ResultScoreHeader(
            correctCount = 3,
            totalCount = 7,
            scorePercentage = 42.8f
        )
    }
}