package dev.renheyzer.memorize.feature.core.numbers.presentation.ui.results

import dev.renheyzer.memorize.feature.core.numbers.domain.model.AnswerResult
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.result.ResultsComponent
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.result.ResultsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeResultsComponent : ResultsComponent {

    override val uiState: StateFlow<ResultsUiState> = MutableStateFlow(
        ResultsUiState(
            details = listOf(
                AnswerResult(id = 1, number = 23, answer = 32, isCorrect = false),
                AnswerResult(id = 2, number = 15, answer = 15, isCorrect = true),
                AnswerResult(id = 3, number = 32, answer = 32, isCorrect = true),
                AnswerResult(id = 4, number = 59, answer = 59, isCorrect = true),
                AnswerResult(id = 5, number = 23, answer = 32, isCorrect = false),
                AnswerResult(id = 6, number = 23, answer = 32, isCorrect = false),
                AnswerResult(id = 7, number = 99, answer = 99, isCorrect = true),
                AnswerResult(id = 8, number = 23, answer = 32, isCorrect = false),
                AnswerResult(id = 9, number = 81, answer = 81, isCorrect = true),
                AnswerResult(id = 10, number = 23, answer = 32, isCorrect = false),
                AnswerResult(id = 11, number = 23, answer = 32, isCorrect = false),
                AnswerResult(id = 12, number = 60, answer = 60, isCorrect = true),
                AnswerResult(id = 13, number = 75, answer = 75, isCorrect = true)
            ),
            correctCount = 23,
            totalCount = 32,
            scorePercentage = 83.32333f,
            itemPerPage = 9,
        )
    )

    override fun onGoHomeClick() = Unit

    override fun onPlayAgainClick() = Unit
}