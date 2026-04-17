package dev.renheyzer.memorize.core.components.core.numbers.result.store

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import dev.renheyzer.memorize.core.components.core.numbers.store.GameSessionStore
import dev.renheyzer.memorize.feature.core.numbers.domain.model.AnswerResult
import dev.renheyzer.memorize.feature.core.numbers.domain.usecase.CheckAnswersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.ceil

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

        _uiState.update {
            it.copy(
                details = results.details,
                correctCount = results.correctCount,
                totalCount = results.totalCount,
                scorePercentage = results.scorePercentage
            )
        }
    }
}

data class ResultsUiState(
    val details: List<AnswerResult> = emptyList(),
    val correctCount: Int = 0,
    val totalCount: Int = 0,
    val scorePercentage: Float = 0f,
    val itemPerPage: Int = 9
) {
    val pageCount: Int
        get() = ceil(details.size.toDouble() / itemPerPage).toInt()
}