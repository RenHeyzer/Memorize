package dev.renheyzer.memorize.feature.core.cards.presentation.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.getOrCreate
import dev.renheyzer.memorize.core.di.factory.ComponentFactory
import dev.renheyzer.memorize.feature.core.cards.di.CardsDependencies
import dev.renheyzer.memorize.feature.core.cards.presentation.component.memorization.createMemorizationComponent
import dev.renheyzer.memorize.feature.core.cards.presentation.component.recall.createRecallComponent
import dev.renheyzer.memorize.feature.core.cards.presentation.component.results.createResultsComponent
import dev.renheyzer.memorize.feature.core.cards.presentation.component.setup.createCardsSetupComponent
import dev.renheyzer.memorize.feature.core.cards.presentation.store.CardsSessionIntent
import dev.renheyzer.memorize.feature.core.cards.presentation.store.CardsSessionState
import dev.renheyzer.memorize.feature.core.cards.presentation.store.CardsSessionStore
import kotlinx.serialization.Serializable

private const val KEY_CARDS_SESSION_STATE = "cardsSessionState"

class DefaultCardsRootComponent(
    componentContext: ComponentContext,
    private val factory: ComponentFactory,
    private val cardsDependenciesFactory: () -> CardsDependencies,
    private val backHome: () -> Unit
) : ComponentContext by componentContext, CardsRootComponent {

    private val cardsDependencies = instanceKeeper.getOrCreate { cardsDependenciesFactory() }

    private val sessionStore = instanceKeeper.getOrCreate {
        CardsSessionStore(
            savedState = stateKeeper.consume(
                key = KEY_CARDS_SESSION_STATE,
                strategy = CardsSessionState.serializer()
            ),
            generateOrderedDeckUseCase = cardsDependencies.generateOrderedDeckUseCase,
            shuffleDeckUseCase = cardsDependencies.shuffleDeckUseCase,
            calculateCardsResultUseCase = cardsDependencies.calculateCardsResultUseCase,
        )
    }

    init {
        stateKeeper.register(
            key = KEY_CARDS_SESSION_STATE,
            strategy = CardsSessionState.serializer(),
            supplier = sessionStore.sessionState::value
        )
    }

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, CardsRootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Memorization,
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
                    context = componentContext
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
                    }
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
                        navigation.replaceCurrent(Config.Results)
                    }
                )
            )

            Config.Results -> CardsRootComponent.Child.Results(
                factory.createResultsComponent(
                    context = componentContext,
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