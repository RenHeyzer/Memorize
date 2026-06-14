package dev.renheyzer.memorize.core.components.core.numbers.factory

import com.arkivanov.decompose.ComponentContext
import dev.renheyzer.memorize.core.di.factory.ComponentFactory
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.DefaultNumbersRootComponent
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.NumbersRootComponent

fun ComponentFactory.createNumbersRootComponent(
    context: ComponentContext,
    backHome: () -> Unit
): NumbersRootComponent =
    DefaultNumbersRootComponent(
        componentContext = context,
        env = appDependencies.componentEnvironment,
        factory = this,
        numbersDependenciesFactory = { appDependencies.numbersDependencies() },
        backHome = backHome,
    )