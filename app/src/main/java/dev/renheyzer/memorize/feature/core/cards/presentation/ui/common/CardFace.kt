package dev.renheyzer.memorize.feature.core.cards.presentation.ui.common

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import dev.renheyzer.memorize.core.ui.AdaptiveDevicePreviews
import dev.renheyzer.memorize.core.ui.ThemePreviews
import dev.renheyzer.memorize.feature.core.cards.domain.model.Card
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardRank
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardSuit
import dev.renheyzer.memorize.ui.theme.MemorizeTheme
import dev.renheyzer.memorize.ui.theme.spacings

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CardFace(
    card: Card,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(MaterialTheme.spacings.small),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = card.rank.value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold,
            softWrap = false
        )

        Text(
            text = card.suit.suit,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold,
            softWrap = false
        )
    }
}

@ThemePreviews
@AdaptiveDevicePreviews
@Composable
private fun PreviewCardFace() {
    MemorizeTheme {
        CardFace(
            card = Card(
                rank = CardRank.ACE,
                suit = CardSuit.CLUBS
            )
        )
    }
}