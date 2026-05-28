package dev.renheyzer.memorize.feature.core.numbers.presentation.ui.results.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.renheyzer.memorize.feature.core.numbers.domain.model.AnswerResult
import dev.renheyzer.memorize.ui.theme.MemorizeTheme

@Composable
fun ResultsContent(
    details: List<AnswerResult>,
    correctCount: Int,
    totalCount: Int,
    scorePercentage: Float,
    itemPerPage: Int,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ResultsBoard(
            modifier = Modifier
                .padding(16.dp),
            correctCount = correctCount,
            totalCount = totalCount,
            scorePercentage = scorePercentage
        )

        Spacer(modifier = Modifier.weight(0.2f))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { pageIndex ->
            val startIndex = (pageIndex * itemPerPage).coerceAtMost(details.size)
            val endIndex = (startIndex + itemPerPage).coerceAtMost(details.size)
            val numbersForThisPage = details.subList(startIndex, endIndex)

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
                    space = 8.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                verticalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                    alignment = Alignment.CenterVertically
                ),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(
                    items = numbersForThisPage,
                    key = { result -> result.id }
                ) { result ->
                    ResultItem(
                        number = result.number,
                        answer = result.answer,
                        isCorrect = result.isCorrect,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(0.4f))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewResultsContent() {
    MemorizeTheme {
        ResultsContent(
            details = listOf(
                AnswerResult(id = 1, number = 23, answer = 32, isCorrect = false),
                AnswerResult(id = 2, number = 23, answer = 32, isCorrect = false),
                AnswerResult(id = 3, number = 23, answer = 32, isCorrect = false),
                AnswerResult(id = 4, number = 23, answer = 32, isCorrect = false),
                AnswerResult(id = 5, number = 23, answer = 32, isCorrect = false),
                AnswerResult(id = 6, number = 23, answer = 32, isCorrect = false),
                AnswerResult(id = 7, number = 23, answer = 32, isCorrect = false),
                AnswerResult(id = 8, number = 23, answer = 32, isCorrect = false),
                AnswerResult(id = 9, number = 23, answer = 32, isCorrect = false),
                AnswerResult(id = 11, number = 23, answer = 32, isCorrect = false),
                AnswerResult(id = 12, number = 23, answer = 32, isCorrect = false),
                AnswerResult(id = 13, number = 23, answer = 32, isCorrect = false),
                AnswerResult(id = 14, number = 23, answer = 32, isCorrect = false)
            ),
            correctCount = 24,
            totalCount = 32,
            scorePercentage = 83.34333f,
            itemPerPage = 9,
            pagerState = rememberPagerState { 9 },
        )
    }
}