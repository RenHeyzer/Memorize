package dev.renheyzer.memorize.core.components.core.home.factory

import com.arkivanov.decompose.ComponentContext
import dev.renheyzer.memorize.core.components.core.home.DefaultHomeComponent
import dev.renheyzer.memorize.core.components.core.home.HomeComponent
import dev.renheyzer.memorize.core.di.factory.ComponentFactory

fun ComponentFactory.createHomeComponent(
    context: ComponentContext,
    onOutput: (HomeComponent.Output) -> Unit
): HomeComponent =
    DefaultHomeComponent(componentContext = context, onOutput = onOutput)