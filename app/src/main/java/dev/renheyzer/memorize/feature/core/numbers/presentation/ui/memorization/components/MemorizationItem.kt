package dev.renheyzer.memorize.feature.core.numbers.presentation.ui.memorization.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.renheyzer.memorize.ui.theme.MemorizeTheme

@Composable
fun MemorizationItem(number: Int, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.aspectRatio(1f),
        shape = MemorizeTheme.shape.card,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MemorizeTheme.colors.secondaryBackground),
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = number.toString(),
                color = MemorizeTheme.colors.primaryText,
                style = MemorizeTheme.typography.display
            )
        }
    }
}