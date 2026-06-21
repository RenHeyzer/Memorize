package dev.renheyzer.memorize.feature.core.cards.presentation.ui.recall

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import dev.renheyzer.memorize.feature.core.cards.domain.model.Card

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TargetSlot(
    card: Card?,
    onClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    modifier: Modifier = Modifier
) {
    val outlineColor = MaterialTheme.colorScheme.outline

    with(sharedTransitionScope) {
        Box(
            modifier = modifier
                .aspectRatio(0.72f)
                .clickable(onClick = onClick)
        ) {
            AnimatedContent(
                targetState = card,
                label = "RecalledCardTransition"
            ) { cardState ->
                if (cardState != null) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                        modifier = Modifier
                            .fillMaxSize()
                            .sharedBounds(
                                sharedContentState = rememberSharedContentState(key = "card_${cardState.id}"),
                                animatedVisibilityScope = this@AnimatedContent
                            )
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = cardState.value,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .drawBehind {
                                drawRoundRect(
                                    color = outlineColor,
                                    style = Stroke(
                                        width = 3f,
                                        pathEffect = PathEffect.dashPathEffect(
                                            floatArrayOf(
                                                10f,
                                                10f
                                            ), 0f
                                        )
                                    ),
                                    cornerRadius = CornerRadius(12.dp.toPx())
                                )
                            },
                        contentAlignment = Alignment.Center,
                        content = {}
                    )
                }
            }
        }
    }
}