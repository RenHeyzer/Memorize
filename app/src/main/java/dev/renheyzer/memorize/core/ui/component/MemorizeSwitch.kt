package dev.renheyzer.memorize.core.ui.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.renheyzer.memorize.ui.theme.MemorizeTheme

@Composable
fun MemorizeSwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    thumbContent: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
) {
    Switch(
        checked = checked,
        modifier = modifier,
        onCheckedChange = onCheckedChange,
        thumbContent = thumbContent,
        enabled = enabled,
        colors = SwitchDefaults.colors(
            checkedTrackColor = MemorizeTheme.colors.accentColor,
            uncheckedBorderColor = MemorizeTheme.colors.borderColor,
            uncheckedTrackColor = MemorizeTheme.colors.disabledColor
        ),
        interactionSource = interactionSource
    )
}