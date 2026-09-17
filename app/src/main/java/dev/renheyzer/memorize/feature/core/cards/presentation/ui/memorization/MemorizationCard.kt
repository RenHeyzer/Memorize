package dev.renheyzer.memorize.feature.core.cards.presentation.ui.memorization

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.renheyzer.memorize.core.ui.AdaptiveDevicePreviews
import dev.renheyzer.memorize.core.ui.ThemePreviews
import dev.renheyzer.memorize.feature.core.cards.domain.model.Card
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardRank
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardSuit
import dev.renheyzer.memorize.feature.core.cards.presentation.ui.common.CardFace
import dev.renheyzer.memorize.ui.theme.MemorizeTheme

@Composable
fun MemorizationCard(card: Card, modifier: Modifier = Modifier) {
    OutlinedCard(
        modifier = modifier
            .aspectRatio(0.72f),
    ) {
        CardFace(card = card, modifier = Modifier.weight(1f))
    }
}

@ThemePreviews
@AdaptiveDevicePreviews
@Composable
private fun PreviewMemorizationCard() {
    MemorizeTheme {
        MemorizationCard(
            card = Card(
                rank = CardRank.ACE,
                suit = CardSuit.CLUBS
            )
        )
    }
}