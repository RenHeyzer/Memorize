package dev.renheyzer.memorize.feature.core.cards.presentation.ui.memorization

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import dev.renheyzer.memorize.feature.core.cards.domain.model.Card
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardSuit

@Composable
fun CardItem(card: Card) {
    val suitText = card.suit.suit
    val rankText = card.rank.value

    val isRedSuit = card.suit == CardSuit.DIAMONDS || card.suit == CardSuit.HEARTS
    val contentColor =
        if (isRedSuit) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.72f),
        contentAlignment = Alignment.Center
    ) {
        val cardWidth = maxWidth

        val cardPadding = cardWidth * 0.05f
        val internalSpacing = cardWidth * 0.02f

        val rankFontSize = (cardWidth.value * 0.50f).sp
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
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = elevation),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = contentColor
            )
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = rankText,
                    style = MaterialTheme.typography.displayMedium.copy(fontSize = rankFontSize),
                    fontWeight = FontWeight.ExtraBold,
                    softWrap = false
                )

                Spacer(modifier = Modifier.height(internalSpacing))

                Text(
                    text = suitText,
                    style = MaterialTheme.typography.headlineLarge.copy(fontSize = suitFontSize),
                    fontWeight = FontWeight.Bold,
                    softWrap = false
                )
            }
        }
    }
}