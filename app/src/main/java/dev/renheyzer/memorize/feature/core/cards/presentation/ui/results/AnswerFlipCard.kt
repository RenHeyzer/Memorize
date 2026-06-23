package dev.renheyzer.memorize.feature.core.cards.presentation.ui.results

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.feature.core.cards.domain.model.AnswerResult
import dev.renheyzer.memorize.feature.core.cards.domain.model.Card
import dev.renheyzer.memorize.ui.theme.MemorizeTheme

@Composable
fun AnswerFlipCard(
    result: AnswerResult,
    modifier: Modifier = Modifier
) {
    // State to track if the wrong card is flipped to reveal the truth
    var isFlipped by remember { mutableStateOf(false) }

    // 3D Rotation animation
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 400),
        label = "FlipAnimation"
    )

    // Determine if the front or back is visible based on rotation angle
    val isFrontVisible = rotation < 90f

    // We use graphicsLayer to apply the 3D rotation
    Box(
        modifier = modifier
            .aspectRatio(0.72f)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            }
            .clickable(enabled = !result.isCorrect) {
                // Only allow flipping if the answer was incorrect or missed
                isFlipped = !isFlipped
            },
        contentAlignment = Alignment.Center
    ) {
        if (isFrontVisible) {
            // ФРОНТАЛЬНАЯ СТОРОНА: Ответ пользователя
            FrontSideCard(result = result)
        } else {
            // ОБРАТНАЯ СТОРОНА: Правильный ответ (Скомпенсированный поворот)
            BackSideCard(
                targetCard = result.memorizedCard,
                modifier = Modifier.graphicsLayer {
                    rotationY = 180f // Counter-rotate so text is not mirrored
                }
            )
        }
    }
}

@Composable
private fun FrontSideCard(
    result: AnswerResult,
    modifier: Modifier = Modifier
) {
    val isCorrect = result.isCorrect
    val isMissed = result.recalledCard == null

    val borderColor = when {
        isCorrect -> MemorizeTheme.colors.successColor
        isMissed -> MaterialTheme.colorScheme.outlineVariant
        else -> MaterialTheme.colorScheme.error
    }

    val containerColor = when {
        isCorrect -> MemorizeTheme.colors.successColor.copy(alpha = 0.1f)
        isMissed -> Color.Transparent
        else -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
    }

    Card(
        modifier = modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = if (isMissed) null else BorderStroke(2.dp, borderColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .then(
                    // Пунктирная рамка для пропущенных карт
                    if (isMissed) {
                        Modifier.drawBehind {
                            drawRoundRect(
                                color = borderColor,
                                style = Stroke(
                                    width = 4f,
                                    pathEffect = PathEffect.dashPathEffect(
                                        floatArrayOf(10f, 10f),
                                        0f
                                    )
                                ),
                                cornerRadius = CornerRadius(12.dp.toPx()) // Приближено к M3 medium shape
                            )
                        }
                    } else Modifier
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isMissed) {
                Text(
                    text = "?",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.outline
                )
            } else {
                Text(
                    text = result.recalledCard?.value ?: "",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
private fun BackSideCard(
    targetCard: Card,
    modifier: Modifier = Modifier
) {
    // Подсветка правильного ответа нейтральным/зеленым фоном,
    // чтобы отличить его от пользовательской карточки
    Card(
        modifier = modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(
            containerColor = MemorizeTheme.colors.successColor.copy(alpha = 0.8f)
        ),
        border = BorderStroke(2.dp, MemorizeTheme.colors.successColor)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Иконка для явного обозначения "Это правильный ответ"
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_check),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(16.dp)
                        .padding(bottom = 4.dp)
                )
                Text(
                    text = targetCard.value,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
            }
        }
    }
}