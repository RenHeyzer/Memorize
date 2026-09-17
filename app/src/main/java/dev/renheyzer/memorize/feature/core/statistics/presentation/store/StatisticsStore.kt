package dev.renheyzer.memorize.feature.core.statistics.presentation.store

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.core.ui.UiText
import dev.renheyzer.memorize.core.ui.mapper.toUiText
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsResult
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersResult
import dev.renheyzer.memorize.feature.core.statistics.data.repository.StatisticsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class StatisticsStore(
    mainContext: CoroutineContext,
    private val statisticsRepository: StatisticsRepository
) : InstanceKeeper.Instance {

    private val scope = CoroutineScope(mainContext + SupervisorJob())

    private val _uiState = MutableStateFlow(StatisticsUiState())
    val uiState = _uiState.asStateFlow()

    private val _actions = Channel<StatisticsAction>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    init {
        subscribeToGameResults()
    }

    private fun subscribeToGameResults() {
        scope.launch {
            val numbersDeferred = async {
                statisticsRepository.getNumbersResults()
            }

            val cardsDeferred = async {
                statisticsRepository.getCardsResults()
            }

            val numbersResults = numbersDeferred.await()
            val cardsResults = cardsDeferred.await()

            if (numbersResults.isRight && cardsResults.isRight) {
                _uiState.update {
                    it.copy(
                        numbersResults = numbersResults.rightOrNull() ?: emptyList(),
                        cardsResults = cardsResults.rightOrNull() ?: emptyList()
                    )
                }
            } else {
                val error = numbersResults.leftOrNull() ?: cardsResults.leftOrNull()
                _actions.send(
                    StatisticsAction.ShowError(
                        error?.toUiText() ?: UiText.StringResource(
                            R.string.error_network_unknown
                        )
                    )
                )
            }
        }
    }
}

data class StatisticsUiState(
    val numbersResults: List<NumbersResult> = emptyList(),
    val cardsResults: List<CardsResult> = emptyList(),
)

sealed interface StatisticsAction {
    data class ShowError(val message: UiText) : StatisticsAction
}