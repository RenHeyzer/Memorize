package dev.renheyzer.memorize.feature.core.cards.presentation.ui.setup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardSuit

@Composable
fun SuitItem(
    suit: CardSuit,
    onSelect: () -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    val isRedSuit = suit == CardSuit.DIAMONDS || suit == CardSuit.HEARTS
    val defaultContentColor =
        if (isRedSuit) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface

    val containerColor =
        if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
    val contentColor =
        if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else defaultContentColor
    val borderColor =
        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(0.72f)
            .clickable { onSelect() },
        contentAlignment = Alignment.Center
    ) {
        val cardWidth = maxWidth

        val cardPadding = cardWidth * 0.05f

        val suitFontSize = (cardWidth.value * 0.35f).sp

        val elevation = if (cardWidth < 80.dp) 2.dp else 6.dp
        val shape =
            if (cardWidth < 80.dp) MaterialTheme.shapes.medium else MaterialTheme.shapes.large

        Card(
            modifier = Modifier
                .aspectRatio(0.72f)
                .fillMaxSize()
                .padding(cardPadding),
            shape = shape,
            border = BorderStroke(1.dp, borderColor),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = elevation),
            colors = CardDefaults.elevatedCardColors(
                containerColor = containerColor,
                contentColor = contentColor
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = suit.suit,
                    style = MaterialTheme.typography.headlineLarge.copy(fontSize = suitFontSize),
                    fontWeight = FontWeight.Bold,
                    softWrap = false
                )
            }
        }
    }
}