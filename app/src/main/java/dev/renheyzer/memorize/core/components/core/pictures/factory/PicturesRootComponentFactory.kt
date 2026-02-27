package dev.renheyzer.memorize.core.components.core.pictures.factory

import com.arkivanov.decompose.ComponentContext
import dev.renheyzer.memorize.core.components.core.pictures.DefaultPicturesRootComponent
import dev.renheyzer.memorize.core.components.core.pictures.PicturesRootComponent
import dev.renheyzer.memorize.core.di.factory.ComponentFactory

fun ComponentFactory.createPicturesRootComponent(
    context: ComponentContext
): PicturesRootComponent =
    DefaultPicturesRootComponent(componentContext = context, env = appDependencies.componentEnvironment)
