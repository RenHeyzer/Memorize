package dev.renheyzer.memorize.core.components.core.pictures

import com.arkivanov.decompose.ComponentContext
import dev.renheyzer.memorize.core.di.factory.ComponentFactory

fun ComponentFactory.createPicturesRootComponent(
    context: ComponentContext
): PicturesRootComponent =
    DefaultPicturesRootComponent(
        componentContext = context,
        env = appDependencies.componentEnvironment
    )
