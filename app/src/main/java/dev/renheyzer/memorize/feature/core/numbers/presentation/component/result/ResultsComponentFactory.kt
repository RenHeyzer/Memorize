package dev.renheyzer.memorize.feature.core.numbers.presentation.component.result

import com.arkivanov.decompose.ComponentContext
import dev.renheyzer.memorize.core.di.factory.ComponentFactory
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersResult

fun ComponentFactory.createResultsComponent(
    context: ComponentContext,
    results: NumbersResult,
    finishResults: () -> Unit,
    mapsToSetup: () -> Unit,
): ResultsComponent = DefaultResultsComponent(
    componentContext = context,
    env = appDependencies.componentEnvironment,
    results = results,
    finishResults = finishResults,
    mapsToSetup = mapsToSetup
)