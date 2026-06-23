package dev.renheyzer.memorize.feature.core.cards.presentation.ui.recall

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.renheyzer.memorize.feature.core.cards.domain.model.Card
import dev.renheyzer.memorize.feature.core.presenatation.ui.TimerContent

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun RecallContent(
    timerValueProvider: () -> String,
    orderedDeck: List<Card>,
    answersDeck: List<Card?>,
    selectedCard: Card?,
    onCardSelected: (source: Card?) -> Unit,
    onCardMoved: (index: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    SharedTransitionLayout(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TimerContent(
                timerValueProvider = timerValueProvider,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            )

            CorrectOrderDeck(
                selectedCard = selectedCard,
                onCardSelected = onCardSelected,
                orderedDeck = orderedDeck,
                answersDeck = answersDeck,
                sharedTransitionScope = this@SharedTransitionLayout,
                modifier = Modifier.weight(0.5f)
            )

            RecallDeck(
                answersDeck = answersDeck,
                onCardMoved = { index -> onCardMoved(index) },
                sharedTransitionScope = this@SharedTransitionLayout,
                modifier = Modifier.weight(0.5f)
            )
        }
    }
}