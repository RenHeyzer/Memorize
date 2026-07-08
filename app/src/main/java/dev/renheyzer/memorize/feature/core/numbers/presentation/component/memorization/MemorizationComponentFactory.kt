package dev.renheyzer.memorize.feature.core.numbers.presentation.component.memorization

import com.arkivanov.decompose.ComponentContext
import dev.renheyzer.memorize.core.di.factory.ComponentFactory
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersParam
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersTask

fun ComponentFactory.createMemorizationComponent(
    context: ComponentContext,
    params: NumbersParam,
    task: NumbersTask,
    finishMemorization: () -> Unit
): MemorizationComponent =
    DefaultMemorizationComponent(
        componentContext = context,
        env = appDependencies.componentEnvironment,
        countdownTimerManager = appDependencies.createCountdownTimerManager(),
        params = params,
        task = task,
        finishMemorization = finishMemorization
    )