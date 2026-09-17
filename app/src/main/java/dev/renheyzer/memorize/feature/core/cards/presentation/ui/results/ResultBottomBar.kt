package dev.renheyzer.memorize.feature.core.cards.presentation.ui.results

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.renheyzer.memorize.R

@Composable
fun ResultBottomBar(
    onRetryClick: () -> Unit,
    onHomeClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FilledTonalButton(
                onClick = onHomeClick,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
            ) {
                Text(text = stringResource(R.string.action_home))
            }

            Button(
                onClick = onRetryClick,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
            ) {
                Text(text = stringResource(R.string.action_retry))
            }
        }
    }
}