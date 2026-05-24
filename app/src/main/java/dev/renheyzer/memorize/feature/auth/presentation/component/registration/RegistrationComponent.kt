package dev.renheyzer.memorize.feature.auth.presentation.component.registration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import dev.renheyzer.memorize.feature.auth.presentation.store.registration.RegistrationEvents
import dev.renheyzer.memorize.feature.auth.presentation.store.registration.RegistrationStore
import dev.renheyzer.memorize.feature.auth.presentation.store.registration.RegistrationUiState
import dev.renheyzer.memorize.core.ui.SnackbarController
import dev.renheyzer.memorize.core.ui.StringResolver
import dev.renheyzer.memorize.core.ui.UiText
import dev.renheyzer.memorize.feature.auth.domain.usecase.registration.RegisterByEmailUseCase
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RegistrationComponent(
    componentContext: ComponentContext,
    private val mainContext: CoroutineContext,
    private val stringResolver: StringResolver,
    private val registerByEmailUseCase: RegisterByEmailUseCase,
    private val snackbarController: SnackbarController,
    private val navigateToVerification: (message: UiText) -> Unit,
    private val navigateToLogin: () -> Unit
) : Registration, ComponentContext by componentContext {

    private val scope = coroutineScope(mainContext + SupervisorJob())

    private val store = instanceKeeper.getOrCreate {
        RegistrationStore(
            mainContext = mainContext,
            registerByEmailUseCase = registerByEmailUseCase,
            snackbarController = snackbarController
        )
    }

    init {
        scope.launch {
            store.events.collect { event ->
                when (event) {
                    is RegistrationEvents.NavigateToVerification -> navigateToVerification(
                        event.message
                    )

                    is RegistrationEvents.ShowError -> {
                        val message = stringResolver.resolve(event.error)
                        store.showMessage(message)
                    }
                }
            }
        }
    }

    override val uiState: StateFlow<RegistrationUiState> = store.uiState

    override fun onSignUpClick(email: String, password: String, confirmPassword: String) {
        store.registerByEmail(email, password, confirmPassword)
    }

    override fun onAlreadyHaveAnAccountClick() {
        navigateToLogin()
    }
}