package dev.renheyzer.memorize.feature.core.cards.presentation.ui.recall

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.renheyzer.memorize.feature.core.cards.domain.model.Card

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun RecallDeck(
    answersDeck: List<Card?>,
    onCardMoved: (index: Int) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(64.dp),
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(
                count = answersDeck.size
            ) { index ->
                val card = answersDeck[index]

                TargetSlot(
                    card = card,
                    onClick = { onCardMoved(index) },
                    sharedTransitionScope = sharedTransitionScope
                )
            }
        }
    }
}