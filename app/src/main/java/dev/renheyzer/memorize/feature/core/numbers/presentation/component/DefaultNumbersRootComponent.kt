package dev.renheyzer.memorize.feature.core.numbers.presentation.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.getOrCreate
import dev.renheyzer.memorize.core.di.factory.ComponentFactory
import dev.renheyzer.memorize.core.ui.decompose.ComponentEnvironment
import dev.renheyzer.memorize.feature.core.numbers.di.NumbersDependencies
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.NumbersRootComponent.Child.Memorization
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.NumbersRootComponent.Child.Recall
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.NumbersRootComponent.Child.Results
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.NumbersRootComponent.Child.Setup
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.memorization.createMemorizationComponent
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.recall.createRecallComponent
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.result.createResultsComponent
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.setup.createNumbersSetupComponent
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.NumbersSessionIntent
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.NumbersSessionStore
import kotlinx.serialization.Serializable

private const val KEY_SESSION_STATE = "sessionState"

class DefaultNumbersRootComponent(
    componentContext: ComponentContext,
    private val env: ComponentEnvironment,
    private val factory: ComponentFactory,
    private val numbersDependenciesFactory: () -> NumbersDependencies,
    private val backHome: () -> Unit
) : NumbersRootComponent, ComponentContext by componentContext {

    private val numbersDependencies = instanceKeeper.getOrCreate { numbersDependenciesFactory() }

    private val sessionStore = instanceKeeper.getOrCreate {
        NumbersSessionStore(
            mainContext = env.mainContext,
            savedState = stateKeeper.consume(
                key = KEY_SESSION_STATE,
                strategy = NumbersSessionState.serializer()
            ),
            generateNumbersUseCase = numbersDependencies.generateNumbersUseCase,
            calculateNumbersResultUseCase = numbersDependencies.calculateNumbersResultUseCase
        )
    }

    init {
        stateKeeper.register(
            key = KEY_SESSION_STATE,
            strategy = NumbersSessionState.serializer(),
            supplier = sessionStore.sessionState::value
        )
    }

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
                    saveParamsAndStartGame = { params ->
                        sessionStore.onIntent(intent = NumbersSessionIntent.OnSetupCompleted(params))
                        navigation.replaceCurrent(Config.Memorization)
                    }
                )
            )

            is Config.Memorization -> Memorization(
                factory.createMemorizationComponent(
                    context = componentContext,
                    params = requireNotNull(sessionStore.sessionState.value.params) {
                        "Params cannot be null when setup completed"
                    },
                    task = requireNotNull(sessionStore.sessionState.value.task) {
                        "Task cannot be null when setup completed"
                    },
                    finishMemorization = {
                        sessionStore.onIntent(intent = NumbersSessionIntent.OnMemorizationFinished)
                        navigation.replaceCurrent(Config.Recall)
                    }
                )
            )

            is Config.Recall -> Recall(
                factory.createRecallComponent(
                    context = componentContext,
                    params = requireNotNull(sessionStore.sessionState.value.params) {
                        "Params cannot be null when setup completed"
                    },
                    finishRecall = { answers ->
                        sessionStore.onIntent(intent = NumbersSessionIntent.OnRecallFinished(answers))
                        navigation.replaceCurrent(Config.Results)
                    }
                )
            )

            is Config.Results -> Results(
                factory.createResultsComponent(
                    context = componentContext,
                    results = requireNotNull(sessionStore.sessionState.value.result) {
                        "Results cannot be null when recall completed"
                    },
                    finishResults = backHome,
                    mapsToSetup = {
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
    data object Memorization : Config

    @Serializable
    data object Recall : Config

    @Serializable
    data object Results : Config
}