package dev.renheyzer.memorize.zeature

import com.arkivanov.decompose.ComponentContext
import dev.renheyzer.memorize.core.di.factory.ComponentFactory

fun ComponentFactory.createNumbersSetupComponent(
    context: ComponentContext,
    navigateToMemorization: (NumbersSetupOptions) -> Unit
): NumbersSetupComponent = DefaultNumbersSetupComponent(
    componentContext = context,
    env = appDependencies.componentEnvironment,
    navigateToMemorization = navigateToMemorization
)