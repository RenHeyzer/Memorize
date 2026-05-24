package dev.renheyzer.memorize.core.components.core.pictures

import com.arkivanov.decompose.ComponentContext
import dev.renheyzer.memorize.core.ui.decompose.ComponentEnvironment

class DefaultPicturesRootComponent(
    componentContext: ComponentContext,
    private val env: ComponentEnvironment
) : PicturesRootComponent, ComponentContext by componentContext