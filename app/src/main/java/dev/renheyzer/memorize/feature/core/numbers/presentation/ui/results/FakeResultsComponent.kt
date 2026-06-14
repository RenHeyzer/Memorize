package dev.renheyzer.memorize.feature.core.numbers.presentation.ui.results

import dev.renheyzer.memorize.feature.core.numbers.domain.model.AnswerResult
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.result.ResultsComponent
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.result.ResultsIntent
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.result.ResultsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeResultsComponent : ResultsComponent {

    override val uiState: StateFlow<ResultsUiState> = MutableStateFlow(
        ResultsUiState(
            details = listOf(
                AnswerResult(index = 1, number = 23, answer = 32),
                AnswerResult(index = 2, number = 15, answer = 15),
                AnswerResult(index = 3, number = 32, answer = 32),
                AnswerResult(index = 4, number = 59, answer = 59),
                AnswerResult(index = 5, number = 23, answer = 32),
                AnswerResult(index = 6, number = 23, answer = 32),
                AnswerResult(index = 7, number = 99, answer = 99),
                AnswerResult(index = 8, number = 23, answer = 32),
                AnswerResult(index = 9, number = 81, answer = 81),
                AnswerResult(index = 10, number = 23, answer = 32),
                AnswerResult(index = 11, number = 23, answer = 32),
                AnswerResult(index = 12, number = 60, answer = 60),
                AnswerResult(index = 13, number = 75, answer = 75)
            ),
            correctCount = 23,
            totalCount = 32,
            scorePercentage = 83.32333f,
            itemPerPage = 9,
        )
    )

    override fun onIntent(intent: ResultsIntent) = Unit
}