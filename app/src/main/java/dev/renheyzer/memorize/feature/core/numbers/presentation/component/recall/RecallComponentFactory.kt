package dev.renheyzer.memorize.feature.core.numbers.presentation.component.recall

import com.arkivanov.decompose.ComponentContext
import dev.renheyzer.memorize.core.di.factory.ComponentFactory
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersAnswer
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersParam

fun ComponentFactory.createRecallComponent(
    context: ComponentContext,
    params: NumbersParam,
    finishRecall: (answers: NumbersAnswer) -> Unit
): RecallComponent = DefaultRecallComponent(
    componentContext = context,
    env = appDependencies.componentEnvironment,
    params = params,
    countdownTimerManager = appDependencies.createCountdownTimerManager(),
    finishRecall = finishRecall
)