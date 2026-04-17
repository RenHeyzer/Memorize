package dev.renheyzer.memorize.core.components.core

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import dev.renheyzer.memorize.core.components.core.home.HomeComponent
import dev.renheyzer.memorize.core.components.core.home.factory.createHomeComponent
import dev.renheyzer.memorize.core.components.core.numbers.factory.createNumbersRootComponent
import dev.renheyzer.memorize.core.components.core.pictures.factory.createPicturesRootComponent
import dev.renheyzer.memorize.core.di.factory.ComponentFactory
import kotlinx.serialization.Serializable

class DefaultCoreRootComponent(
    componentContext: ComponentContext,
    private val factory: ComponentFactory
) : CoreRootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, CoreRootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Home,
        handleBackButton = true,
        childFactory = ::childFactory
    )

    private fun childFactory(
        config: Config,
        componentContext: ComponentContext
    ): CoreRootComponent.Child =
        when (config) {
            Config.Home -> CoreRootComponent.Child.Home(
                factory.createHomeComponent(componentContext, onOutput = { output ->
                    when (output) {
                        HomeComponent.Output.NavigateToNumbers -> {
                            navigation.pushNew(Config.Numbers)
                        }

                        HomeComponent.Output.NavigateToPictures -> {}
                    }
                })
            )

            Config.Numbers -> CoreRootComponent.Child.Numbers(
                factory.createNumbersRootComponent(
                    context = componentContext,
                    backHome = {
                        navigation.pop()
                    }
                )
            )

            Config.Pictures -> CoreRootComponent.Child.Pictures(
                factory.createPicturesRootComponent(componentContext)
            )
        }

    override fun onBackPressed() {
        navigation.pop()
    }

}

@Serializable
private sealed interface Config {
    @Serializable
    data object Home : Config

    @Serializable
    data object Pictures : Config

    @Serializable
    data object Numbers : Config
}