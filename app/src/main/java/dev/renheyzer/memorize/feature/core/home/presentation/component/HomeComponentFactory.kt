package dev.renheyzer.memorize.feature.core.home.presentation.component

import com.arkivanov.decompose.ComponentContext
import dev.renheyzer.memorize.core.di.factory.ComponentFactory

fun ComponentFactory.createHomeComponent(
    context: ComponentContext,
    onOutput: (HomeComponent.Output) -> Unit
): HomeComponent =
    DefaultHomeComponent(componentContext = context, onOutput = onOutput)