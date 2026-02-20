package dev.renheyzer.memorize.core.components.auth.verification

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import dev.renheyzer.memorize.core.components.auth.verification.store.VerificationEvents
import dev.renheyzer.memorize.core.components.auth.verification.store.VerificationStore
import dev.renheyzer.memorize.core.components.auth.verification.store.VerificationUiState
import dev.renheyzer.memorize.core.ui.SnackbarController
import dev.renheyzer.memorize.core.ui.StringResolver
import dev.renheyzer.memorize.core.ui.timer.CountdownTimerManager
import dev.renheyzer.memorize.feature.auth.data.repositories.AuthRepository
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class VerificationComponent(
    componentContext: ComponentContext,
    private val mainContext: CoroutineContext,
    private val countdownTimerManager: CountdownTimerManager,
    private val params: Verification.Params,
    private val authRepository: AuthRepository,
    private val stringResolver: StringResolver,
    private val snackbarController: SnackbarController,
    private val navigateToLogin: () -> Unit,
    private val navigateToHome: () -> Unit
) : Verification, ComponentContext by componentContext {

    private val scope = coroutineScope(mainContext + SupervisorJob())

    private val store = instanceKeeper.getOrCreate {
        VerificationStore(
            mainContext = mainContext,
            countdownTimerManager = countdownTimerManager,
            oobCode = params.oobCode,
            authRepository = authRepository,
            snackbarController = snackbarController
        )
    }

    init {
        scope.launch {
            store.events.collect { event ->
                when (event) {
                    is VerificationEvents.ShowError -> {
                        val message = stringResolver.resolve(event.error)
                        store.showMessage(message)
                    }
                }
            }
        }
    }

    override val uiState: StateFlow<VerificationUiState> = store.uiState

    override fun onResendClicked() {
        store.restartTimer()
        store.startTimer()
        store.sendEmailLink()
    }

    override fun onBackToLoginClicked() {
        navigateToLogin()
    }

    override fun onNextClicked() {
        if (authRepository.isUserLoggedIn) {
            navigateToHome()
        } else {
            navigateToLogin
        }
    }
}

