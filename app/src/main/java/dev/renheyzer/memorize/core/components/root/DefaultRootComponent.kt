package dev.renheyzer.memorize.core.components.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
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
            initialConfiguration = ChildConfig.Auth,
            handleBackButton = true,
            serializer = ChildConfig.serializer(),
            childFactory = ::childFactory
        )

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
                        snackbarController = appDependencies.snackbarController
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
    data object Auth : ChildConfig
}