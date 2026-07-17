package dev.renheyzer.memorize.feature.core.cards.presentation.component.memorization

import com.arkivanov.decompose.ComponentContext
import dev.renheyzer.memorize.core.di.factory.ComponentFactory
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsParam
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsTask

fun ComponentFactory.createMemorizationComponent(
    context: ComponentContext,
    params: CardsParam,
    task: CardsTask,
    finishMemorization: () -> Unit
): MemorizationComponent = DefaultMemorizationComponent(
    componentContext = context,
    env = appDependencies.componentEnvironment,
    countdownTimerManager = appDependencies.createCountdownTimerManager(),
    params = params,
    task = task,
    finishMemorization = finishMemorization
)