package dev.renheyzer.memorize.feature.core.numbers.presentation.component.setup

import com.arkivanov.decompose.ComponentContext
import dev.renheyzer.memorize.core.di.factory.ComponentFactory
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersParam

fun ComponentFactory.createNumbersSetupComponent(
    context: ComponentContext,
    saveParamsAndStartGame: (params: NumbersParam) -> Unit,
): NumbersSetupComponent = DefaultNumbersSetupComponent(
    componentContext = context,
    env = appDependencies.componentEnvironment,
    saveParamsAndStartGame = saveParamsAndStartGame
)