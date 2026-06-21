package dev.renheyzer.memorize.feature.core.cards.presentation.ui.recall

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
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
    isCardSelected: Boolean,
    onCardSelected: (source: Card?) -> Unit,
    onCardMoved: (index: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TimerContent(
            timerValueProvider = timerValueProvider,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        )

        SharedTransitionScope {
            CorrectOrderDeck(
                isCardSelected = isCardSelected,
                onCardSelected = onCardSelected,
                orderedDeck = orderedDeck,
                answersDeck = answersDeck,
                sharedTransitionScope = this@SharedTransitionScope,
                modifier = Modifier.weight(0.5f)
            )

            RecallDeck(
                answersDeck = answersDeck,
                onCardMoved = { index -> onCardMoved(index) },
                sharedTransitionScope = this@SharedTransitionScope,
                modifier = Modifier.weight(0.5f)
            )
        }
    }
}