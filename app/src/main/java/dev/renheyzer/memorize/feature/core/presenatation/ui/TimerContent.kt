package dev.renheyzer.memorize.feature.core.presenatation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.renheyzer.memorize.R
import kotlinx.coroutines.flow.StateFlow

@Composable
fun TimerContent(
    timerState: StateFlow<String>,
    modifier: Modifier = Modifier
) {
    val timerValue by timerState.collectAsStateWithLifecycle()

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        AssistChip(
            onClick = {},
            label = {
                Text(
                    text = timerValue,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_clock_loader_24),
                    contentDescription = null,
                    modifier = Modifier.size(AssistChipDefaults.IconSize),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            },
            colors = AssistChipDefaults.assistChipColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            border = AssistChipDefaults.assistChipBorder(enabled = true)
        )
    }
}