package dev.renheyzer.memorize.components.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import dev.renheyzer.memorize.components.auth.DefaultAuthComponent
import dev.renheyzer.memorize.components.home.DefaultHomeComponent
import dev.renheyzer.memorize.di.AppDependencies
import kotlinx.serialization.Serializable

class DefaultRootComponent(
    componentContext: ComponentContext,
    private val appDependencies: AppDependencies
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<ChildConfig>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            initialConfiguration = ChildConfig.Home,
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
                    DefaultAuthComponent(componentContext, appDependencies)
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