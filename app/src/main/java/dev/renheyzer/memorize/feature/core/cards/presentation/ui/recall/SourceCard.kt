package dev.renheyzer.memorize.feature.core.cards.presentation.ui.recall

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.renheyzer.memorize.core.ui.AdaptiveDevicePreviews
import dev.renheyzer.memorize.core.ui.ThemePreviews
import dev.renheyzer.memorize.feature.core.cards.domain.model.Card
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardRank
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardSuit
import dev.renheyzer.memorize.feature.core.cards.presentation.ui.common.CardFace
import dev.renheyzer.memorize.ui.theme.MemorizeTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SourceCard(
    card: Card,
    onClick: () -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {

    val containerColor =
        if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceContainer
    val contentColor =
        if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
    val borderColor =
        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
    val borderStroke = BorderStroke(if (isSelected) 2.dp else 1.dp, borderColor)

    OutlinedCard(
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        border = borderStroke,
        modifier = modifier
            .aspectRatio(0.72f),
    ) {
        CardFace(card = card, modifier = Modifier.weight(1f))
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@ThemePreviews
@AdaptiveDevicePreviews
@Composable
private fun PreviewSourceCard() {
    MemorizeTheme {
        var isSelected by remember { mutableStateOf(false) }
        SourceCard(
            card = Card(
                rank = CardRank.ACE,
                suit = CardSuit.CLUBS
            ),
            onClick = { isSelected = !isSelected},
            isSelected = isSelected,
        )
    }
}