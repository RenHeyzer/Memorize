package dev.renheyzer.memorize.core.components.auth.verification.store

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.core.common.fold
import dev.renheyzer.memorize.core.ui.SnackbarAction
import dev.renheyzer.memorize.core.ui.SnackbarController
import dev.renheyzer.memorize.core.ui.SnackbarEvent
import dev.renheyzer.memorize.core.ui.UiText
import dev.renheyzer.memorize.core.ui.timer.CountdownTimerManager
import dev.renheyzer.memorize.core.ui.timer.formatAsTimerMMSS
import dev.renheyzer.memorize.core.ui.toUiText
import dev.renheyzer.memorize.feature.auth.data.repositories.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class VerificationStore(
    mainContext: CoroutineContext,
    private val countdownTimerManager: CountdownTimerManager,
    private val oobCode: String?,
    private val authRepository: AuthRepository,
    private val snackbarController: SnackbarController
) : InstanceKeeper.Instance {

    private val scope = CoroutineScope(mainContext + SupervisorJob())

    private val _uiState = MutableStateFlow(VerificationUiState())

    val uiState: StateFlow<VerificationUiState> = combine(
        _uiState,
        countdownTimerManager.timeLeft,
        countdownTimerManager.isRunning
    ) { uiState, timeLeft, isRunning ->
        uiState.copy(
            timerValue = timeLeft.formatAsTimerMMSS(),
            isResendEnabled = !uiState.isLoading && !isRunning
        )
    }.stateIn(
        scope = scope,
        started = SharingStarted.Lazily,
        initialValue = _uiState.value
    )

    private val _events = Channel<VerificationEvents>(Channel.BUFFERED)

    val events = _events.receiveAsFlow()

    init {
        getUserEmail()
        if (oobCode != null) {
            confirmEmail(oobCode)
        } else {
            sendEmailLink()
        }

        countdownTimerManager.setDuration(RESEND_DURATION)
        countdownTimerManager.start(scope)
    }


    private fun confirmEmail(code: String) {
        scope.launch {
            _uiState.update { it.copy(isLoading = true) }

            authRepository.onDeepLinkReceived(code).fold(
                onRight = {
                    val message = UiText.StringResource(R.string.verification_was_successful)
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isSuccess = true,
                            successMessage = message
                        )
                    }
                },
                onLeft = { error ->
                    _uiState.update { it.copy(isLoading = false) }
                    val message = error.toUiText()
                    _events.send(VerificationEvents.ShowError(message))
                }
            )
        }
    }

    fun sendEmailLink() {
        scope.launch {
            authRepository.sendEmailVerification()
            // Обработать результат
        }
    }

    private fun getUserEmail() {
        val email = authRepository.getCurrentUserEmail()
        if (email != null) {
            _uiState.update { it.copy(email = email) }
        } else {
            _uiState.update {
                it.copy(
                    error = UiText.StringResource(R.string.error_session_expired),
                    isFatalError = true
                )
            }
        }
    }


    fun startTimer() {
        countdownTimerManager.start(scope)
    }

    fun restartTimer() {
        countdownTimerManager.reset(RESEND_DURATION)
    }

    fun showMessage(message: String, action: (() -> Unit)? = null) {
        scope.launch {
            snackbarController.sendEvent(
                SnackbarEvent(
                    message = message,
                    action = action?.let { SnackbarAction(name = "OK", action = it) }
                )
            )
        }
    }

    override fun onDestroy() {
        countdownTimerManager.stop()
        scope.cancel()
    }

    companion object {
        private const val RESEND_DURATION = 120 * 1000L
    }
}

data class VerificationUiState(
    val isLoading: Boolean = false,
    val email: String = "",
    val timerValue: String = "00:00",
    val isResendEnabled: Boolean = false,
    val error: UiText = UiText.Empty,
    val isFatalError: Boolean = false,
    val isSuccess: Boolean = false,
    val successMessage: UiText = UiText.Empty
)

sealed interface VerificationEvents {
    data class ShowError(val error: UiText) : VerificationEvents
}