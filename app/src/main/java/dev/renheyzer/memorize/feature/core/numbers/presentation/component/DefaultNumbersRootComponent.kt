package dev.renheyzer.memorize.feature.core.numbers.presentation.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.getOrCreate
import dev.renheyzer.memorize.core.di.factory.ComponentFactory
import dev.renheyzer.memorize.feature.core.numbers.di.NumbersDependencies
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.NumbersRootComponent.Child.Memorization
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.NumbersRootComponent.Child.Recall
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.NumbersRootComponent.Child.Results
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.NumbersRootComponent.Child.Setup
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.memorization.MemorizationComponent.Params
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.memorization.createMemorizationComponent
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.recall.createRecallComponent
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.result.createResultsComponent
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.setup.createNumbersSetupComponent
import kotlinx.serialization.Serializable

class DefaultNumbersRootComponent(
    componentContext: ComponentContext,
    private val factory: ComponentFactory,
    private val numbersDependenciesFactory: () -> NumbersDependencies,
    private val backHome: () -> Unit
) : NumbersRootComponent, ComponentContext by componentContext {

    private val numbersDependencies = instanceKeeper.getOrCreate { numbersDependenciesFactory() }

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, NumbersRootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Setup,
        handleBackButton = true,
        childFactory = ::childFactory
    )

    private fun childFactory(
        config: Config,
        componentContext: ComponentContext
    ): NumbersRootComponent.Child =
        when (config) {
            Config.Setup -> Setup(
                factory.createNumbersSetupComponent(
                    context = componentContext,
                    navigateToMemorization = { options ->
                        navigation.replaceCurrent(
                            Config.Memorization(
                                quantity = options.quantity,
                                time = options.rememberTime,
                                isRandom = !options.isBinary
                            )
                        )
                    }
                )
            )

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
                        navigation.replaceCurrent(Config.Recall(time = config.time))
                    }
                )
            )

            is Config.Recall -> Recall(
                factory.createRecallComponent(
                    context = componentContext,
                    numbersDependencies = numbersDependencies,
                    time = config.time,
                    navigateToResults = {
                        navigation.replaceCurrent(Config.Results)
                    }
                )
            )

            is Config.Results -> Results(
                factory.createResultsComponent(
                    context = componentContext,
                    numbersDependencies = numbersDependencies,
                    navigateToHome = backHome,
                    navigateToSetup = {
                        navigation.replaceCurrent(Config.Setup)
                    }
                )
            )
        }
}

@Serializable
private sealed interface Config {

    @Serializable
    data object Setup : Config

    @Serializable
    data class Memorization(val quantity: Int, val time: Long, val isRandom: Boolean) : Config

    @Serializable
    data class Recall(val time: Long) : Config

    @Serializable
    data object Results : Config
}