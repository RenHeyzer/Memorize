package dev.renheyzer.memorize.core.components.root

import android.net.Uri
import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import dev.renheyzer.memorize.core.components.auth.DefaultAuthComponent
import dev.renheyzer.memorize.core.components.home.DefaultHomeComponent
import dev.renheyzer.memorize.core.di.AppDependencies
import kotlinx.serialization.Serializable

class DefaultRootComponent(
    componentContext: ComponentContext,
    private val appDependencies: AppDependencies
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<ChildConfig>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            initialConfiguration = defineInitialConfiguration(),
            handleBackButton = true,
            serializer = ChildConfig.serializer(),
            childFactory = ::childFactory
        )

    private fun defineInitialConfiguration(): ChildConfig {
        val isUserLoggedIn = appDependencies.authDependencies().authRepository.isUserLoggedIn

        return if (isUserLoggedIn) {
            Log.e("Home", "Home")
            ChildConfig.Home
        } else {
            Log.e("Auth", "Auth")
            ChildConfig.Auth()
        }
    }

    override fun handleDeepLink(uri: Uri) {
        val mode = uri.getQueryParameter("mode")
        val code = uri.getQueryParameter("oobCode")

        if (mode == "verifyEmail" && code != null) {
            val activeChild = stack.value.active.instance

            if (activeChild is RootComponent.Child.Auth) {
                Log.e("Root", "mode = $mode ||| code = $code")
                activeChild.component.onVerificationLinkReceived(code)
            } else {
                navigation.bringToFront(ChildConfig.Auth(deepLinkCode = code))
            }
        }

        // May add code to reset password
    }

    private fun childFactory(
        config: ChildConfig,
        componentContext: ComponentContext
    ): RootComponent.Child =
        when (config) {
            is ChildConfig.Home -> {
                RootComponent.Child.Home(
                    DefaultHomeComponent(componentContext)
                )
            }

            is ChildConfig.Auth -> {
                RootComponent.Child.Auth(
                    DefaultAuthComponent(
                        componentContext,
                        mainContext = appDependencies.dispatchers.mainImmediate,
                        stringResolver = appDependencies.stringResolver,
                        authDependenciesFactory = { appDependencies.authDependencies() },
                        snackbarController = appDependencies.snackbarController,
                        countdownTimerManager = appDependencies.countdownTimerManager,
                        deepLinkCode = config.deepLinkCode,
                        navigateToHome = {
                            navigation.replaceAll(ChildConfig.Home)
                        }
                    )
                )
            }
        }
}


@Serializable
private sealed interface ChildConfig {

    @Serializable
    data object Home : ChildConfig

    @Serializable
    data class Auth(val deepLinkCode: String? = null) : ChildConfig
}