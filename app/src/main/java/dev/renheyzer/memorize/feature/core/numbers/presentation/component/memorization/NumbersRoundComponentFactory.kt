package dev.renheyzer.memorize.feature.core.numbers.presentation.component.memorization

import com.arkivanov.decompose.ComponentContext
import dev.renheyzer.memorize.core.di.factory.ComponentFactory

fun ComponentFactory.createNumbersRoundComponent(
    context: ComponentContext,
    args: NumbersRoundComponentArgs
): NumbersRoundComponent =
    DefaultNumbersRoundComponent(
        componentContext = context,
        env = appDependencies.componentEnvironment,
        countdownTimerManager = appDependencies.createCountdownTimerManager(),
        args = args
    )