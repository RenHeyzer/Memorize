package dev.renheyzer.memorize.feature.core.cards.presentation.component

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
import dev.renheyzer.memorize.feature.core.cards.di.CardsDependencies
import dev.renheyzer.memorize.feature.core.cards.presentation.component.memorization.createMemorizationComponent
import dev.renheyzer.memorize.feature.core.cards.presentation.component.recall.createRecallComponent
import dev.renheyzer.memorize.feature.core.cards.presentation.component.results.createResultsComponent
import dev.renheyzer.memorize.feature.core.cards.presentation.component.setup.createCardsSetupComponent
import dev.renheyzer.memorize.feature.core.cards.presentation.store.CardsSessionAction
import dev.renheyzer.memorize.feature.core.cards.presentation.store.CardsSessionIntent
import dev.renheyzer.memorize.feature.core.cards.presentation.store.CardsSessionState
import dev.renheyzer.memorize.feature.core.cards.presentation.store.CardsSessionStore
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

private const val KEY_CARDS_SESSION_STATE = "cardsSessionState"

class DefaultCardsRootComponent(
    componentContext: ComponentContext,
    private val env: ComponentEnvironment,
    private val factory: ComponentFactory,
    private val cardsDependenciesFactory: () -> CardsDependencies,
    private val backHome: () -> Unit
) : ComponentContext by componentContext, CardsRootComponent {

    private val scope = coroutineScope(env.mainContext + SupervisorJob())

    private val cardsDependencies = instanceKeeper.getOrCreate { cardsDependenciesFactory() }

    private val sessionStore = instanceKeeper.getOrCreate {
        CardsSessionStore(
            mainContext = env.mainContext,
            savedState = stateKeeper.consume(
                key = KEY_CARDS_SESSION_STATE,
                strategy = CardsSessionState.serializer()
            ),
            generateOrderedDeckUseCase = cardsDependencies.generateOrderedDeckUseCase,
            shuffleDeckUseCase = cardsDependencies.shuffleDeckUseCase,
            calculateCardsResultUseCase = cardsDependencies.calculateCardsResultUseCase,
            repository = cardsDependencies.cardsRepository,
        )
    }

    init {
        stateKeeper.register(
            key = KEY_CARDS_SESSION_STATE,
            strategy = CardsSessionState.serializer(),
            supplier = sessionStore.sessionState::value
        )

        scope.launch {
            sessionStore.actions.collect { action ->
                when (action) {
                    is CardsSessionAction.ShowError -> {
                        val stringMessage = env.stringResolver.resolve(action.message)

                        scope.launch {
                            env.snackbarController.sendEvent(
                                SnackbarEvent(
                                    message = stringMessage
                                )
                            )
                        }
                    }

                    CardsSessionAction.NavigateToResults -> navigation.replaceCurrent(Config.Results)
                }
            }
        }
    }

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, CardsRootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Setup,
        handleBackButton = true,
        childFactory = ::childFactory
    )

    private fun childFactory(
        config: Config,
        componentContext: ComponentContext
    ): CardsRootComponent.Child =
        when (config) {
            Config.Setup -> CardsRootComponent.Child.Setup(
                factory.createCardsSetupComponent(
                    context = componentContext,
                    onStartGameRequested = { params ->
                        sessionStore.onIntent(CardsSessionIntent.OnSetupCompleted(params = params))
                        navigation.replaceCurrent(Config.Memorization)
                    }
                )
            )

            Config.Memorization -> CardsRootComponent.Child.Memorization(
                factory.createMemorizationComponent(
                    context = componentContext,
                    params = requireNotNull(sessionStore.sessionState.value.params) {
                        "Params cannot be null when setup completed"
                    },
                    task = requireNotNull(sessionStore.sessionState.value.task) {
                        "Task cannot be null when setup completed"
                    },
                    finishMemorization = {
                        sessionStore.onIntent(intent = CardsSessionIntent.OnMemorizationFinished)
                        navigation.replaceCurrent(Config.Recall)
                    },
                    navigateHome = backHome
                )
            )

            Config.Recall -> CardsRootComponent.Child.Recall(
                factory.createRecallComponent(
                    context = componentContext,
                    params = requireNotNull(sessionStore.sessionState.value.params) {
                        "Params cannot be null when setup completed"
                    },
                    orderedDeck = requireNotNull(sessionStore.sessionState.value.orderedDeck) {
                        "Ordered deck cannot be null when setup completed"
                    },
                    finishRecall = { answers ->
                        sessionStore.onIntent(
                            CardsSessionIntent.OnRecallFinished(
                                answers
                            )
                        )
                    },
                    navigateHome = backHome
                )
            )

            Config.Results -> CardsRootComponent.Child.Results(
                factory.createResultsComponent(
                    context = componentContext,
                    results = requireNotNull(sessionStore.sessionState.value.result) {
                        "Results cannot be null when recall completed"
                    },
                    onPlayAgainRequested = {
                        // TODO: Save session state and restart current session to play again
                    },
                    onResultsCompleted = {
                        backHome()
                    }
                )
            )
        }
}

@Serializable
sealed interface Config {
    @Serializable
    data object Setup : Config

    @Serializable
    data object Memorization : Config

    @Serializable
    data object Recall : Config

    @Serializable
    data object Results : Config
}