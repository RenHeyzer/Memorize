package dev.renheyzer.memorize.feature.core.numbers.presentation.component

import com.arkivanov.decompose.ComponentContext
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