package dev.renheyzer.memorize.core.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.renheyzer.memorize.ui.theme.MemorizeTheme

@Composable
fun MemorizeDefaultButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    text: String
) {
    ElevatedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        enabled = enabled,
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 2.dp),
        shape = MemorizeTheme.shape.shape,
        colors = ButtonDefaults.elevatedButtonColors(containerColor = MemorizeTheme.colors.secondaryBackground)
    ) {
        Text(
            text = text,
            color = MemorizeTheme.colors.onAccentText,
            style = MemorizeTheme.typography.button,
        )
    }
}