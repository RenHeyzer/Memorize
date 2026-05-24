package dev.renheyzer.memorize.feature.core.game.presentation.store

import dev.renheyzer.memorize.feature.core.game.domain.model.GamePhase
import dev.renheyzer.memorize.feature.core.game.domain.model.ResultSaveStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface GameSessionStore<Params, Task, Answer, Result> {

    val state: StateFlow<GameSessionState<Task, Answer, Result>>
    val action: Flow<GameSessionAction>

    fun accept(intent: GameSessionIntent<Params, Answer>)
}

data class GameSessionState<Task, Answer, Result>(
    val phase: GamePhase = GamePhase.IDLE,
    val task: Task? = null,
    val answer: Answer? = null,
    val result: Result? = null,
    val timeLeftMillis: Long = 0L,
    val saveStatus: ResultSaveStatus? = null,
    val isExitDialogVisible: Boolean = false,
    val errorMessage: String? = null,
)

sealed interface GameSessionIntent<out Params, out Answer> {
    data class Start<Params>(val params: Params) : GameSessionIntent<Params, Nothing>

    data object FinishMemorization : GameSessionIntent<Nothing, Nothing>
    data object MemorizationTimerExpired : GameSessionIntent<Nothing, Nothing>

    data class UpdateAnswer<Answer>(val answer: Answer) : GameSessionIntent<Nothing, Answer>
    data object FinishRecall : GameSessionIntent<Nothing, Nothing>
    data object RecallTimerExpired : GameSessionIntent<Nothing, Nothing>

    data object BackClicked : GameSessionIntent<Nothing, Nothing>
    data object ConfirmExit : GameSessionIntent<Nothing, Nothing>
    data object DismissExitDialog : GameSessionIntent<Nothing, Nothing>

    data object FinishResult : GameSessionIntent<Nothing, Nothing>
    data object ResultScreenClosed : GameSessionIntent<Nothing, Nothing>
}

sealed interface GameSessionAction {
    data object NavigateToHome : GameSessionAction
    data object NavigateToRecall : GameSessionAction
    data object NavigateToResult : GameSessionAction
}