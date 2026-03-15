package dev.renheyzer.memorize.core.components.core.numbers

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.getOrCreate
import dev.renheyzer.memorize.core.components.core.numbers.NumbersRootComponent.Child.Memorization
import dev.renheyzer.memorize.core.components.core.numbers.NumbersRootComponent.Child.Recall
import dev.renheyzer.memorize.core.components.core.numbers.dependencies.NumbersDependencies
import dev.renheyzer.memorize.core.components.core.numbers.memorization.MemorizationComponent.Params
import dev.renheyzer.memorize.core.components.core.numbers.memorization.factory.createMemorizationComponent
import dev.renheyzer.memorize.core.components.core.numbers.recall.factory.createRecallComponent
import dev.renheyzer.memorize.core.di.factory.ComponentFactory
import kotlinx.serialization.Serializable

class DefaultNumbersRootComponent(
    componentContext: ComponentContext,
    private val factory: ComponentFactory,
    private val numbersDependenciesFactory: () -> NumbersDependencies
) : NumbersRootComponent, ComponentContext by componentContext {

    private val numbersDependencies = instanceKeeper.getOrCreate { numbersDependenciesFactory() }

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, NumbersRootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Memorization(
            quantity = 28,
            time = 1000L * 120,
            isRandom = true
        ),
        handleBackButton = true,
        childFactory = ::childFactory
    )

    private fun childFactory(
        config: Config,
        componentContext: ComponentContext
    ): NumbersRootComponent.Child =
        when (config) {
            is Config.Memorization -> Memorization(
                factory.createMemorizationComponent(
                    context = componentContext,
                    numbersDependencies = numbersDependencies,
                    params = Params(
                        quantity = config.quantity,
                        time = config.time,
                        isRandom = config.isRandom,
                    ),
                    navigateToRecall = {
                        navigation.pushNew(Config.Recall(config.time))
                    }
                )
            )

            is Config.Recall -> Recall(
                factory.createRecallComponent(
                    context = componentContext,
                    numbersDependencies = numbersDependencies,
                    time = config.time,
                    navigateToResults = {}
                )
            )
        }
}

@Serializable
private sealed interface Config {
    @Serializable
    data class Memorization(val quantity: Int, val time: Long, val isRandom: Boolean) : Config

    @Serializable
    data class Recall(val time: Long) : Config
}