package dev.renheyzer.memorize.core.components.auth

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.getOrCreate
import dev.renheyzer.memorize.core.components.auth.login.LoginComponent
import dev.renheyzer.memorize.core.components.auth.registration.RegistrationComponent
import dev.renheyzer.memorize.core.components.auth.verification.VerificationComponent
import dev.renheyzer.memorize.core.di.AuthDependencies
import dev.renheyzer.memorize.core.ui.SnackbarController
import dev.renheyzer.memorize.core.ui.StringResolver
import dev.renheyzer.memorize.core.ui.UiText
import kotlinx.serialization.Serializable
import kotlin.coroutines.CoroutineContext

class DefaultAuthComponent(
    componentContext: ComponentContext,
    private val mainContext: CoroutineContext,
    private val stringResolver: StringResolver,
    private val authDependenciesFactory: () -> AuthDependencies,
    private val snackbarController: SnackbarController
) : AuthComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<ScreenConfig>()

    private val authDependencies = instanceKeeper.getOrCreate { authDependenciesFactory() }

    override val stack: Value<ChildStack<*, AuthComponent.AuthChild>> =
        childStack(
            source = navigation,
            initialConfiguration = ScreenConfig.Registration,
            handleBackButton = true,
            serializer = ScreenConfig.serializer(),
            childFactory = ::childFactory
        )

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
                        navigationToVerification = { message ->
                            navigation.pushNew(ScreenConfig.Verification(message))
                        }
                    )
                )
            }

            is ScreenConfig.Verification -> AuthComponent.AuthChild.VerificationChild(
                VerificationComponent(
                    componentContext
                )
            )

            is ScreenConfig.Login -> AuthComponent.AuthChild.LoginChild(
                LoginComponent(
                    componentContext
                )
            )
        }
}

@Serializable
private sealed interface ScreenConfig {
    @Serializable
    data object Registration : ScreenConfig

    @Serializable
    data class Verification(val message: UiText?) : ScreenConfig

    @Serializable
    data object Login : ScreenConfig
}