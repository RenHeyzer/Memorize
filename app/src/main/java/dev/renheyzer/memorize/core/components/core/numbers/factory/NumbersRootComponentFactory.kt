package dev.renheyzer.memorize.core.components.core.numbers.factory

import com.arkivanov.decompose.ComponentContext
import dev.renheyzer.memorize.core.components.core.numbers.DefaultNumbersRootComponent
import dev.renheyzer.memorize.core.components.core.numbers.NumbersRootComponent
import dev.renheyzer.memorize.core.di.factory.ComponentFactory

fun ComponentFactory.createNumbersRootComponent(
    context: ComponentContext,
    backHome: () -> Unit
): NumbersRootComponent =
    DefaultNumbersRootComponent(
        componentContext = context,
        factory = this,
        numbersDependenciesFactory = { appDependencies.numbersDependencies() },
        backHome = backHome
    )