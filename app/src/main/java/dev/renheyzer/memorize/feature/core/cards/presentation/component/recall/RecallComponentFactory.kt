package dev.renheyzer.memorize.feature.core.cards.presentation.component.recall

import com.arkivanov.decompose.ComponentContext
import dev.renheyzer.memorize.core.di.factory.ComponentFactory
import dev.renheyzer.memorize.feature.core.cards.domain.model.Card
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsAnswer
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsParam

fun ComponentFactory.createRecallComponent(
    context: ComponentContext,
    params: CardsParam,
    orderedDeck: List<Card>,
    finishRecall: (answers: CardsAnswer) -> Unit
): RecallComponent = DefaultRecallComponent(
    componentContext = context,
    env = appDependencies.componentEnvironment,
    countdownTimerManager = appDependencies.createCountdownTimerManager(),
    params = params,
    orderedDeck = orderedDeck,
    finishRecall = finishRecall
)