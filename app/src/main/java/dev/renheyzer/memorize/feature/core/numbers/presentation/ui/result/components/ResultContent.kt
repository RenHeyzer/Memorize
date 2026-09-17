package dev.renheyzer.memorize.feature.core.numbers.presentation.ui.result.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import dev.renheyzer.memorize.feature.core.numbers.presentation.model.RoundResultUi
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.result.ResultState
import dev.renheyzer.memorize.feature.core.numbers.presentation.ui.result.ResultStateProvider
import dev.renheyzer.memorize.ui.theme.MemorizeTheme
import dev.renheyzer.memorize.ui.theme.spacings

@Composable
fun ResultContent(
    rounds: Int,
    roundsResult: List<RoundResultUi>,
    correctCount: Int,
    totalCount: Int,
    scorePercentage: String,
    modifier: Modifier = Modifier,
) {
    var selectedRound by rememberSaveable { mutableIntStateOf(1) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ResultsBoard(
            modifier = Modifier
                .padding(MaterialTheme.spacings.medium),
            correctCount = correctCount,
            totalCount = totalCount,
            scorePercentage = scorePercentage
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(40.dp),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(
                space = MaterialTheme.spacings.extraSmall,
                alignment = Alignment.CenterHorizontally
            ),
            verticalArrangement = Arrangement.spacedBy(
                space = MaterialTheme.spacings.extraSmall,
                alignment = Alignment.CenterVertically
            ),
            contentPadding = PaddingValues(MaterialTheme.spacings.medium)
        ) {
            items(rounds) { round ->
                OutlinedCard(
                    onClick = { selectedRound = round },
                    modifier = Modifier.aspectRatio(1f),
                    shape = MaterialTheme.shapes.extraLarge,
                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.outlineVariant)
                ) {
                    Text(
                        text = (round + 1).toString(),
                        modifier = Modifier.fillMaxSize(),
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .sizeIn(
                    minWidth = 300.dp,
                    minHeight = 300.dp,
                    maxWidth = 400.dp,
                    maxHeight = 400.dp
                )
                .aspectRatio(1f),
            horizontalArrangement = Arrangement.spacedBy(
                space = MaterialTheme.spacings.small,
                alignment = Alignment.CenterHorizontally
            ),
            verticalArrangement = Arrangement.spacedBy(
                space = MaterialTheme.spacings.small,
                alignment = Alignment.CenterVertically
            ),
            contentPadding = PaddingValues(MaterialTheme.spacings.medium)
        ) {
            items(
                items = roundsResult[selectedRound].details,
                key = { result -> result.index }
            ) { result ->
                ResultItem(
                    number = result.number,
                    answer = result.answer,
                    isCorrect = result.isCorrect,
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewResultContent(
    @PreviewParameter(ResultStateProvider::class) state: ResultState
) {
    MemorizeTheme {
        ResultContent(
            rounds = state.rounds,
            roundsResult = state.roundsResult,
            correctCount = state.correctCount,
            totalCount = state.totalCount,
            scorePercentage = state.scorePercentage,
        )
    }
}