package dev.renheyzer.memorize.feature.auth.presentation.store.login

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import dev.renheyzer.memorize.core.ui.UIState
import dev.renheyzer.memorize.feature.auth.domain.repository.AuthRepository
import dev.renheyzer.memorize.feature.auth.domain.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoginStore(
    mainContext: CoroutineContext,
    private val repository: AuthRepository
) : InstanceKeeper.Instance {

    private val scope = CoroutineScope(mainContext + SupervisorJob())

    private val _uiState = MutableStateFlow<UIState<User>>(UIState.Loading())
    val uiState = _uiState.asStateFlow()

    fun signInViaEmailAndPassword() {
        scope.launch {
//            repository.signInViaEmailAndPassword(email, password).fold(
//                onSuccess = { user ->
//                    if (!user.isEmailVerified) repository.sendSignInLinkToEmail(email)
//                },
//                onFailure = { error ->
//
//                }
//            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}