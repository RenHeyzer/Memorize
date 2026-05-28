package dev.renheyzer.memorize.feature.auth.presentation.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.getOrCreate
import dev.renheyzer.memorize.core.ui.SnackbarController
import dev.renheyzer.memorize.core.ui.StringResolver
import dev.renheyzer.memorize.core.ui.UiText
import dev.renheyzer.memorize.core.ui.timer.CountdownTimerManager
import dev.renheyzer.memorize.feature.auth.di.AuthDependencies
import dev.renheyzer.memorize.feature.auth.presentation.component.login.LoginComponent
import dev.renheyzer.memorize.feature.auth.presentation.component.registration.RegistrationComponent
import dev.renheyzer.memorize.feature.auth.presentation.component.verification.Verification
import dev.renheyzer.memorize.feature.auth.presentation.component.verification.VerificationComponent
import kotlinx.serialization.Serializable
import kotlin.coroutines.CoroutineContext

class DefaultAuthComponent(
    componentContext: ComponentContext,
    private val mainContext: CoroutineContext,
    private val stringResolver: StringResolver,
    private val authDependenciesFactory: () -> AuthDependencies,
    private val snackbarController: SnackbarController,
    private val countdownTimerManager: CountdownTimerManager,
    private val deepLinkCode: String? = null,
    private val navigateToHome: () -> Unit
) : AuthComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<ScreenConfig>()

    private val authDependencies = instanceKeeper.getOrCreate { authDependenciesFactory() }

    override val stack: Value<ChildStack<*, AuthComponent.AuthChild>> =
        childStack(
            source = navigation,
            initialConfiguration = if (deepLinkCode != null) {
                ScreenConfig.Verification(
                    oobCode = deepLinkCode
                )
            } else ScreenConfig.Login,
            handleBackButton = true,
            serializer = ScreenConfig.serializer(),
            childFactory = ::childFactory
        )

    override fun onVerificationLinkReceived(code: String) {
        navigation.bringToFront(ScreenConfig.Verification(oobCode = code))
    }

    private fun childFactory(
        config: ScreenConfig,
        componentContext: ComponentContext
    ): AuthComponent.AuthChild =
        when (config) {
            is ScreenConfig.Registration -> {
                AuthComponent.AuthChild.RegistrationChild(
                    RegistrationComponent(
                        componentContext,
                        mainContext = mainContext,
                        stringResolver = stringResolver,
                        registerByEmailUseCase = authDependencies.registerByEmailUseCase,
                        snackbarController = snackbarController,
                        navigateToVerification = { message ->
                            navigation.pushNew(ScreenConfig.Verification(message))
                        },
                        navigateToLogin = {
                            navigation.pop()
                        }
                    )
                )
            }

            is ScreenConfig.Verification -> AuthComponent.AuthChild.VerificationChild(
                VerificationComponent(
                    componentContext,
                    mainContext = mainContext,
                    countdownTimerManager = countdownTimerManager,
                    params = Verification.Params(
                        message = config.message,
                        oobCode = config.oobCode,
                    ),
                    authRepository = authDependencies.authRepository,
                    stringResolver = stringResolver,
                    snackbarController = snackbarController,
                    navigateToLogin = {
                        navigation.replaceAll(ScreenConfig.Login)
                    },
                    navigateToHome = navigateToHome
                )
            )

            is ScreenConfig.Login -> AuthComponent.AuthChild.LoginChild(
                LoginComponent(
                    componentContext,
                    navigateToRegistration = {
                        navigation.pushNew(ScreenConfig.Registration)
                    }
                )
            )
        }
}

@Serializable
private sealed interface ScreenConfig {
    @Serializable
    data object Registration : ScreenConfig

    @Serializable
    data class Verification(
        val message: UiText? = null,
        val oobCode: String? = null
    ) : ScreenConfig

    @Serializable
    data object Login : ScreenConfig
}