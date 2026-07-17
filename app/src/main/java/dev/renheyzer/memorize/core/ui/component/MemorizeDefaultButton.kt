package dev.renheyzer.memorize.core.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun MemorizeDefaultButton(
    onClick: () -> Unit,
    text: String,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    addition: (@Composable RowScope.() -> Unit)? = null,
) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 50.dp),
        enabled = enabled,
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 8.dp),
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.elevatedButtonColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyMedium,
        )

        if (addition != null) {
            addition()
        }
    }
}

@Composable
fun MemorizeActionButton(
    onClick: () -> Unit,
    text: String,
    defaultElevation: Dp,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    addition: (@Composable RowScope.() -> Unit)? = null,
) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier.height(70.dp),
        enabled = enabled,
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = defaultElevation),
        shape = MaterialTheme.shapes.large,
        colors = ButtonDefaults.elevatedButtonColors(containerColor = containerColor)
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyLarge
        )
        if (addition != null) addition()
    }
}

@Composable
fun MemorizeActionIconButton(
    onClick: () -> Unit,
    @DrawableRes iconRes: Int,
    contentDescription: String,
    defaultElevation: Dp,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minWidth = 50.dp, minHeight = 50.dp),
        enabled = enabled,
        contentPadding = PaddingValues(0.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = defaultElevation),
        shape = MaterialTheme.shapes.large,
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        )
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(iconRes),
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(24.dp)
        )
    }
}