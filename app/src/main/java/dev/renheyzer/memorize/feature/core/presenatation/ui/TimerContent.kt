package dev.renheyzer.memorize.feature.core.presenatation.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.ui.theme.MemorizeTheme

@Composable
fun TimerContent(
    timerValueProvider: () -> String,
    modifier: Modifier = Modifier
) {
    AssistChip(
        modifier = modifier,
        onClick = {},
        label = {
            Text(
                text = timerValueProvider(),
                style = MemorizeTheme.typography.body,
                color = MemorizeTheme.colors.primaryText
            )
        },
        leadingIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_clock_loader_24),
                contentDescription = null,
                modifier = Modifier.size(AssistChipDefaults.IconSize),
                tint = MemorizeTheme.colors.primaryText
            )
        },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = MemorizeTheme.colors.primaryBackground
        ),
        border = AssistChipDefaults.assistChipBorder(true)
    )
}