package dev.renheyzer.memorize.core.components.core.numbers.result.store

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import dev.renheyzer.memorize.core.components.core.numbers.store.GameSessionStore
import dev.renheyzer.memorize.feature.core.numbers.domain.model.AnswerResult
import dev.renheyzer.memorize.feature.core.numbers.domain.usecase.CheckAnswersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ResultsStore(
    private val gameSessionStore: GameSessionStore,
    private val checkAnswersUseCase: CheckAnswersUseCase
) : InstanceKeeper.Instance {
    private val _uiState = MutableStateFlow(ResultsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        calculateAndGetGameResults()
    }

    private fun calculateAndGetGameResults() {
        val isRandom = gameSessionStore.isRandom
        val numbers = gameSessionStore.generatedNumbers
        val answers = gameSessionStore.userAnswers

        val results = checkAnswersUseCase(numbers = numbers, answers = answers, isRandom = isRandom)

        val scoreValue = "${results.correctCount}/${results.totalCount}"
        val scorePercentage = "${results.scorePercentage}%"

        _uiState.update {
            it.copy(
                details = results.details,
                scoreValue = scoreValue,
                scorePercentage = scorePercentage
            )
        }
    }
}

data class ResultsUiState(
    val details: List<AnswerResult> = emptyList(),
    val scoreValue: String = "0",
    val scorePercentage: String = "0%"
)