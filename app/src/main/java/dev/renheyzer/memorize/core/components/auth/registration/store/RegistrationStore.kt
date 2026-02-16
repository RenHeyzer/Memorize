package dev.renheyzer.memorize.core.components.auth.registration.store

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.core.common.fold
import dev.renheyzer.memorize.core.ui.SnackbarAction
import dev.renheyzer.memorize.core.ui.SnackbarController
import dev.renheyzer.memorize.core.ui.SnackbarEvent
import dev.renheyzer.memorize.core.ui.UiText
import dev.renheyzer.memorize.core.ui.toUiText
import dev.renheyzer.memorize.feature.auth.domain.model.ValidationError
import dev.renheyzer.memorize.feature.auth.domain.usecase.registration.RegisterByEmailUseCase
import dev.renheyzer.memorize.feature.auth.presentation.model.ValidationErrorUI
import dev.renheyzer.memorize.feature.auth.presentation.model.toUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RegistrationStore(
    mainContext: CoroutineContext,
    private val registerByEmailUseCase: RegisterByEmailUseCase,
    private val snackbarController: SnackbarController
) : InstanceKeeper.Instance {

    private val scope = CoroutineScope(mainContext + SupervisorJob())

    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<RegistrationEvents>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun registerByEmail(
        email: String,
        password: String,
        confirmPassword: String,
    ) {
        scope.launch {
            _uiState.update { it.copy(isLoading = true, validationError = ValidationErrorUI()) }
            registerByEmailUseCase(email, password, confirmPassword).fold(
                onRight = {
                    _uiState.update { it.copy(isLoading = false) }
                    val message = UiText.StringResource(R.string.registration_was_successful)
                    _events.send(RegistrationEvents.NavigateToLogin(message))
                },
                onLeft = { error ->
                    _uiState.update { it.copy(isLoading = false) }
                    when (error) {
                        is ValidationError -> _uiState.update { it.copy(validationError = error.toUI()) }
                        else -> {
                            val message = error.toUiText()
                            _events.send(RegistrationEvents.ShowError(message))
                        }
                    }
                }
            )
        }
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
        super.onDestroy()
        scope.cancel()
    }
}

data class RegistrationUiState(
    val isLoading: Boolean = false,
    val validationError: ValidationErrorUI = ValidationErrorUI()
)

sealed interface RegistrationEvents {
    data class ShowError(val error: UiText) : RegistrationEvents
    data class NavigateToLogin(val message: UiText?) : RegistrationEvents
}