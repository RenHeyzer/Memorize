package dev.renheyzer.memorize.feature.core.numbers.presentation.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import dev.renheyzer.memorize.core.di.factory.ComponentFactory
import dev.renheyzer.memorize.core.ui.SnackbarEvent
import dev.renheyzer.memorize.core.ui.decompose.ComponentEnvironment
import dev.renheyzer.memorize.feature.core.numbers.di.NumbersDependencies
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.NumbersRootComponent.Child.Result
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.NumbersRootComponent.Child.Setup
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.memorization.NumbersRoundComponentArgs
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.memorization.createNumbersRoundComponent
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.result.createResultComponent
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.setup.createNumbersSetupComponent
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.NumbersSessionAction
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.NumbersSessionIntent
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.NumbersSessionStore
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

private const val KEY_SESSION_STATE = "sessionState"

class DefaultNumbersRootComponent(
    componentContext: ComponentContext,
    private val env: ComponentEnvironment,
    private val factory: ComponentFactory,
    private val numbersDependenciesFactory: () -> NumbersDependencies,
    private val backHome: () -> Unit
) : NumbersRootComponent, ComponentContext by componentContext {

    private val scope = coroutineScope(env.mainContext + SupervisorJob())

    private val numbersDependencies = instanceKeeper.getOrCreate { numbersDependenciesFactory() }

    private val sessionStore = instanceKeeper.getOrCreate {
        NumbersSessionStore(
            mainContext = env.mainContext,
            savedState = stateKeeper.consume(
                key = KEY_SESSION_STATE,
                strategy = NumbersSessionState.serializer()
            ),
            generateNumbersUseCase = numbersDependencies.generateNumbersUseCase,
            repository = numbersDependencies.numbersRepository
        )
    }

    init {
        stateKeeper.register(
            key = KEY_SESSION_STATE,
            strategy = NumbersSessionState.serializer(),
            supplier = sessionStore.sessionState::value
        )

        scope.launch {
            sessionStore.actions.collect { action ->
                when (action) {
                    is NumbersSessionAction.ShowError -> {
                        val stringMessage = env.stringResolver.resolve(action.message)

                        scope.launch {
                            env.snackbarController.sendEvent(
                                SnackbarEvent(
                                    message = stringMessage
                                )
                            )
                        }
                    }

                    NumbersSessionAction.StartNextRound -> {
                        val currentRound = sessionStore.sessionState.value.round
                        navigation.replaceCurrent(Config.Round(round = currentRound))
                    }

                    NumbersSessionAction.NavigateToResult ->
                        navigation.replaceCurrent(Config.Result)
                }
            }
        }
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
                    startGame = { params ->
                        sessionStore.onIntent(intent = NumbersSessionIntent.OnSetupCompleted(params))
                        navigation.replaceCurrent(Config.Round(round = 1))
                    }
                )
            )

            is Config.Round -> NumbersRootComponent.Child.Round(
                factory.createNumbersRoundComponent(
                    context = componentContext,
                    args = NumbersRoundComponentArgs(
                        params = requireNotNull(sessionStore.sessionState.value.params) {
                            "Params cannot be null when setup completed"
                        },
                        currentRound = config.round,
                        task = requireNotNull(sessionStore.sessionState.value.allTask) {
                            "Task cannot be null when setup completed"
                        },
                        calculateRoundResultsUseCase = numbersDependencies.calculateRoundResultsUseCase,
                        navigateToResult = { roundResult ->
                            sessionStore.onIntent(
                                intent = NumbersSessionIntent.OnRoundFinished(roundResult)
                            )
                        },
                        navigateHome = backHome
                    )
                )
            )

            is Config.Result -> Result(
                factory.createResultComponent(
                    context = componentContext,
                    result = requireNotNull(sessionStore.sessionState.value.result) {
                        "NumbersResult cannot be null when all the rounds completed"
                    },
                    finishResult = backHome,
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
    data class Round(val round: Int) : Config

    @Serializable
    data object Result : Config
}