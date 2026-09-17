package dev.renheyzer.memorize.feature.core.numbers.presentation.component.result

import com.arkivanov.decompose.ComponentContext
import dev.renheyzer.memorize.core.di.factory.ComponentFactory
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersResult

fun ComponentFactory.createResultComponent(
    context: ComponentContext,
    result: NumbersResult,
    finishResult: () -> Unit,
    mapsToSetup: () -> Unit,
): ResultComponent = DefaultResultComponent(
    componentContext = context,
    env = appDependencies.componentEnvironment,
    result = result,
    navigateHome = finishResult,
    navigateToSetup = mapsToSetup
)